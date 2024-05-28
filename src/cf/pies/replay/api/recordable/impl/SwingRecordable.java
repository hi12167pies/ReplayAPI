package cf.pies.replay.api.recordable.impl;

import cf.pies.replay.api.Replay;
import cf.pies.replay.api.ReplayPlayback;
import cf.pies.replay.api.recordable.Recordable;

public class SwingRecordable implements Recordable {
    private final int entityId;

    public SwingRecordable(int entityId) {
        this.entityId = entityId;
    }

    @Override
    public void onRecord(Replay replay) {

    }

    @Override
    public void play(ReplayPlayback playback) {
        playback.getNPC(entityId).animation(0);
    }
}
