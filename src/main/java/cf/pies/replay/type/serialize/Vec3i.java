package cf.pies.replay.type.serialize;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
public class Vec3i implements Serializable {
    public static Vec3i from(Location location) {
        return new Vec3i(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public static Vec3i from(Block block) {
        return from(block.getLocation());
    }

    private final int x;
    private final int y;
    private final int z;
}
