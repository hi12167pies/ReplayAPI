package cf.pies.replay.buffer.disk;

import cf.pies.replay.buffer.ReplayBuffer;
import cf.pies.replay.recordable.RecordableStore;

import java.io.File;

/**
 * Saves all replay frames to a file on disk.
 * Once completed reads back object by object
 */
public class DiskBuffer implements ReplayBuffer {
    private final Reader reader;
    private final Writer writer;

    public DiskBuffer(File file, RecordableStore store) {
        reader = null;
        writer = new DiskBufferWriter(file, store);
    }

    @Override
    public Reader getReader() {
        return reader;
    }

    @Override
    public Writer getWriter() {
        return writer;
    }
}
