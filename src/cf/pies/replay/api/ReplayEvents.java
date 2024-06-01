package cf.pies.replay.api;

import cf.pies.replay.api.recordable.entity.*;
import cf.pies.replay.api.recordable.world.BlockRecordable;
import cf.pies.replay.api.utils.NMS;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class ReplayEvents implements Listener {
    private final Set<Replay> recordingReplays = ReplayAPI.getApi().getRecordingReplays();

    @EventHandler
    public void joinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        ChannelPipeline pipeline = NMS.getHandle(player).playerConnection.networkManager.channel.pipeline();

        // don't inject again on reload
        if (pipeline.get("ReplayAPI-Decoder") != null) return;

        pipeline.addAfter("decoder", "ReplayAPI-Decoder", new ChannelDuplexHandler() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object message) throws Exception {
                if (message instanceof Packet) {
                    // note: return to cancel packet
                    onPacket(player, (Packet<?>) message);
                }
                super.channelRead(ctx, message);
            }
        });
    }

    public void onPacket(Player player, Packet<?> packet) {
        if (packet instanceof PacketPlayInArmAnimation) {

            for (Replay replay : recordingReplays) {
                if (!replay.isRecording()) continue;
                if (replay.isRecordingPlayer(player)) {
                    replay.record(new SwingRecordable(player.getEntityId()));
                }
            }

        }
    }

    @EventHandler(
            priority = EventPriority.HIGHEST,
            ignoreCancelled = true
    )
    public void moveEvent(PlayerMoveEvent event) {
        for (Replay replay : recordingReplays) {
            if (!replay.isRecording()) continue;
            Player player = event.getPlayer();
            if (replay.isRecordingPlayer(player)) {
                replay.record(new LocationRecordable(player.getEntityId(), event.getTo()));
            }
        }
    }

    @EventHandler(
            priority = EventPriority.HIGHEST,
            ignoreCancelled = true
    )
    public void sneakEvent(PlayerToggleSneakEvent event) {
        for (Replay replay : recordingReplays) {
            if (!replay.isRecording()) continue;
            Player player = event.getPlayer();
            if (replay.isRecordingPlayer(player)) {
                replay.record(new SneakRecordable(player.getEntityId(), event.isSneaking()));
            }
        }
    }

    @EventHandler(
            priority = EventPriority.HIGHEST,
            ignoreCancelled = true
    )
    public void placeEvent(BlockPlaceEvent event) {
        for (Replay replay : recordingReplays) {
            if (!replay.isRecording()) continue;
            Player player = event.getPlayer();
            if (replay.isRecordingPlayer(player)) {
                replay.record(new BlockRecordable(
                        event.getBlock().getLocation(),
                        event.getBlock().getType(),
                        event.getBlock().getData(),
                        false
                ));
            }
        }
    }

    @EventHandler(
            priority = EventPriority.HIGHEST,
            ignoreCancelled = true
    )
    public void breakEvent(BlockBreakEvent event) {
        for (Replay replay : recordingReplays) {
            if (!replay.isRecording()) continue;
            Player player = event.getPlayer();
            if (replay.isRecordingPlayer(player)) {
                replay.record(new BlockRecordable(
                        event.getBlock().getLocation(),
                        event.getBlock().getType(),
                        event.getBlock().getData(),
                        true
                ));
            }
        }
    }

    @EventHandler(
            priority = EventPriority.HIGHEST,
            ignoreCancelled = true
    )
    public void switchItemEvent(PlayerItemHeldEvent event) {
        for (Replay replay : recordingReplays) {
            if (!replay.isRecording()) continue;
            Player player = event.getPlayer();
            if (replay.isRecordingPlayer(player)) {
                int slot = event.getNewSlot();
                ItemStack itemStack = player.getInventory().getItem(slot);
                if (itemStack == null) {
                    replay.record(new ItemHeldRecordable(
                            player.getEntityId(),
                            Material.AIR,
                            (byte) 0
                    ));
                } else {
                    replay.record(new ItemHeldRecordable(
                            player.getEntityId(),
                            itemStack.getType(),
                            itemStack.getData().getData()
                    ));
                }
            }
        }
    }

    @EventHandler(
            priority = EventPriority.HIGHEST,
            ignoreCancelled = true
    )
    public void damageEvent(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        for (Replay replay : recordingReplays) {
            if (!replay.isRecording()) continue;
            if (replay.isRecordingPlayer(player)) {
                replay.record(new DamageRecordable(player.getEntityId()));
            }
        }
    }
}
