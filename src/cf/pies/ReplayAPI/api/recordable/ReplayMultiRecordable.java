package cf.pies.ReplayAPI.api.recordable;

import cf.pies.ReplayAPI.api.Replay;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class ReplayMultiRecordable<T> extends ReplayRecordable<T> {
    public ReplayMultiRecordable(Replay replay) {
        super(replay);
    }

    public void addMulti(T obj) {
        if (!this.ticks.contains(this.replay.getCurrentTick())) {
            this.ticks.add(this.replay.getCurrentTick());
            // If their already an array add to the existing one.
            if (this.ticks.contains(this.replay.getCurrentTick())) {
                this.actions.get(this.actions.size() -1)
                        .add(obj);
            } else {
                this.actions.add(Lists.newArrayList(obj));
            }
        }
    }

    public List<List<T>> actions = new ArrayList<>();
}
