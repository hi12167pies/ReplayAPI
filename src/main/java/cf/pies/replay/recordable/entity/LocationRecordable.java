package cf.pies.replay.recordable.entity;

import cf.pies.replay.recordable.EntityRecordable;
import cf.pies.replay.recordable.Recordable;
import cf.pies.replay.stream.ReplayInputStream;
import cf.pies.replay.stream.ReplayOutputStream;
import cf.pies.replay.type.serialize.Vec3f;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

/**
 * Location recordable will track the current location of an entity.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LocationRecordable implements Recordable, EntityRecordable {
    private static final long serialVersionUID = 1;

    private int entityId;

    private Vec3f location;

    private float pitch;
    private float yaw;

    @Override
    public void read(ReplayInputStream stream) throws IOException {
        entityId = stream.readVarInt();
        location = stream.readVec3f();
        pitch = stream.readVarFloat();
        yaw = stream.readVarFloat();
    }

    @Override
    public void write(ReplayOutputStream stream) throws IOException {
        stream.writeVarInt(entityId);
        stream.writeVec3f(location);
        stream.writeVarFloat(pitch);
        stream.writeVarFloat(yaw);
    }
}
