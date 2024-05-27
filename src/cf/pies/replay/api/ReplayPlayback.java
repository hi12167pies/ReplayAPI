package cf.pies.replay.api;

import cf.pies.replay.api.entity.EntityInfo;
import cf.pies.replay.api.recordable.EntityRecordable;
import cf.pies.replay.api.recordable.Recordable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import top.speedcubing.lib.bukkit.entity.NPC;

import java.util.*;

/**
 * Use this class to replay a replay to a player
 */
public class ReplayPlayback {
    private final JavaPlugin plugin;
    private final Replay replay;
    private final Location origin;
    private final HashMap<Integer, NPC> npcs = new HashMap<>();
    private final Set<Player> listeners = new HashSet<>();
    private int task = -1;
    private int currentTick = 0;

    public ReplayPlayback(JavaPlugin plugin, Replay replay, Location origin) {
        this.plugin = plugin;
        this.replay = replay;
        this.origin = origin;
    }

    public void addListener(Player player) {
        listeners.add(player);
    }

    public NPC getNPC(int id) {
        return npcs.getOrDefault(id, null);
    }

    public Location getOrigin() {
        return origin;
    }

    public Replay getReplay() {
        return replay;
    }

    public void start() {
        for (Integer id : replay.getEntityInfo().keySet()) {
            EntityInfo info = replay.getEntityInfo().get(id);

            NPC npc = new NPC(info.getName(), UUID.randomUUID(), true, false, false);
            npc.addListener(listeners);

            npc.spawn();

            npcs.put(id, npc);
        }

        currentTick = 0;
        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if (currentTick == replay.getLength()) {
                this.end();
                return;
            }
            if (!replay.getReplayData().containsKey(currentTick)) return;
            List<Recordable> data = replay.getReplayData().get(currentTick);
            for (Recordable recordable : data) {
                recordable.play(this);
            }
            currentTick++;
        }, 0L, 1L);
    }

    public void end() {
        if (task != -1) {
            Bukkit.getScheduler().cancelTask(task);
            task = -1;
        }
        for (NPC npc : npcs.values()) {
            npc.despawn();
            npc.delete();
        }
        npcs.clear();
    }
}
