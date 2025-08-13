package cf.pies.replay.saver;

import cf.pies.replay.recording.ReplaySession;

public interface ReplaySaver {
    void save(ReplaySession session);
}