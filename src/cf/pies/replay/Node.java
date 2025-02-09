package cf.pies.replay;

import cf.pies.replay.stream.NodeOutputStream;

import java.io.IOException;

public interface Node {
    /**
     * Encodes and writes the node to the stream.
     * Must be decoded by the read method.
     */
    void write(NodeOutputStream stream) throws IOException;
}
