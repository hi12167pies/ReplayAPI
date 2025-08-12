package cf.pies.replay;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nz.blair.npcs.NpcsApi;
import nz.blair.npcs.NpcsProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Handles only plugin related enable and disable stuff.
 */
@RequiredArgsConstructor
public class ReplayAPI {
    @Getter
    private final JavaPlugin plugin;
    private NpcsProvider npcsProvider;

    public void enable() {
        npcsProvider = new NpcsProvider(plugin);
    }

    public void disable() {
        npcsProvider.disable();
    }

    public NpcsApi getNpcsApi() {
        return npcsProvider.getApi();
    }
}
