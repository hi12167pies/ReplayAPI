package cf.pies.replay.api.npc;

import cf.pies.replay.api.ReplayPlayback;
import cf.pies.replay.api.entity.ReplaySkin;
import cf.pies.replay.api.utils.NMS;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.WorldSettings;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import top.speedcubing.lib.bukkit.entity.NPC;

import java.util.UUID;

public class SpeedcubingNPC implements ReplayNPC {
    private final ReplayPlayback playback;
    private final NPC npc;
    public SpeedcubingNPC(ReplayPlayback playback, String name) {
        this.playback = playback;
        this.npc = new NPC(name, UUID.randomUUID(), true, false, false);
    }

    @Override
    public void addListener(Player player) {
        npc.addListener(player);
    }

    @Override
    public void removeListener(Player player) {
        npc.removeListener(player);
    }

    @Override
    public void spawn() {
        npc.spawn();
    }

    @Override
    public void setSkin(ReplaySkin skin) {
        npc.setSkin(skin.getValue(), skin.getSignature());
    }

    @Override
    public void remove() {
        npc.despawn();
        npc.delete();
    }

    @Override
    public void swingArm() {
        npc.animation(0);
    }

    @Override
    public void damageAnimation() {
        npc.animation(1);
    }

    @Override
    public void setSneaking(boolean sneaking) {
        npc.setSneaking(sneaking);
    }

    @Override
    public void setLocation(Location location) {
        npc.setLocation(location);
        npc.updateNpcLocation();
    }

    @Override
    public Location getLocation() {
        return new Location(
                npc.entityPlayer.world.getWorld(),
                npc.entityPlayer.locX,
                npc.entityPlayer.locY,
                npc.entityPlayer.locZ,
                npc.entityPlayer.yaw,
                npc.entityPlayer.pitch
        );
    }

    @Override
    public void setHolding(ItemStack stack) {
        npc.setItemInHand(stack);
    }

    @Override
    public void setHelmet(ItemStack stack) {
        npc.setArmor(4, stack);
    }

    @Override
    public void setChestplate(ItemStack stack) {
        npc.setArmor(3, stack);
    }

    @Override
    public void setLeggings(ItemStack stack) {
        npc.setArmor(2, stack);
    }

    @Override
    public void setBoots(ItemStack stack) {
        npc.setArmor(1, stack);
    }

    @Override
    public void setGamemode(GameMode gamemode) {
        // Update npc gamemode
        npc.entityPlayer.playerInteractManager.setGameMode(WorldSettings.EnumGamemode.getById(gamemode.getValue()));
        for (Player player : playback.getListeners()) {
            NMS.sendPacket(player, new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_GAME_MODE, npc.entityPlayer));
        }
    }
}
