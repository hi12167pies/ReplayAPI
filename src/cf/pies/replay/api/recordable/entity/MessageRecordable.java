package cf.pies.replay.api.recordable.entity;

import cf.pies.replay.api.ReplayPlayback;
import cf.pies.replay.api.data.SaveRecordable;
import cf.pies.replay.api.data.stream.ReplayInputStream;
import cf.pies.replay.api.data.stream.ReplayOutputStream;
import cf.pies.replay.api.recordable.Recordable;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.IOException;

public class MessageRecordable implements Recordable, SaveRecordable {
    private String message;

    public MessageRecordable() {
    }

    public MessageRecordable(String message) {
        this.message = message;
    }

    @Override
    public void play(ReplayPlayback playback) {
        for (Player listener : playback.getListeners()) {
            listener.sendMessage(message);
        }
    }

    @Override
    public void write(ReplayOutputStream stream) throws IOException {
        stream.writeString(message);
    }

    @Override
    public void read(ReplayInputStream stream, World world) throws IOException {
        message = stream.readString();
    }
}
