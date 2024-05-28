package cf.pies.replay.api.data;

import cf.pies.replay.api.Replay;
import cf.pies.replay.api.data.codec.ReplayCodec1;
import cf.pies.replay.api.data.stream.ReplayInputStream;
import cf.pies.replay.api.exception.UnknownRecordableTypeException;
import cf.pies.replay.api.exception.UnsupportedCodecException;
import org.bukkit.World;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ReplayLoader {
    private final ReplayInputStream in;

    public ReplayLoader(InputStream stream, World world) {
        in = new ReplayInputStream(stream, world);
    }

    public ReplayLoader(File file, World world) throws IOException {
        this(Files.newInputStream(file.toPath()), world);
    }

    /**
     * Loads into the replay object
     */
    public void load(Replay replay) throws IOException, UnsupportedCodecException, UnknownRecordableTypeException, IllegalAccessException, InstantiationException {
        int codecVersion = in.readVarInt();

        switch (codecVersion) {
            case 1:
                new ReplayCodec1().read(replay, in);
                break;
            default:
                throw new UnsupportedCodecException(codecVersion);
        }

        in.close();
    }
}
