package cf.pies.replay.buffer;

import cf.pies.replay.recordable.Recordable;

import java.util.List;

/**
 * Used to buffer replays to disk.
 * This is not a replay saver, it does not need to be compressed or anything and is temporary.
 * It should also be designed to be read back tick by tick.
 */
public interface ReplayBufferWriter {
    /**
     * Called when the replay has started.
     * This can be used for any setup.
     */
    void begin() throws Exception;

    /**
     * Once a tick is completed, it will need to be buffered away to disk, memory, or another place.
     * This method will be called when it is time.
     */
    void submit(int tick, List<Recordable> recordables);

    /**
     * Ends the buffer.
     * Should flush any remaining data out as well.
     */
    void end();
}
