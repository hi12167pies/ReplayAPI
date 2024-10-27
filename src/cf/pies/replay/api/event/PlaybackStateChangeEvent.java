package cf.pies.replay.api.event;

import cf.pies.replay.api.ReplayPlayback;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlaybackStateChangeEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }

    private final ReplayPlayback playback;

    public PlaybackStateChangeEvent(ReplayPlayback playback) {
        this.playback = playback;
    }

    public ReplayPlayback getPlayback() {
        return playback;
    }
}
