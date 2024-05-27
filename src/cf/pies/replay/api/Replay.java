package cf.pies.replay.api;

import cf.pies.replay.api.entity.EntityInfo;
import cf.pies.replay.api.recordable.Recordable;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Replay {
    private int startTick;
    private int length;
    private boolean recording = false;
    private final Location origin;
    private final Map<Integer, List<Recordable>> replayData = new HashMap<>();
    private final Map<Integer, EntityInfo> entityInfo = new HashMap<>();

    public Replay(Location origin) {
        this.origin = origin;
    }

    public Map<Integer, List<Recordable>> getReplayData() {
        return replayData;
    }

    public Map<Integer, EntityInfo> getEntityInfo() {
        return entityInfo;
    }

    public boolean isRecording() {
        return recording;
    }

    public Location getOrigin() {
        return origin;
    }

    /**
     * @return Length of replay, the replay must have finished recording
     */
    public int getLength() {
        return length;
    }

    /**
     * @return The current tick of the replay
     */
    public int getTick() {
        return MinecraftServer.currentTick - startTick;
    }

    /**
     * Starts recording the replay
     */
    public void startRecording() {
        startTick = MinecraftServer.currentTick;
        recording = true;
    }

    /**
     * Stops recording the replay
     */
    public void endRecording() {
        length = getTick();
        recording = false;
    }

    /**
     * Adds a recordable to the replay data
     */
    public void record(Recordable recordable) {
        int tick = getTick();
        if (!replayData.containsKey(tick)) {
            replayData.put(tick, new ArrayList<>());
        }
        replayData.get(tick).add(recordable);
        // Use this for shifting locations, or anything needed to be run on record
        recordable.onRecord(this);
    }

    /**
     * Adds an entity to the replay
     * @param id The id of the entity, must be unique
     * @param info The info object of the entity
     */
    public void addEntity(int id, EntityInfo info) {
        if (entityInfo.containsKey(id)) return;
        entityInfo.put(id, info);
    }

    /**
     * Adds a player to the replay
     */
    public void addPlayer(Player player) {
        addEntity(player.getEntityId(), new EntityInfo(player.getName()));
    }

    public boolean hasEntity(int id) {
        return entityInfo.containsKey(id);
    }

    public boolean hasPlayer(Player player) {
        return hasEntity(player.getEntityId());
    }
}