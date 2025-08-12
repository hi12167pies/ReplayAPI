package cf.pies.replay.npc;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface ReplayNpc {
    void create(String name, Location location);
    void delete();

    void addListener(Player player);
    void removeListener(Player player);
}
