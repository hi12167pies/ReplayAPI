package cf.pies.ReplayAPI.api;

import org.bukkit.Location;
import org.bukkit.Material;

public class ReplayBlockData {
    public Material material;
    public byte data;
    public boolean placed;
    public Location location;

    public ReplayBlockData(Material material, byte data, Location location, boolean placed) {
        this.material = material;
        this.data = data;
        this.placed = placed;
        this.location = location;
    }
}
