package cf.pies.replay.api;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;

public class ReplayAPI implements Listener {
    private static final ReplayAPI api = new ReplayAPI();
    public static ReplayAPI getApi() {
        return api;
    }

    // --- instance ---

    private Plugin plugin = null;
    private final Set<Replay> recordingReplays = new HashSet<>();

    /**
     * Sets the plugin for the api
     * @param plugin The plugin to set
     */
    public void initialize(Plugin plugin) {
        if (this.plugin != null) return;
        if (plugin == null) return;
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(new ReplayEvents(), plugin);
    }

    /**
     * @return The current plugin
     */
    public Plugin getPlugin() {
        return plugin;
    }

    public void addRecording(Replay replay) {
        recordingReplays.add(replay);
    }

    public void removeRecording(Replay replay) {
        recordingReplays.remove(replay);
    }

    public Set<Replay> getRecordingReplays() {
        return recordingReplays;
    }
}
