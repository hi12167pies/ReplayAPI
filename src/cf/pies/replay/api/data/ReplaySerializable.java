package cf.pies.replay.api.data;

import cf.pies.replay.api.data.stream.ReplayInputStream;
import cf.pies.replay.api.data.stream.ReplayOutputStream;

import java.io.IOException;

public interface ReplaySerializable {
    void write(ReplayOutputStream out) throws IOException;
    void read(ReplayInputStream in) throws IOException;
}
