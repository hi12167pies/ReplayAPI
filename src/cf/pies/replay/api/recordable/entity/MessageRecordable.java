package cf.pies.replay.api.recordable.entity;

import cf.pies.replay.api.Replay;
import cf.pies.replay.api.ReplayPlayback;
import cf.pies.replay.api.recordable.Recordable;
import org.bukkit.entity.Player;

public class MessageRecordable implements Recordable {
    private final String message;

    public MessageRecordable(String message) {
        this.message = message;
    }

    @Override
    public void onRecord(Replay replay) {

    }

    @Override
    public void play(ReplayPlayback playback) {
        for (Player listener : playback.getListeners()) {
            listener.sendMessage(message);
        }
    }
}
