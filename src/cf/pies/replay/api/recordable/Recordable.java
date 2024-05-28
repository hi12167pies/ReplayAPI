package cf.pies.replay.api.recordable;

import cf.pies.replay.api.Replay;
import cf.pies.replay.api.ReplayPlayback;

public interface Recordable {
    /**
     * Called when event recorded
     */
    void onRecord(Replay replay);

    /**
     * Called on playback
     */
    void play(ReplayPlayback playback);
}
