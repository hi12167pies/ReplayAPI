package cf.pies.replay.buffer;

import cf.pies.replay.recordable.Recordable;

import java.util.List;

/**
 * Used to buffer replays to disk.
 */
public interface ReplayBuffer {
    /**
     * Called when the replay has started.
     * This can be used for any setup.
     */
    void begin() throws Exception;

    void submit(int tick, List<Recordable> recordables);

    /**
     * Ends the buffer.
     * Should flush any remaining data out as well.
     */
    void end();
}
