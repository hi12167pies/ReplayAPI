package cf.pies.ReplayAPI.core;

import cf.pies.ReplayAPI.api.Replay;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitMain extends JavaPlugin {
    @Override
    public void onEnable() {
    }

    public void testExample() {
        Player player = Bukkit.getPlayer("test");
        Location origin = new Location(Bukkit.getWorld("test"),10,10,10);

        Replay replay = new Replay(player, origin);

        
    }
}
