package cf.pies.replay.api.recordable;

import cf.pies.replay.api.ReplayPlayback;

public interface UndoRecordable {
    /**
     * Undoes whatever the recordable did
     */
    void undo(ReplayPlayback playback);
}
