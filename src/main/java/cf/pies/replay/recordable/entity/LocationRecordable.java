package cf.pies.replay.recordable.entity;

import cf.pies.replay.recordable.EntityRecordable;
import cf.pies.replay.recordable.Recordable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * Location recordable will track the current location of an entity.
 */
@Getter
@RequiredArgsConstructor
public class LocationRecordable implements EntityRecordable, Recordable, Serializable {
    private static final long serialVersionUID = 1L;

    private final int entityId;

    private final double x;
    private final double y;
    private final double z;

    private final float pitch;
    private final float yaw;


}
