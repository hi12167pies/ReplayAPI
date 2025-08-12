package cf.pies.replay.recorder;

import cf.pies.replay.recording.ReplaySession;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * This interface states the basic requirements to be considered a recorder.
 */
public interface Recorder {
    void register(JavaPlugin plugin);

    void add(ReplaySession recorder);
    void remove(ReplaySession recorder);
}
