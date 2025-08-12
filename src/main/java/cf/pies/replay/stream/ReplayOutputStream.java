package cf.pies.replay.stream;

import cf.pies.replay.type.serialize.Vec3i;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Any data type definitions are in {@link ReplayInputStream} as I do not want to write out every definition twice.
 */
public class ReplayOutputStream extends DataOutputStream implements ReplayStream {
    public ReplayOutputStream(OutputStream out) {
        super(out);
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

    public void writeVarFloat(float f) throws IOException {
        int whole = (int) f;
        int mantissa = (int) ((f - whole) * MANTISSA_LENGTH);

        writeVarInt(whole);
        writeVarInt(mantissa);
    }

    public void writeVec3i(Vec3i vec) throws IOException {
        writeVarInt(vec.getX());
        writeVarInt(vec.getY());
        writeVarInt(vec.getZ());
    }

    public void writeVec3d(Vec3i vec, boolean writeAsVarFloat) throws IOException {
        writeVarInt(vec.getX());
        writeVarInt(vec.getY());
        writeVarInt(vec.getZ());
    }
}
