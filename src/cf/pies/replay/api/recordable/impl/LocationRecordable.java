package cf.pies.replay.api.recordable.impl;

import cf.pies.replay.api.Replay;
import cf.pies.replay.api.ReplayPlayback;
import cf.pies.replay.api.recordable.EntityRecordable;
import org.bukkit.Location;
import top.speedcubing.lib.bukkit.entity.NPC;

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
        location = location.clone().subtract(replay.getOrigin());
    }

    @Override
    public void play(ReplayPlayback playback) {
        NPC npc = playback.getNPC(entityId);

        npc.setLocation(location.clone().add(playback.getOrigin()));
    }
}
