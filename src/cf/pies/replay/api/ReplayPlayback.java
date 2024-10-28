package cf.pies.replay.api;

import cf.pies.replay.api.entity.EntityInfo;
import cf.pies.replay.api.enums.PlaybackState;
import cf.pies.replay.api.event.PlaybackEndEvent;
import cf.pies.replay.api.event.PlaybackStateChangeEvent;
import cf.pies.replay.api.npc.ReplayNPC;
import cf.pies.replay.api.npc.SpeedcubingNPC;
import cf.pies.replay.api.recordable.Recordable;
import cf.pies.replay.api.recordable.UndoRecordable;
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
    private PlaybackState state = PlaybackState.NONE;

    public ReplayPlayback(Plugin plugin, Replay replay, Location origin) {
        this.plugin = plugin;
        this.replay = replay;
        this.origin = origin;
    }

    public void addListener(Player player) {
        listeners.add(player);
    }

    public boolean isListening(Player player) {
        return listeners.contains(player);
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

    public PlaybackState getState() {
        return state;
    }

    private void setState(PlaybackState state) {
        this.state = state;
        Bukkit.getPluginManager().callEvent(new PlaybackStateChangeEvent(this));
    }

    /**
     * Spawns a npc in the replay playback
     * @param entityId Entity id of the new npc
     * @param info The info of the npc
     */
    public void spawnNpc(int entityId, EntityInfo info) {
        // Note: This can be changed for another npc library if needed, or native npc packets.
        ReplayNPC npc = new SpeedcubingNPC(this, info.getName());

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
     * Sets the tick of the playback
     * Note: This does not change anything in the replay beside the variable.
     */
    public void setCurrentTick(int currentTick) {
        this.currentTick = currentTick;
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

        setState(PlaybackState.NONE);
        playTick();
    }

    /**
     * Starts playing the replay
     */
    public void play() {
        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if (currentTick >= replay.getLength()) {
                pause();
                return;
            }
            nextTick();
        }, 0L, 1L);
        setState(PlaybackState.PLAYING);
    }

    /**
     * Pauses the replay at its current state
     */
    public void pause() {
        if (task != -1) {
            Bukkit.getScheduler().cancelTask(task);
            task = -1;
        }
        setState(PlaybackState.PAUSED);
    }

    /**
     * Ends the replay completely
     */
    public void end() {
        if (state == PlaybackState.PLAYING) {
            pause();
        }

        // Replay entire replay, removing everything
        for (List<Recordable> recordableList : getReplay().getReplayData().values()) {
            for (Recordable recordable : recordableList) {
                if (recordable instanceof UndoRecordable) {
                    ((UndoRecordable) recordable).undo(this);
                }
            }
        }

        synchronized (npcs) {
            for (int id : npcs.keySet()) {
                removeNPC(id);
            }
            npcs.clear();
        }

        Bukkit.getPluginManager().callEvent(new PlaybackEndEvent(this));
        setState(PlaybackState.ENDED);
    }

    /**
     * Plays one tick of the replay
     */
    public void nextTick() {
        currentTick++;
        playTick();
    }

    /**
     * Plays the current tick of the replay.
     * Not recommended to use, use nextTick or lastTick instead.
     */
    public void playTick() {
        if (replay.getReplayData().containsKey(currentTick)) {
            List<Recordable> data = replay.getReplayData().get(currentTick);
            for (Recordable recordable : data) {
                recordable.play(this);
            }
        }
    }

    /**
     * Reverses one tick of the replay
     */
    public void lastTick() {
        // undo current tick
        if (replay.getReplayData().containsKey(currentTick)) {
            List<Recordable> data = replay.getReplayData().get(currentTick);
            for (Recordable recordable : data) {
                if (recordable instanceof UndoRecordable) {
                    ((UndoRecordable) recordable).undo(this);
                }
            }
        }
        // remove one tick
        currentTick--;
        // play next tick
        if (replay.getReplayData().containsKey(currentTick)) {
            List<Recordable> data = replay.getReplayData().get(currentTick);
            for (Recordable recordable : data) {
                recordable.play(this);
            }
        }
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
