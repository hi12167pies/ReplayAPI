package cf.pies.ReplayAPI.api.data;

import org.bukkit.Material;

public class ReplayBlock extends ReplayItem {
    private final boolean broken;

    public ReplayBlock(Material type, byte data, boolean broken) {
        super(type, data);
        this.broken = broken;
    }

    public boolean isBroken() {
        return this.broken;
    }
}
