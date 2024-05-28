package cf.pies.replay.api.recordable;

import cf.pies.replay.api.data.ReplaySerializable;

public interface RecordableSerializable extends ReplaySerializable {
    int getSerializedId();
}
