package cf.pies.replay.api.data;

import cf.pies.replay.api.Replay;
import cf.pies.replay.api.data.stream.ReplayInputStream;
import cf.pies.replay.api.data.stream.ReplayOutputStream;

import java.io.IOException;

public interface ReplayCodec {
    void read(Replay replay, ReplayInputStream in) throws IOException;
    void write(Replay replay, ReplayOutputStream out) throws IOException;
}
