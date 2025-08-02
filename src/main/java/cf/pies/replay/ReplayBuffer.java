package cf.pies.replay;

import cf.pies.replay.recordable.Recordable;

import java.util.List;
import java.util.Map;

/**
 * Used to buffer replays to disk.
 */
public interface ReplayBuffer {
    /**
     * Called when the replay has started.
     * This can be used for any setup.
     */
    default void begin() throws Exception {}

    void submit(int tick, List<Recordable> recordables);
}
