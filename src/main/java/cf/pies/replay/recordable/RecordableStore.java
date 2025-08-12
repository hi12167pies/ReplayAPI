package cf.pies.replay.recordable;

import cf.pies.replay.recordable.entity.LocationRecordable;
import cf.pies.replay.recordable.world.BlockChangeRecordable;
import cf.pies.replay.recordable.world.BlockRemoveRecordable;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores a map of recordable -> id
 */
public class RecordableStore {
    @Getter
    private static RecordableStore defaultStore = new RecordableStore();

    static {
        defaultStore.register(0x1, LocationRecordable.class);
        defaultStore.register(0x2, BlockChangeRecordable.class);
        defaultStore.register(0x3, BlockRemoveRecordable.class);
    }

    private final Map<Class<? extends Recordable>, Integer> recordableToId = new HashMap<>();
    private final Map<Integer, Class<? extends Recordable>> idToRecordable = new HashMap<>();

    public void register(int id, Class<? extends Recordable> recordable) {
        if (idToRecordable.containsKey(id) || recordableToId.containsKey(recordable)) {
            throw new IllegalStateException("A recordable is already registered with that id or class.");
        }

        recordableToId.put(recordable, id);
        idToRecordable.put(id, recordable);
    }

    public Class<? extends Recordable> getRecordableById(int id) {
        return idToRecordable.get(id);
    }

    public int getIdByRecordable(Class<? extends Recordable> clazz) {
        return recordableToId.get(clazz);
    }
}
