package cf.pies.replay.api.recordable;

import cf.pies.replay.api.Replay;
import cf.pies.replay.api.ReplayPlayback;

public interface Recordable {
    void onRecord(Replay replay);
    void play(ReplayPlayback playback);
}
