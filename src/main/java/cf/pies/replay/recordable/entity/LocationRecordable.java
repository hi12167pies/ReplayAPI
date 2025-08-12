package cf.pies.replay.recordable.entity;

import cf.pies.replay.recordable.EntityRecordable;
import cf.pies.replay.recordable.Recordable;
import cf.pies.replay.type.data.Vec3d;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * Location recordable will track the current location of an entity.
 */
@Getter
@RequiredArgsConstructor
public class LocationRecordable implements EntityRecordable, Recordable, Serializable {
    private static final long serialVersionUID = 1;

    private final int entityId;

    private final Vec3d location;

    private final float pitch;
    private final float yaw;


}
