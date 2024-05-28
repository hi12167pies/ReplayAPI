package cf.pies.replay.api.data.stream;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ReplayInputStream extends DataInputStream {
    private static final int SEGMENT_BITS = 0x7F;
    private static final int CONTINUE_BIT = 0x80;

    public ReplayInputStream(InputStream in) {
        super(in);
    }

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

        return value;
    }

    public String readString() throws IOException {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < readVarInt(); i++) {
            builder.append(readChar());
        }
        return builder.toString();
    }
}
