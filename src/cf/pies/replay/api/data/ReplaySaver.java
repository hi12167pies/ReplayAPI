package cf.pies.replay.api.data;

import cf.pies.replay.api.Replay;
import cf.pies.replay.api.data.stream.ReplayOutputStream;
import cf.pies.replay.api.entity.EntityInfo;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class ReplaySaver {
    private final Replay replay;
    public ReplaySaver(Replay replay) {
        this.replay = replay;
    }

    public void save(File file) throws IOException {
        save(Files.newOutputStream(file.toPath()));
    }

    public void save(OutputStream stream) throws IOException {
        ReplayOutputStream out = new ReplayOutputStream(stream);

        // codec version
        out.writeInt(1);

        for (Integer id : replay.getEntityInfo().keySet()) {
            EntityInfo info = replay.getEntityInfo().get(id);

            // write id
            out.writeInt(id);

            // write info
            info.write(out);
        }
    }
}
