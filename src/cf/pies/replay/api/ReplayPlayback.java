package cf.pies.replay.api;

import cf.pies.replay.api.entity.EntityInfo;
import cf.pies.replay.api.npc.ReplayNPC;
import cf.pies.replay.api.npc.SpeedcubingNPC;
import cf.pies.replay.api.recordable.Recordable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Use this class to replay a replay to a player
 */
public class ReplayPlayback {
    private final Plugin plugin;
    private final Replay replay;
    private final Location origin;
    private final HashMap<Integer, ReplayNPC> npcs = new HashMap<>();
    private final Set<Player> listeners = new HashSet<>();
    private int task = -1;
    private int currentTick = 0;

    public ReplayPlayback(Plugin plugin, Replay replay, Location origin) {
        this.plugin = plugin;
        this.replay = replay;
        this.origin = origin;
    }

    public void addListener(Player player) {
        listeners.add(player);
    }

    public ReplayNPC getNPC(int id) {
        return npcs.getOrDefault(id, null);
    }

    public Location getOrigin() {
        return origin;
    }

    public Replay getReplay() {
        return replay;
    }

    /**
     * Spawns a npc in the replay playback
     * @param entityId Entity id of the new npc
     * @param info The info of the npc
     */
    public void spawnNpc(int entityId, EntityInfo info) {
        ReplayNPC npc = new SpeedcubingNPC(info.getName());

        for (Player listener : listeners) {
            npc.addListener(listener);
        }

        if (info.hasSkin()) {
            npc.setSkin(info.getSkin());
        }

        npc.setLocation(origin);
        npc.spawn();

        npcs.put(entityId, npc);
    }

    /**
     * Removes a npc in the playback
     * @param id The entity id of the npc
     */
    public void removeNPC(int id) {
        ReplayNPC npc = npcs.get(id);
        npc.remove();
        npcs.remove(id);
    }

    /**
     * @return The players currently watching the playback
     */
    public Set<Player> getListeners() {
        return listeners;
    }

    /**
     * @return The current tick the playback is at
     */
    public int getCurrentTick() {
        return currentTick;
    }

    /**
     * Setups the replay npcs and other stuff
     */
    public void setup() {
        currentTick = 0;

        for (Integer id : replay.getEntityInfo().keySet()) {
            EntityInfo info = replay.getEntityInfo().get(id);
            spawnNpc(id, info);
        }
    }

    /**
     * Starts playing the replay
     */
    public void play() {
        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if (currentTick >= replay.getLength()) {
                this.end();
                return;
            }
            nextTick();
        }, 0L, 1L);
    }

    /**
     * Pauses the replay at its current state
     */
    public void pause() {
        if (task != -1) {
            Bukkit.getScheduler().cancelTask(task);
            task = -1;
        }
    }

    /**
     * Ends the replay completely
     */
    public void end() {
        pause();
        for (int id : npcs.keySet()) {
            removeNPC(id);
        }
    }

    /**
     * Plays one tick of the replay
     */
    public void nextTick() {
        if (replay.getReplayData().containsKey(currentTick)) {
            List<Recordable> data = replay.getReplayData().get(currentTick);
            for (Recordable recordable : data) {
                recordable.play(this);
            }
        }
        currentTick++;
    }

    /**
     * Sends a message to everyone listening to the replay, should only be used for testing
     * @param message Message to send
     */
    public void messageListeners(String message) {
        for (Player listener : listeners) {
            listener.sendMessage(message);
        }
    }
}
