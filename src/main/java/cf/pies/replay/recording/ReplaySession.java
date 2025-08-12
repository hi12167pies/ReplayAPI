package cf.pies.replay.recording;

import cf.pies.replay.buffer.ReplayBufferWriter;
import cf.pies.replay.recordable.Recordable;
import cf.pies.replay.time.ReplayTime;
import cf.pies.replay.type.EntityType;
import cf.pies.replay.type.EntityMetadata;
import cf.pies.replay.type.serialize.Vec3f;
import io.netty.util.collection.IntObjectHashMap;
import io.netty.util.collection.IntObjectMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is designed for recording replays only.
 * Its data is designed to be buffered off to a disk or deleted.
 * The playback class will ensure to keep replay data loaded or load it dynamically.
 */
@RequiredArgsConstructor
public class ReplaySession {
    private final ReplayTime time;
    private final ReplayBufferWriter buffer;

    @Getter
    private final Vec3f origin;

    /**
     * A list of recordables in the current tick.
     */
    private List<Recordable> currentTickRecordables = new ArrayList<>();

    /**
     * Metadata for entities in replay.
     */
    private final IntObjectMap<EntityMetadata> entities = new IntObjectHashMap<>();

    /**
     * A map of actual entity id -> replay entity id.
     * This is used to know if an entity is being recording in the replay
     */
    private final Map<Integer, Integer> recordingEntityIds = new HashMap<>();

    /**
     * Current tick and state of the {@link ReplaySession#currentTickRecordables}
     */
    private int currentTick = 0;

    /**
     * States if the current replay is actively recording
     */
    @Getter
    private boolean active = false;

    /**
     * Begins the replay recording (internally)
     * Ticking and recording is required manually or by a helper class.
     * @throws Exception An exception may be thrown by upstream classes such as the buffer
     */
    public void start() throws Exception {
        time.start();
        buffer.begin();
        active = true;
    }

    public void end() {
        finishTick();
        time.end();
        buffer.end();
        active = false;
    }

    public boolean isEntityRecorded(int entityId) {
        return recordingEntityIds.containsKey(entityId);
    }

    public int getEntityIdToRecId(int entityId) {
        return recordingEntityIds.get(entityId);
    }

    /**
     * This will finish the tick by clearing the current tick array, and moving it to the buffer as well as anything else needed to complete the tick.
     * The difference is this will not advance the replay and is only for internal use.
     */
    private void finishTick() {
        // If there is no recordables, there is no point saving to the buffer or updating the array list.
        if (!currentTickRecordables.isEmpty()) {
            // Move the old tick into the completed buffer
            buffer.submit(currentTick, currentTickRecordables);

            // Create new array for current tick
            currentTickRecordables = new ArrayList<>();
        }
    }

    /**
     * Adds an entity to replay, must be supported entity type.
     * @param recId The entities id in the replay, this can be the actual entity or id or just an incrementing number.
     * @param entity This must be a supported entity which will be added.
     */
    public void addEntity(int recId, EntityType type, Entity entity) throws IllegalArgumentException {
        if (entity.getClass().isInstance(type.getEntityClass())) {
            throw new IllegalArgumentException("Entity type does not match supplied entity");
        }

        entities.put(recId, new EntityMetadata(recId, type));
        recordingEntityIds.put(entity.getEntityId(), recId);
    }

    public void record(Recordable recordable) {
        int timeTick = time.getCurrentTick();
        if (timeTick != currentTick) {
            finishTick();
            currentTick = timeTick;
        }
        currentTickRecordables.add(recordable);
    }
}
