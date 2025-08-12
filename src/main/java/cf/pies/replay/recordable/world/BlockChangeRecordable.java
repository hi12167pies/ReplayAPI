package cf.pies.replay.recordable.world;

import cf.pies.replay.recordable.Recordable;
import cf.pies.replay.type.serialize.MaterialInfo;
import cf.pies.replay.type.serialize.Vec3i;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Any block change that happens in the world
 */
@Getter
@RequiredArgsConstructor
public class BlockChangeRecordable implements Recordable {
    private final Vec3i position;
    private final MaterialInfo type;
}