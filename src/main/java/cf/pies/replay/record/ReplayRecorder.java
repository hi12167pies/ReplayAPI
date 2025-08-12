package cf.pies.replay.record;

import cf.pies.replay.buffer.ReplayBufferWriter;
import cf.pies.replay.recordable.Recordable;
import cf.pies.replay.time.ReplayTime;
import cf.pies.replay.type.EntityType;
import cf.pies.replay.type.ReplayEntityMetadata;
import io.netty.util.collection.IntObjectHashMap;
import io.netty.util.collection.IntObjectMap;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is designed for recording replays only.
 * Its data is designed to be buffered off to a disk or deleted.
 * The playback class will ensure to keep replay data loaded or load it dynamically.
 */
@RequiredArgsConstructor
public class ReplayRecorder {
    private final ReplayTime time;
    private final ReplayBufferWriter buffer;

    /**
     * A list of recordables in the current tick.
     */
    private List<Recordable> currentTickRecordables = new ArrayList<>();

    private IntObjectMap<ReplayEntityMetadata> entities = new IntObjectHashMap<>();

    /**
     * Current tick and state of the {@link ReplayRecorder#currentTickRecordables}
     */
    private int currentTick = 0;

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
     * Begins the replay recording (internally)
     * Ticking and recording is required manually or by a helper class.
     * @throws Exception An exception may be thrown by upstream classes such as the buffer
     */
    public void start() throws Exception {
        time.start();
        buffer.begin();
    }

    public void end() {
        finishTick();
        time.end();
        buffer.end();
    }

    /**
     * Adds an entity to replay, must be supported entity type.
     * @param recId The entities id in the replay, this can be the actual entity or id or just an incrementing number.
     * @param entity This must be a supported entity which will be added.
     */
    public void addEntity(int recId, EntityType type, Entity entity) throws IllegalArgumentException {
        if (type.getEntityClass().isInstance(entity)) {
            throw new IllegalArgumentException("Entity type does not match supplied entity");
        }

        entities.put(recId, new ReplayEntityMetadata(recId, type));
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
