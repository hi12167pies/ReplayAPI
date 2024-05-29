package cf.pies.replay.api.recordable.entity;

import cf.pies.replay.api.Replay;
import cf.pies.replay.api.ReplayPlayback;
import cf.pies.replay.api.npc.ReplayNPC;
import cf.pies.replay.api.recordable.Recordable;

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
        ReplayNPC npc = playback.getNPC(entityId);
        if (npc == null) return;
        npc.setSneaking(isSneaking);
    }
}
