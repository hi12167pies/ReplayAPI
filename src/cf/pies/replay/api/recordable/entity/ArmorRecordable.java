package cf.pies.replay.api.recordable.entity;

import cf.pies.replay.api.Replay;
import cf.pies.replay.api.ReplayPlayback;
import cf.pies.replay.api.npc.ReplayNPC;
import cf.pies.replay.api.recordable.Recordable;
import org.bukkit.inventory.ItemStack;

public class ArmorRecordable implements Recordable {
    private final int entityId;
    private final ItemStack helmet;
    private final ItemStack chestplate;
    private final ItemStack leggings;
    private final ItemStack boots;

    public ArmorRecordable(int entityId, ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots) {
        this.entityId = entityId;
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
    }


    @Override
    public void onRecord(Replay replay) {

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
}
