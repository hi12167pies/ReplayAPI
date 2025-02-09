package cf.pies.replay.buffer;

import cf.pies.replay.Node;
import cf.pies.replay.ReplayBuffer;
import cf.pies.replay.ReplayNodeRegistry;
import cf.pies.replay.ReplayTimeManager;
import cf.pies.replay.stream.NodeOutputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileReplayBuffer implements ReplayBuffer {
    private final NodeOutputStream stream;
    private final ReplayTimeManager timeManager;

    private int lastWrittenTime = -1;
    private boolean writtenFirstNode;

    public FileReplayBuffer(ReplayNodeRegistry nodeRegistry, ReplayTimeManager timeManager, File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        stream = new NodeOutputStream(fileOutputStream, nodeRegistry);

        this.timeManager = timeManager;
    }

    @Override
    public void write(Node node) throws IOException {
        if (!timeManager.isTiming()) {
            throw new IOException("Replay is not currently timing.");
        }

        if (timeManager.getTime() > lastWrittenTime) {
            if (writtenFirstNode) {
                // End of list
                stream.writeByte(0x00);
            } else {
                writtenFirstNode = true;
            }

            int time = timeManager.getTime();
            stream.writeVarInt(time);
            lastWrittenTime = time;
        }

        stream.writeNode(node);
    }

    public void close() throws IOException {
        stream.close();
    }
}
