package cf.pies.replay.api.recordable.entity;

import cf.pies.replay.api.Replay;
import cf.pies.replay.api.ReplayPlayback;
import cf.pies.replay.api.entity.EntityInfo;
import cf.pies.replay.api.recordable.Recordable;

public class EntityAddRecordable implements Recordable {
    private final int entityId;
    private final EntityInfo info;

    public EntityAddRecordable(int entityId, EntityInfo info) {
        this.entityId = entityId;
        this.info = info;
    }


    @Override
    public void onRecord(Replay replay) {

    }

    @Override
    public void play(ReplayPlayback playback) {
        playback.getReplay().getEntityInfo().put(entityId, info);
    }
}
