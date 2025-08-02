package cf.pies.replay.buffer;

import cf.pies.replay.ReplayBuffer;
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
public class DiskBuffer implements ReplayBuffer {
    private final File file;
    private ObjectOutputStream stream;

    @Override
    public void begin() throws IOException {
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
}
