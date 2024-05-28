package cf.pies.replay.api.recordable.impl;

import cf.pies.replay.api.Replay;
import cf.pies.replay.api.ReplayPlayback;
import cf.pies.replay.api.recordable.Recordable;
import top.speedcubing.lib.bukkit.entity.NPC;

public class SneakRecordable implements Recordable {
    private final int entityId;
    private final boolean isSneaking;

    public SneakRecordable(int entityId, boolean isSneaking) {
        this.entityId = entityId;
        this.isSneaking = isSneaking;
    }

    @Override
    public void onRecord(Replay replay) {

    }

    @Override
    public void play(ReplayPlayback playback) {
        NPC npc = playback.getNPC(entityId);
        npc.setSneaking(isSneaking);
    }
}
