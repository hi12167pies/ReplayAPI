package cf.pies.replay.buffer;

import cf.pies.replay.recordable.Recordable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import javax.management.ReflectionException;
import java.io.IOException;
import java.util.List;

/**
 * Used to buffer replays to disk.
 * This is not a replay saver/reader, it does not need to be compressed or anything and is temporary.
 * It should also be designed to be read back tick by tick.
 */
public interface ReplayBuffer {
    interface Writer {
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

    /**
     * Represents a frame in the replay.
     * When reading back the structure isn't very defined, so a tick is needed.
     */
    @Getter
    @RequiredArgsConstructor
    class BufferFrame {
        private final int tick;
        private final List<Recordable> recordables;
    }

    interface Reader {
        /**
         * Called when the replay has started.
         * This can be used for any setup.
         */
        void begin() throws Exception;

        /**
         * Reads one tick from the buffer
         * @return Returns null if there is no more data
         */
        @Nullable
        BufferFrame readNextTick() throws Exception;

        /**
         * Ends the buffer.
         * Should flush any remaining data out as well.
         */
        void end();

    }

    /**
     * Gets the reader implementation for the current buffer.
     */
    Reader getReader();

    /**
     * Gets the writer implementation for the current buffer.
     */
    Writer getWriter();
}
