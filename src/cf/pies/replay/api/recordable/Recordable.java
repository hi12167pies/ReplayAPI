package cf.pies.replay.api.recordable;

import cf.pies.replay.api.Replay;

public interface Recordable {
    void onRecord(Replay replay);
}
