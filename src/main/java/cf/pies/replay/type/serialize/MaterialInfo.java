package cf.pies.replay.type.serialize;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.block.Block;

import java.io.Serializable;

/**
 * Represents any bukkit material
 */
@Getter
@RequiredArgsConstructor
public class MaterialInfo implements Serializable {
    @SuppressWarnings("deprecation")
    public static MaterialInfo from(Block block) {
        return new MaterialInfo(
                block.getType().getId(),
                block.getData()
        );
    }

    private final int id;
    private final byte data;
}
