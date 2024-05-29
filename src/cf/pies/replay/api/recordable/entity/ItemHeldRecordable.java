package cf.pies.replay.api.recordable.entity;

import cf.pies.replay.api.Replay;
import cf.pies.replay.api.ReplayPlayback;
import cf.pies.replay.api.recordable.Recordable;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import top.speedcubing.lib.bukkit.entity.NPC;

public class ItemHeldRecordable implements Recordable {
    private final int entityId;
    private final Material material;
    private final byte data;

    public ItemHeldRecordable(int entityId, Material material, byte data) {
        this.entityId = entityId;
        this.material = material;
        this.data = data;
    }

    @Override
    public void onRecord(Replay replay) {

    }

    @Override
    public void play(ReplayPlayback playback) {
        NPC npc = playback.getNPC(entityId);
        if (npc == null) return;
        npc.setItemInHand(new ItemStack(material, 1, data));
    }
}
