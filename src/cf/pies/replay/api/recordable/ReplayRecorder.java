package cf.pies.replay.api.recordable;

import cf.pies.replay.api.Replay;
import cf.pies.replay.api.recordable.impl.LocationRecordable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ReplayRecorder implements Listener {
    private final Replay replay;

    public ReplayRecorder(JavaPlugin plugin, Replay replay) {
        this.replay = replay;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
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
