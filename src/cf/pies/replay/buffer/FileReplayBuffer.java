package cf.pies.replay.buffer;

import cf.pies.replay.Node;
import cf.pies.replay.ReplayBuffer;
import cf.pies.replay.ReplayNodeRegistry;
import cf.pies.replay.stream.NodeOutputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileReplayBuffer implements ReplayBuffer {
    private final NodeOutputStream stream;

    public FileReplayBuffer(ReplayNodeRegistry nodeRegistry, File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        stream = new NodeOutputStream(fileOutputStream, nodeRegistry);
    }

    @Override
    public void write(Node node) throws IOException {
        stream.writeNode(node);
    }

    public void close() throws IOException {
        stream.close();
    }
}
