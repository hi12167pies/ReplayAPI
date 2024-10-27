package cf.pies.replay.api.recordable.entity;

import cf.pies.replay.api.ReplayPlayback;
import cf.pies.replay.api.data.SaveRecordable;
import cf.pies.replay.api.data.stream.ReplayInputStream;
import cf.pies.replay.api.data.stream.ReplayOutputStream;
import cf.pies.replay.api.recordable.Recordable;

import java.io.IOException;

public class EntityRemoveRecordable implements Recordable, SaveRecordable {
    private int entityId;

    public EntityRemoveRecordable() {
    }

    public EntityRemoveRecordable(int entityId) {
        this.entityId = entityId;
    }

    @Override
    public void play(ReplayPlayback playback) {
        if (playback.getNPC(entityId) != null) {
            playback.removeNPC(entityId);
        }
        playback.getReplay().getEntityInfo().remove(entityId);
    }

    @Override
    public void write(ReplayOutputStream stream) throws IOException {
        stream.writeInt(entityId);
    }

    @Override
    public void read(ReplayInputStream stream) throws IOException {
        entityId = stream.readInt();
    }
}
