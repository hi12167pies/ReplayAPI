package cf.pies.replay;

import cf.pies.replay.api.Replay;
import cf.pies.replay.api.entity.EntityInfo;
import cf.pies.replay.api.recordable.impl.LocationRecordable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * I use this file to just test random code
 * this should be removed later
 */
public class Testing {
    Replay replay = new Replay(
            // This location is the origin (where everything is relative to)
            new Location(Bukkit.getWorld("world"), 0, 0, 0)
    );

    public void test() {
        // You will have to run this when you create the replay
        // The ids just have to be unique it doesn't matter what they are (if they are bukkit or random)
        replay.addEntity(1, new EntityInfo("quadflame"));
    }

    // Now you can record the events
    public void event(PlayerMoveEvent event) {

        Player player = event.getPlayer();
        replay.record(new LocationRecordable(player.getEntityId(), event.getTo()));
    }

}
