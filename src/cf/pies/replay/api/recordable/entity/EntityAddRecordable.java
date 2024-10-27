package cf.pies.replay.api.recordable.entity;

import cf.pies.replay.api.ReplayPlayback;
import cf.pies.replay.api.data.SaveRecordable;
import cf.pies.replay.api.data.stream.ReplayInputStream;
import cf.pies.replay.api.data.stream.ReplayOutputStream;
import cf.pies.replay.api.entity.EntityInfo;
import cf.pies.replay.api.recordable.Recordable;

import java.io.IOException;

public class EntityAddRecordable implements Recordable, SaveRecordable {
    private int entityId;
    private EntityInfo info;

    public EntityAddRecordable() {
    }

    public EntityAddRecordable(int entityId, EntityInfo info) {
        this.entityId = entityId;
        this.info = info;
    }

    @Override
    public void play(ReplayPlayback playback) {
        playback.spawnNpc(entityId, info);
        playback.getReplay().getEntityInfo().put(entityId, info);
    }

    @Override
    public void write(ReplayOutputStream stream) throws IOException {
        stream.writeInt(entityId);
        stream.writeEntityInfo(info);
    }

    @Override
    public void read(ReplayInputStream stream) throws IOException {
        entityId = stream.readInt();
        info = stream.readEntityInfo();
    }
}
