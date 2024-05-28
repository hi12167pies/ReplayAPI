package cf.pies.replay.plugin;

import cf.pies.replay.api.ReplayAPI;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitMain extends JavaPlugin {
    @Override
    public void onEnable() {
        // If using as a plugin, you do not need to initialize the api
        ReplayAPI.getApi().initialize(this);
    }
}
