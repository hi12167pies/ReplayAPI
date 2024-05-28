package cf.pies.replay.api.data;

import cf.pies.replay.api.Replay;
import cf.pies.replay.api.ReplayAPI;
import cf.pies.replay.api.data.stream.ReplayOutputStream;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class ReplaySaver {
    private final Replay replay;
    public ReplaySaver(Replay replay) {
        this.replay = replay;
    }

    /**
     * Saves the replay to a file
     */
    public void save(File file) throws IOException {
        save(Files.newOutputStream(file.toPath()));
    }

    /**
     * Saves the replay to the stream
     */
    public void save(OutputStream stream) throws IOException {
        ReplayOutputStream out = new ReplayOutputStream(stream);

        // codec version
        out.writeInt(1);

        ReplayAPI.getApi().getCurrentCodec().write(replay, out);

        out.close();
    }
}
