package cf.pies.replay.buffer.disk;

import cf.pies.replay.buffer.ReplayBuffer;
import cf.pies.replay.recordable.Recordable;
import cf.pies.replay.recordable.RecordableStore;
import cf.pies.replay.stream.ReplayInputStream;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class DiskBufferReader implements ReplayBuffer.Reader {
    private final File file;
    private final RecordableStore store;

    private ReplayInputStream stream;

    @Override
    public void begin() throws IOException {
        if (!file.exists()) {
            throw new IOException("File does not exist, cannot read " + file.getAbsolutePath());
        }

        stream = new ReplayInputStream(Files.newInputStream(file.toPath()));
    }
    @Override
    public ReplayBuffer.BufferFrame readNextTick() throws Exception {
        if (stream.available() <= 0) {
            // no more data
            return null;
        }

        // tick
        int tick = stream.readUnsignedVarInt();

        // list size
        int recordableSize = stream.readUnsignedVarInt();

        List<Recordable> recordables = new ArrayList<>(recordableSize);

        for (int i = 0; i < recordableSize; i++) {
            // recordable id
            int recordableId = stream.readUnsignedVarInt();

            Class<? extends Recordable> recordableClass = store.getRecordableById(recordableId);
            Recordable recordable = recordableClass.getConstructor().newInstance();

            // recordable data
            recordable.read(stream);

            recordables.add(recordable);
        }

        return new ReplayBuffer.BufferFrame(tick, recordables);
    }

    @Override
    public void end() {
        try {
            stream.close();
        } catch (IOException ignored) {
        }
    }
}
