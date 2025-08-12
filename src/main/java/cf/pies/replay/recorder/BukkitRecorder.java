package cf.pies.replay.recorder;

import cf.pies.replay.recordable.entity.LocationRecordable;
import cf.pies.replay.recordable.world.BlockChangeRecordable;
import cf.pies.replay.recordable.world.BlockRemoveRecordable;
import cf.pies.replay.recording.ReplaySession;
import cf.pies.replay.type.serialize.MaterialInfo;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class BukkitRecorder implements Recorder, Listener {
    private final Set<ReplaySession> replays = new HashSet<>();

    public void register(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public void add(ReplaySession recorder) {
        replays.add(recorder);
    }

    @Override
    public void remove(ReplaySession recorder) {
        replays.remove(recorder);
    }

    /**
     * Checks if the provided entity should be recorded in the replay.
     * Checks if the entity is in the replay and if the replay is active itself.
     */
    private boolean isRecordableEntity(int entityId, ReplaySession replay) {
        return replay.isActive() && replay.isEntityRecorded(entityId);
    }

    /**
     * Records {@link cf.pies.replay.recordable.entity.LocationRecordable}
     */
    @EventHandler(
            priority = EventPriority.MONITOR,
            ignoreCancelled = true
    )
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        int entityId = player.getEntityId();

        Location location = event.getTo();
        for (ReplaySession replay : replays) {
            if (!isRecordableEntity(entityId, replay)) continue;

            int recId = replay.getEntityIdToRecId(entityId);
            replay.record(new LocationRecordable(
                    recId,
                    replay.getOrigin().shiftFloat(location),
                    location.getYaw(),
                    location.getPitch()
            ));
        }
    }

    /**
     * Records {@link cf.pies.replay.recordable.world.BlockChangeRecordable}
     */
    @EventHandler(
            priority = EventPriority.MONITOR,
            ignoreCancelled = true
    )
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        int entityId = player.getEntityId();

        Block block = event.getBlockPlaced();
        Location location = block.getLocation();

        for (ReplaySession replay : replays) {
            if (!isRecordableEntity(entityId, replay)) continue;

            replay.record(new BlockChangeRecordable(
                    replay.getOrigin().shiftInt(location),
                    MaterialInfo.from(block)
            ));
        }
    }

    /**
     * Records {@link cf.pies.replay.recordable.world.BlockChangeRecordable}
     */
    @EventHandler(
            priority = EventPriority.MONITOR,
            ignoreCancelled = true
    )
    public void onPlace(BlockBreakEvent event) {
        Player player = event.getPlayer();
        int entityId = player.getEntityId();

        Block block = event.getBlock();
        Location location = block.getLocation();

        for (ReplaySession replay : replays) {
            if (!isRecordableEntity(entityId, replay)) continue;

            replay.record(new BlockRemoveRecordable(
                    replay.getOrigin().shiftInt(location)
            ));
        }
    }
}
