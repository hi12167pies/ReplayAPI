package cf.pies.ReplayAPI.api.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReplayRecordable<T> {
    private final HashMap<Integer, List<T>> data = new HashMap<>();

    public void add(int tick, T data) {
        if (!this.data.containsKey(tick)) {
            this.data.put(tick, new ArrayList<>());
        }
        this.data.get(tick).add(data);
    }

    public HashMap<Integer, List<T>> getData() {
        return this.data;
    }

    public List<T> getAt(int tick) {
        return this.data.getOrDefault(tick, null);
    }
}
