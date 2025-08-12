package cf.pies.replay.recordable.entity;

import cf.pies.replay.recordable.EntityRecordable;
import cf.pies.replay.recordable.Recordable;
import cf.pies.replay.type.serialize.Vec3f;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Location recordable will track the current location of an entity.
 */
@Getter
@RequiredArgsConstructor
public class LocationRecordable implements Recordable, EntityRecordable {
    private static final long serialVersionUID = 1;

    private final int entityId;

    private final Vec3f location;

    private final float pitch;
    private final float yaw;
}
