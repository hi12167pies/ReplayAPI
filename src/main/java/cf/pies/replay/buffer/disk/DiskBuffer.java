package cf.pies.replay.buffer.disk;

import cf.pies.replay.buffer.ReplayBuffer;
import cf.pies.replay.recordable.Recordable;
import cf.pies.replay.recordable.RecordableStore;
import cf.pies.replay.stream.ReplayOutputStream;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * Saves all replay frames to a file on disk.
 * Once completed reads back object by object
 */
public class DiskBuffer implements ReplayBuffer {
    private final Reader reader;
    private final Writer writer;

    public DiskBuffer(File file, RecordableStore store) {
        /**
         * TODO: Implement reader
         */
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
