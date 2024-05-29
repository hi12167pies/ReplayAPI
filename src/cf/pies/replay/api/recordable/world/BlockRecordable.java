package cf.pies.replay.api.recordable.world;

import cf.pies.replay.api.Replay;
import cf.pies.replay.api.ReplayPlayback;
import cf.pies.replay.api.recordable.Recordable;
import cf.pies.replay.api.utils.NMS;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldEvent;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.CraftSound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class BlockRecordable implements Recordable {
    private Location location;
    private final Material material;
    private final byte data;
    private final boolean isBroken;

    public BlockRecordable(Location location, Material material, byte data, boolean isBroken) {
        this.location = location;
        this.material = material;
        this.data = data;
        this.isBroken = isBroken;
    }

    @Override
    public void onRecord(Replay replay) {
        location = location.clone().subtract(replay.getOrigin());
    }

    @Override
    public void play(ReplayPlayback playback) {
        Location shiftedLocation = location.clone().add(playback.getOrigin());
        for (Player player : playback.getListeners()) {
            if (isBroken) {
                NMS.updateBlock(player, shiftedLocation, Material.AIR, (byte) 0);
                NMS.sendPacket(player, new PacketPlayOutWorldEvent(2001, NMS.toNMS(shiftedLocation), NMS.getCombined(material, data), false));
            } else {
                // TODO Place sound
                NMS.updateBlock(player, shiftedLocation, material, data);
            }
        }
    }
}
