package cf.pies.replay.type.serialize;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
public class Vec3f implements Serializable {
    public static Vec3f from(Location location) {
        return new Vec3f(
                (float) location.getX(),
                (float) location.getY(),
                (float) location.getZ()
        );
    }

    private final float x;
    private final float y;
    private final float z;
}
