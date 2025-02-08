package cf.pies.replay.stream;

import cf.pies.replay.Node;
import cf.pies.replay.ReplayNodeRegistry;
import lombok.RequiredArgsConstructor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ReplayOutputStream extends DataOutputStream {
    private static final int SEGMENT_BITS = 0x7F;
    private static final int CONTINUE_BIT = 0x80;

    private final ReplayNodeRegistry nodeRegistry;

    public ReplayOutputStream(OutputStream out, ReplayNodeRegistry nodeRegistry) {
        super(out);
        this.nodeRegistry = nodeRegistry;
    }

    public void writeNode(Node node) throws IOException {
        int nodeId = nodeRegistry.getNodeId(node.getClass());

        if (nodeId == -1) {
            throw new IOException("Node " + node.getClass() + " has no id in registry.");
        }

        writeVarInt(nodeId);
        node.write(this);
    }

    public void writeVarInt(int value) throws IOException {
        while (true) {
            if ((value & ~SEGMENT_BITS) == 0) {
                writeByte(value);
                return;
            }

            writeByte((value & SEGMENT_BITS) | CONTINUE_BIT);

            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
        }
    }
}
