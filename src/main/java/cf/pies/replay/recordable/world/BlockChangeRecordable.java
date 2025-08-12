package cf.pies.replay.recordable.world;

import cf.pies.replay.recordable.Recordable;
import cf.pies.replay.stream.ReplayInputStream;
import cf.pies.replay.stream.ReplayOutputStream;
import cf.pies.replay.type.serialize.MaterialInfo;
import cf.pies.replay.type.serialize.Vec3i;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

/**
 * Any block change that happens in the world
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlockChangeRecordable implements Recordable {
    private Vec3i position;
    private MaterialInfo type;

    @Override
    public void read(ReplayInputStream stream) throws IOException {
        position = stream.readVec3i();
        type = stream.readMaterialInfo();
    }

    @Override
    public void write(ReplayOutputStream stream) throws IOException {
        stream.writeVec3i(position);
        stream.writeMaterialInfo(type);
    }
}