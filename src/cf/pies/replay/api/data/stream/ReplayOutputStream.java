package cf.pies.replay.api.data.stream;

import org.bukkit.Location;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ReplayOutputStream extends DataOutputStream {
    private static final int SEGMENT_BITS = 0x7F;
    private static final int CONTINUE_BIT = 0x80;

    public ReplayOutputStream(OutputStream out) {
        super(out);
    }

    public void writeByteChar(char c) throws IOException {
        writeByte((byte) c);
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

    public void writeString(String string) throws IOException {
        writeVarInt(string.length());
        for (int i = 0; i < string.length(); i++) {
            writeByteChar(string.charAt(i));
        }
    }

    public void writeLocation(Location location) throws IOException {
        writeDouble(location.getX());
        writeDouble(location.getY());
        writeDouble(location.getZ());
        writeFloat(location.getYaw());
        writeFloat(location.getPitch());
    }
}
