package cf.pies.replay.api.recordable.world;

import cf.pies.replay.api.Replay;
import cf.pies.replay.api.ReplayPlayback;
import cf.pies.replay.api.data.SaveRecordable;
import cf.pies.replay.api.data.stream.ReplayInputStream;
import cf.pies.replay.api.data.stream.ReplayOutputStream;
import cf.pies.replay.api.recordable.Recordable;
import cf.pies.replay.api.recordable.UndoRecordable;
import cf.pies.replay.api.utils.NMS;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedSoundEffect;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.IOException;

public class BlockRecordable implements Recordable, UndoRecordable, SaveRecordable {
    private Location location;
    private Material material;
    private byte data;
    private boolean isBroken;

    public BlockRecordable() {
    }

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
        if (!location.getWorld().equals(playback.getOrigin().getWorld())) {
            location.setWorld(playback.getOrigin().getWorld());
        }
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

    @Override
    public void write(ReplayOutputStream stream) throws IOException {
        stream.writeLocation(location);
        stream.writeMaterial(material);
        stream.writeByte(data);
        stream.writeBoolean(isBroken);
    }

    @Override
    public void read(ReplayInputStream stream, World world) throws IOException {
        location = stream.readLocation(world);
        material = stream.readMaterial();
        data = stream.readByte();
        isBroken = stream.readBoolean();
    }
}
