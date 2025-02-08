package cf.pies.replay;

import cf.pies.replay.stream.ReplayOutputStream;

import java.io.IOException;

public interface Node {
    void write(ReplayOutputStream stream) throws IOException;
}
