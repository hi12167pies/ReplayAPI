package cf.pies.replay.api.recordable.impl;

import cf.pies.replay.api.Replay;
import cf.pies.replay.api.ReplayPlayback;
import cf.pies.replay.api.recordable.Recordable;
import org.bukkit.Location;
import top.speedcubing.lib.bukkit.entity.NPC;

public class LocationRecordable implements Recordable {
    private final int entityId;
    private Location location;

    public LocationRecordable(int entityId, Location location) {
        this.entityId = entityId;
        this.location = location;
    }

    @Override
    public void onRecord(Replay replay) {
        location = location.clone().subtract(replay.getOrigin());
    }

    @Override
    public void play(ReplayPlayback playback) {
        NPC npc = playback.getNPC(entityId);
        if (npc == null) return;
        npc.setLocation(location.clone().add(playback.getOrigin()));
        npc.updateNpcLocation();
    }
}
