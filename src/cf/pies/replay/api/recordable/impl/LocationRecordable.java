package cf.pies.replay.api.recordable.impl;

import cf.pies.replay.api.Replay;
import cf.pies.replay.api.recordable.EntityRecordable;
import org.bukkit.Location;

public class LocationRecordable implements EntityRecordable {
    private final int entityId;
    private Location location;

    public LocationRecordable(int entityId, Location location) {
        this.entityId = entityId;
        this.location = location;

    }

    public Location getLocation() {
        return location;
    }

    @Override
    public int getEntityId() {
        return entityId;
    }

    @Override
    public void onRecord(Replay replay) {
        // Shift location relative to origin
        location = location.subtract(replay.getOrigin());
    }
}
