package cf.pies.replay.buffer.disk;

import cf.pies.replay.buffer.ReplayBufferWriter;
import cf.pies.replay.recordable.Recordable;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.List;

/**
 * Saves all replay frames to a file on disk.
 * Once completed reads back object my object
 */
@RequiredArgsConstructor
public class DiskBuffer implements ReplayBufferWriter {
    private final File file;
    private ObjectOutputStream stream;

    @Override
    public void begin() throws IOException {
        if (file.exists()) {
            if (!file.delete()) {
                throw new IOException("Failed to delete existing file: " + file.getAbsolutePath());
            }
        }

        if (!file.createNewFile()) {
            throw new IOException("Failed to create new file: " + file.getAbsolutePath());
        }

        stream = new ObjectOutputStream(Files.newOutputStream(file.toPath()));
    }

    @Override
    public void submit(int tick, List<Recordable> recordables) {
        try {
            stream.write(tick);
            for (Recordable recordable : recordables) {
                stream.writeObject(recordable);
            }
        } catch (IOException err) {
            err.printStackTrace(System.err);
        }
    }

    @Override
    public void end() {
        try {
            stream.close();
        } catch (IOException ignored) {
        }
    }
}
