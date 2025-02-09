package cf.pies.replay.stream;

import cf.pies.replay.Node;
import cf.pies.replay.ReplayNodeRegistry;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class NodeOutputStream extends DataOutputStream {
    private static final int SEGMENT_BITS = 0x7F;
    private static final int CONTINUE_BIT = 0x80;

    private final ReplayNodeRegistry nodeRegistry;

    public NodeOutputStream(OutputStream out, ReplayNodeRegistry nodeRegistry) {
        super(out);
        this.nodeRegistry = nodeRegistry;
    }

    /**
     * Writes the encoded to the node prefixed with the id from the registry.
     */
    public void writeNode(Node node) throws IOException {
        int nodeId = nodeRegistry.getIdFromNode(node.getClass());

        if (nodeId == -1) {
            throw new IOException("Node " + node.getClass() + " has no id in registry.");
        }

        writeVarInt(nodeId);
        node.write(this);
    }

    /**
     * Writes a variable-length encoded string to the stream.
     * This is same as the Minecraft protocol.
     */
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
