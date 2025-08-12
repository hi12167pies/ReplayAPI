package cf.pies.replay.stream;

import cf.pies.replay.type.serialize.MaterialInfo;
import cf.pies.replay.type.serialize.Vec3f;
import cf.pies.replay.type.serialize.Vec3i;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ReplayInputStream extends DataInputStream implements ReplayStream {

    public ReplayInputStream(InputStream in) {
        super(in);
    }

    /**
     * Flips the signed bit to other side of binary
     * e.g.
     * <code>
     * Default: 1100
     *          ^ Signed bit
     * Zigzag:
     *         0101
     *            ^ Signed bit
     * </code>
     */
    private int decodeZigZag32(int n) {
        return (n >>> 1) ^ -(n & 1);
    }

    /**
     * Same a Mojang VarInt defined on protocol wiki
     * <a href="https://minecraft.wiki/w/Java_Edition_protocol/Data_types#VarInt_and_VarLong">VarInt reference</a>
     */
    public int readVarInt() throws IOException {
        int value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = readByte();
            value |= (currentByte & SEGMENT_BITS) << position;

            if ((currentByte & CONTINUE_BIT) == 0) break;

            position += 7;

            if (position >= 32) throw new RuntimeException("VarInt is too big");
        }

        return decodeZigZag32(value);
    }

    /**
     * Custom VarFloat, VarFloat works by splitting in two parts:
     * - whole                   (e.g. 123.456 -> 123)
     * - mantissa (decimal part) (e.g. 123.456 -> 456)
     * Then the value will be saved as two VarInts
     */
    public float readVarFloat() throws IOException {
        int whole = readVarInt();
        int mantissa = readVarInt();

        return (whole + ((float) mantissa / MANTISSA_LENGTH));
    }

    public Vec3i readVec3i() throws IOException {
        int x = readVarInt();
        int y = readVarInt();
        int z = readVarInt();

        return new Vec3i(x, y, z);
    }

    public Vec3f readVec3f() throws IOException {
        float x = readVarFloat();
        float y = readVarFloat();
        float z = readVarFloat();

        return new Vec3f(x, y, z);
    }

    public MaterialInfo readMaterialInfo() throws IOException {
        int id = readVarInt();
        byte data = readByte();

        return new MaterialInfo(id, data);
    }
}
