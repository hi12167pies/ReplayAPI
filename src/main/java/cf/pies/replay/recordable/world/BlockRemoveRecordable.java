package cf.pies.replay.recordable.world;

import cf.pies.replay.recordable.Recordable;
import cf.pies.replay.recordable.ReplaySerializable;
import cf.pies.replay.stream.ReplayInputStream;
import cf.pies.replay.stream.ReplayOutputStream;
import cf.pies.replay.type.serialize.Vec3i;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;

/**
 * States a block change to air
 * Could be a {@link BlockChangeRecordable} but has its own recordable for compression sakes
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlockRemoveRecordable implements Recordable {
    private Vec3i position;

    @Override
    public void read(ReplayInputStream stream) throws IOException {
        position = stream.readVec3i();
    }

    @Override
    public void write(ReplayOutputStream stream) throws IOException {
        stream.writeVec3i(position);
    }
}
