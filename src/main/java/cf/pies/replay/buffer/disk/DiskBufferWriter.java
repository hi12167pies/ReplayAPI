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

@RequiredArgsConstructor
public class DiskBufferWriter implements ReplayBuffer.Writer {
    private final File file;
    private final RecordableStore store;

    private ReplayOutputStream stream;

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

        stream = new ReplayOutputStream(Files.newOutputStream(file.toPath()));
    }

    @Override
    public void submit(int tick, List<Recordable> recordables) {
        try {
            System.out.println(tick + " submitted " + recordables.size());
            // tick
            stream.writeUnsignedVarInt(tick);

            // list size

        } catch (IOException err) {
            err.printStackTrace(System.out);
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