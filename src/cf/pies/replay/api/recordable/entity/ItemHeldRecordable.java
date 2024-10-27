package cf.pies.replay.api.recordable.entity;

import cf.pies.replay.api.ReplayPlayback;
import cf.pies.replay.api.data.SaveRecordable;
import cf.pies.replay.api.data.stream.ReplayInputStream;
import cf.pies.replay.api.data.stream.ReplayOutputStream;
import cf.pies.replay.api.npc.ReplayNPC;
import cf.pies.replay.api.recordable.Recordable;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class ItemHeldRecordable implements Recordable, SaveRecordable {
    private int entityId;
    private Material material;
    private byte data;

    public ItemHeldRecordable() {
    }

    public ItemHeldRecordable(int entityId, Material material, byte data) {
        this.entityId = entityId;
        this.material = material;
        this.data = data;
    }

    @Override
    public void play(ReplayPlayback playback) {
        ReplayNPC npc = playback.getNPC(entityId);
        if (npc == null) return;
        npc.setHolding(new ItemStack(material, 1, data));
    }

    @Override
    public void write(ReplayOutputStream stream) throws IOException {
        stream.writeInt(entityId);
        stream.writeMaterial(material);
        stream.writeByte(data);
    }

    @Override
    public void read(ReplayInputStream stream, World world) throws IOException {
        entityId = stream.readInt();
        material = stream.readMaterial();
        data = stream.readByte();
    }
}
