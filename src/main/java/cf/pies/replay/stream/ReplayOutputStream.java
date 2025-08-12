package cf.pies.replay.stream;

import cf.pies.replay.type.serialize.MaterialInfo;
import cf.pies.replay.type.serialize.Vec3f;
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

    private int encodeZigZag32(int n) {
        return (n << 1) ^ (n >> 31);
    }

    public void writeVarInt(int value) throws IOException {
        writeUnsignedVarInt(encodeZigZag32(value));
    }

    public void writeUnsignedVarInt(int value) throws IOException {
        value = encodeZigZag32(value);
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
        writeUnsignedVarInt(mantissa);
    }

    public void writeVec3i(Vec3i vec) throws IOException {
        writeVarInt(vec.getX());
        writeVarInt(vec.getY());
        writeVarInt(vec.getZ());
    }

    public void writeVec3f(Vec3f vec) throws IOException {
        writeVarFloat(vec.getX());
        writeVarFloat(vec.getY());
        writeVarFloat(vec.getZ());
    }

    public void writeMaterialInfo(MaterialInfo info) throws IOException {
        this.writeVarInt(info.getId());
        this.writeByte(info.getData());
    }
}
