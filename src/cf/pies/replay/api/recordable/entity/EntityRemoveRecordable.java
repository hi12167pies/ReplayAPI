package cf.pies.replay.api.recordable.entity;

import cf.pies.replay.api.Replay;
import cf.pies.replay.api.ReplayPlayback;
import cf.pies.replay.api.entity.EntityInfo;
import cf.pies.replay.api.recordable.Recordable;

public class EntityRemoveRecordable implements Recordable {
    private final int entityId;

    public EntityRemoveRecordable(int entityId) {
        this.entityId = entityId;
    }


    @Override
    public void onRecord(Replay replay) {

    }

    @Override
    public void play(ReplayPlayback playback) {
        if (playback.getNPC(entityId) != null) {
            playback.removeNPC(entityId);
        }
        playback.getReplay().getEntityInfo().remove(entityId);
    }
}
