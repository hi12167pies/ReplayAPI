package cf.pies.replay.type.serialize;

import lombok.experimental.SuperBuilder;
import org.bukkit.Location;

/**
 * A {@link Vec3f}, except with origin util methods!
 */
@SuperBuilder
public class Vec3fOrigin extends Vec3i {
    /**
     * Create a new instance of {@link Vec3f} shifted (subtracted) from the location.
     */
    public Vec3f shiftFloat(Location location) {
        return new Vec3f(
                (float) location.getX() - this.getX(),
                (float) location.getY() - this.getY(),
                (float) location.getZ() - this.getZ()
        );
    }

    /**
     * Create a new instance of {@link Vec3i} shifted (subtracted) from the location.
     * This will also use block x/y/z
     */
    public Vec3i shiftInt(Location location) {
        return new Vec3i(
                location.getBlockX() - this.getX(),
                location.getBlockY() - this.getY(),
                location.getBlockZ() - this.getZ()
        );
    }
}
