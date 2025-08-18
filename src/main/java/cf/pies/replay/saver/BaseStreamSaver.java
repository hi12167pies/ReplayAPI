package cf.pies.replay.saver;

import cf.pies.replay.buffer.ReplayBuffer;
import cf.pies.replay.recordable.Recordable;
import cf.pies.replay.recording.ReplaySession;
import cf.pies.replay.stream.ReplayOutputStream;
import cf.pies.replay.type.EntityMetadata;

import java.io.OutputStream;
import java.util.List;

public abstract class BaseStreamSaver implements ReplaySaver {
    protected static final int BASE_VERSION = 1;

    /**
     * A private api to write the replay to a stream.
     * @param extensionVersion The base format has its own versioning, however the custom recordables need their own format too.
     */
    protected void writeReplay(ReplaySession session, OutputStream outputStream, int extensionVersion) throws Exception {
        ReplayOutputStream stream = new ReplayOutputStream(outputStream);

        // Version metadata
        stream.writeUnsignedVarInt(BASE_VERSION);
        stream.writeUnsignedVarInt(extensionVersion);

        // Replay metadata
        int length = session.getTime().getCurrentTick();
        stream.writeUnsignedVarInt(length);

        // Entity metadata
        EntityMetadata[] metadata = session.getEntities();

        stream.writeUnsignedVarInt(metadata.length);
        for (EntityMetadata entity : metadata) {
            stream.writeVarInt(entity.getRecId());
            stream.writeUnsignedVarInt(entity.getType().getId());
        }

        ReplayBuffer.Reader bufferReader = session.getBuffer().getReader();

        bufferReader.begin();

        ReplayBuffer.BufferFrame frame;
        while ((frame = bufferReader.readNextTick()) != null) {
            // Current tick
            stream.writeUnsignedVarInt(frame.getTick());

            List<Recordable> recordables = frame.getRecordables();

            stream.writeUnsignedVarInt(recordables.size());
            for (Recordable recordable : recordables) {
                writeRecordable(recordable, outputStream, extensionVersion);
            }
        }

        // This will read as tick -1, not possible and knows it the end.
        stream.writeVarInt(-1);

        bufferReader.end();
    }


    /**
     * This is expected to implement yourself, so you can do compression yourself.
     * The base structure is saved in the {@link BaseStreamSaver#writeReplay(ReplaySession, OutputStream, int)} but you need to compress recordables yourself.
     * You can just use .write if you don't mind.
     * @param extensionVersion This value is provided from the caller as the version of the extension, or the extender of this class.
     */
    protected abstract void writeRecordable(Recordable recordable, OutputStream outputStream, int extensionVersion);
}
