package cf.pies.replay.api.recordable.world;

import cf.pies.replay.api.Replay;
import cf.pies.replay.api.ReplayPlayback;
import cf.pies.replay.api.recordable.Recordable;
import cf.pies.replay.api.recordable.UndoRecordable;
import cf.pies.replay.api.utils.NMS;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedSoundEffect;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class BlockRecordable implements Recordable, UndoRecordable {
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
                NMS.updateBlock(player, shiftedLocation, material, data);

                Block.StepSound sound = Block.getById(material.getId()).stepSound;

                NMS.sendPacket(player, new PacketPlayOutNamedSoundEffect(
                        sound.getPlaceSound(), shiftedLocation.getX(), shiftedLocation.getY(), shiftedLocation.getZ(),
                        (sound.getVolume1() + 1.0F) / 2.0F, sound.getVolume2() * 0.8F
                ));
            }
        }
    }

    @Override
    public void undo(ReplayPlayback playback) {
        Location shiftedLocation = location.clone().add(playback.getOrigin());
        for (Player player : playback.getListeners()) {
            NMS.updateBlock(
                    player, shiftedLocation,
                    shiftedLocation.getBlock().getType(),
                    shiftedLocation.getBlock().getData()
            );
        }
    }
}
