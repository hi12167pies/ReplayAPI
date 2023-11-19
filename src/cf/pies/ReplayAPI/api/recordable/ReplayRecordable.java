package cf.pies.ReplayAPI.api.recordable;

import cf.pies.ReplayAPI.api.Replay;

import java.util.ArrayList;
import java.util.List;

public class ReplayRecordable<T> {
    protected final Replay replay;
    public ReplayRecordable(Replay replay) {
        this.replay = replay;
    }
    public void add(T obj) {
        if (!this.ticks.contains(this.replay.getCurrentTick())) {
            this.ticks.add(this.replay.getCurrentTick());
            this.actions.add(obj);
        }
    }

    public List<Integer> ticks = new ArrayList<>();
    public List<T> actions = new ArrayList<>();

}
