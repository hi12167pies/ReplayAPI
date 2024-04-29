package cf.pies.ReplayAPI.api;

import org.bukkit.Location;

public class Replay {
    private final ReplayData data = new ReplayData();
    private Location origin;

    public Replay(Location origin) {
        this.origin = origin;
    }

    public Location getOrigin() {
        return this.origin;
    }

    public void setOrigin(Location origin) {
        this.origin = origin;
    }

    public ReplayData getData() {
        return this.data;
    }
}
