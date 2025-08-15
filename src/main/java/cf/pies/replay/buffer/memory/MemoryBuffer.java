package cf.pies.replay.buffer.memory;

import cf.pies.replay.buffer.ReplayBuffer;
import cf.pies.replay.recordable.Recordable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores data in memory.
 * Not recommended.
 */
public class MemoryBuffer implements ReplayBuffer {
    private Map<Integer, List<Recordable>> cache;

    @Override
    public void begin() throws Exception {
        cache = new HashMap<>();
    }

    @Override
    public void submit(int tick, List<Recordable> recordables) {
        cache.put(tick, recordables);
    }

    @Override
    public void end() {
        // clear cache
        cache.clear();
        cache = null;
    }
}
