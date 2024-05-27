package cf.pies.replay.api;

import cf.pies.replay.api.recordable.impl.LocationRecordable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

// need a better way of doing this that won't register events
public class ReplayRecorder implements Listener {
    private final Replay replay;
    private final JavaPlugin plugin;

    public ReplayRecorder(JavaPlugin plugin, Replay replay) {
        this.replay = replay;
        this.plugin = plugin;
    }

    public void register() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void unregister() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler(
            priority = EventPriority.MONITOR,
            ignoreCancelled = true
    )
    public void moveEvent(PlayerMoveEvent event) {
        if (!replay.isRecording()) return;
        Player player = event.getPlayer();
        if (replay.hasPlayer(player)) {
            replay.record(new LocationRecordable(player.getEntityId(), event.getTo()));
        }
    }
}
