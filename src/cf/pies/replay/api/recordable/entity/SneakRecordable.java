package cf.pies.replay.api.recordable.entity;

import cf.pies.replay.api.ReplayPlayback;
import cf.pies.replay.api.data.SaveRecordable;
import cf.pies.replay.api.data.stream.ReplayInputStream;
import cf.pies.replay.api.data.stream.ReplayOutputStream;
import cf.pies.replay.api.npc.ReplayNPC;
import cf.pies.replay.api.recordable.Recordable;

import java.io.IOException;

public class SneakRecordable implements Recordable, SaveRecordable {
    private int entityId;
    private boolean isSneaking;

    public SneakRecordable() {
    }

    public SneakRecordable(int entityId, boolean isSneaking) {
        this.entityId = entityId;
        this.isSneaking = isSneaking;
    }

    @Override
    public void play(ReplayPlayback playback) {
        ReplayNPC npc = playback.getNPC(entityId);
        if (npc == null) return;
        npc.setSneaking(isSneaking);
    }

    @Override
    public void write(ReplayOutputStream stream) throws IOException {
        stream.writeInt(entityId);
        stream.writeBoolean(isSneaking);
    }

    @Override
    public void read(ReplayInputStream stream) throws IOException {
        entityId = stream.readInt();
        isSneaking = stream.readBoolean();;
    }
}
