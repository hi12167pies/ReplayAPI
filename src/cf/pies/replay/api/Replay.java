package cf.pies.replay.api;

import cf.pies.replay.api.entity.EntityInfo;
import cf.pies.replay.api.entity.ReplaySkin;
import cf.pies.replay.api.recordable.Recordable;
import cf.pies.replay.api.recordable.entity.*;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Replay {
    private int startTick = -1;
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
     * Sets the length of the replay.
     * <strong>This should only be used for loading replays</strong>
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * @return The current tick of the replay
     */
    public int getTick() {
        if (startTick == -1) return 0;
        return MinecraftServer.currentTick - startTick;
    }

    /**
     * Starts recording the replay
     */
    public void start() {
        startTick = MinecraftServer.currentTick;
        recording = true;
    }

    /**
     * Stops recording the replay
     */
    public void end() {
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
        if (isRecording()) {
            record(new EntityAddRecordable(id, info));
        } else {
            entityInfo.put(id, info);
        }
    }

    /**
     * Removes an entity from replay
     */
    public void removeEntity(int id) {
        if (isRecording()) {
            record(new EntityRemoveRecordable(id));
        } else {
            entityInfo.remove(id);
        }
    }

    /**
     * Adds a player to the replay
     * This also records all the current events of the player
     */
    public void addPlayer(Player player) {
        // TODO PlayerAddRecordable?
        if (entityInfo.containsKey(player.getEntityId())) return;

        // Add the player's entity
        addEntity(player.getEntityId(), new EntityInfo(player.getName(), ReplaySkin.from(player)));

        // Record the current events of the player
        record(new LocationRecordable(player.getEntityId(), player.getLocation()));
        record(new SneakRecordable(player.getEntityId(), player.isSneaking()));

        if (player.getItemInHand() != null) {
            record(new ItemHeldRecordable(player.getEntityId(), player.getItemInHand().getType(),player.getItemInHand().getData().getData()));
        }
    }

    /**
     * @param id The id of the entity
     * @return If the entity is in the replay
     */
    public boolean hasEntity(int id) {
        return entityInfo.containsKey(id);
    }

    /**
     * @param player The player to check
     * @return If the replay is in the replay
     */
    public boolean hasPlayer(Player player) {
        return hasEntity(player.getEntityId());
    }

    /**
     * Adds the current instance to the default replay recorder
     */
    public void addRecording() {
        ReplayAPI.getApi().addRecording(this);
    }

    /**
     * Removes the current instance from the default replay recorder
     */
    public void removeRecording() {
        ReplayAPI.getApi().removeRecording(this);
    }
}
