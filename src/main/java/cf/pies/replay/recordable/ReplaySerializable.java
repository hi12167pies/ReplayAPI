package cf.pies.replay.recordable;

import cf.pies.replay.stream.ReplayInputStream;
import cf.pies.replay.stream.ReplayOutputStream;

import java.io.IOException;

public interface ReplaySerializable {
    /**
     * Read and set objects from the provided stream.
     */
    void read(ReplayInputStream stream) throws IOException;

    /**
     * Writes to a stream from current data.
     */
    void write(ReplayOutputStream stream) throws IOException;
}
