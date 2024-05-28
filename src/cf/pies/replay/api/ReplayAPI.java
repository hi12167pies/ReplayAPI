package cf.pies.replay.api;

import cf.pies.replay.api.recordable.impl.LocationRecordable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;

public class ReplayAPI implements Listener {
    private static final ReplayAPI api = new ReplayAPI();

    public static ReplayAPI getApi() {
        return api;
    }

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
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
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

    @EventHandler
    public void moveEvent(PlayerMoveEvent event) {
        for (Replay replay : recordingReplays) {
            if (!replay.isRecording()) continue;
            Player player = event.getPlayer();
            if (replay.hasPlayer(player)) {
                replay.record(new LocationRecordable(player.getEntityId(), event.getTo()));
            }
        }
    }
}