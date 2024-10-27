package cf.pies.replay.api.recordable.entity;

import cf.pies.replay.api.ReplayPlayback;
import cf.pies.replay.api.data.SaveRecordable;
import cf.pies.replay.api.data.stream.ReplayInputStream;
import cf.pies.replay.api.data.stream.ReplayOutputStream;
import cf.pies.replay.api.npc.ReplayNPC;
import cf.pies.replay.api.recordable.Recordable;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class ArmorRecordable implements Recordable, SaveRecordable {
    private int entityId;
    private ItemStack helmet;
    private ItemStack chestplate;
    private ItemStack leggings;
    private ItemStack boots;

    public ArmorRecordable() {
    }

    public ArmorRecordable(int entityId, ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots) {
        this.entityId = entityId;
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
    }

    @Override
    public void play(ReplayPlayback playback) {
        ReplayNPC npc = playback.getNPC(entityId);
        if (npc == null) return;
        npc.setHelmet(helmet);
        npc.setChestplate(chestplate);
        npc.setLeggings(leggings);
        npc.setBoots(boots);
    }

    @Override
    public void write(ReplayOutputStream stream) throws IOException {
        stream.writeInt(entityId);
        stream.writeItemStack(helmet);
        stream.writeItemStack(chestplate);
        stream.writeItemStack(leggings);
        stream.writeItemStack(boots);
    }

    @Override
    public void read(ReplayInputStream stream, World world) throws IOException {
        entityId = stream.readInt();
        helmet = stream.readItemStack();
        chestplate = stream.readItemStack();
        leggings = stream.readItemStack();
        boots = stream.readItemStack();
    }
}
