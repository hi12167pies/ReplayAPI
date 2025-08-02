package cf.pies.replay.recordable.entity;

import cf.pies.replay.recordable.EntityRecordable;
import cf.pies.replay.recordable.Recordable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LocationRecordable implements EntityRecordable, Recordable {
    private final int entityId;

    private final double x;
    private final double y;
    private final double z;
}
