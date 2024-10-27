package cf.pies.replay.api.data;

import cf.pies.replay.api.data.stream.ReplayInputStream;
import cf.pies.replay.api.data.stream.ReplayOutputStream;
import org.bukkit.World;

import java.io.IOException;

public interface SaveRecordable {
    /**
     * Writes out the data of the recordable to a stream.
     */
    void write(ReplayOutputStream stream) throws IOException;

    /**
     * Reads the data into the recordables.
     */
    void read(ReplayInputStream stream, World world) throws IOException;
}
