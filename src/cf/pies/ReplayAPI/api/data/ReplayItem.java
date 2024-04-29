package cf.pies.ReplayAPI.api.data;

import org.bukkit.Material;

public class ReplayItem {
    private final Material type;
    private final byte data;

    public ReplayItem(Material type, byte data) {
        this.type = type;
        this.data = data;
    }

    public Material getType() {
        return this.type;
    }

    public byte getData() {
        return this.data;
    }
}
