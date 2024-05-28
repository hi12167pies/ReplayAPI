package cf.pies.replay.api.recordable.impl;

import cf.pies.replay.api.Replay;
import cf.pies.replay.api.ReplayPlayback;
import cf.pies.replay.api.data.stream.ReplayInputStream;
import cf.pies.replay.api.data.stream.ReplayOutputStream;
import cf.pies.replay.api.recordable.EntityRecordable;
import cf.pies.replay.api.recordable.RecordableSerializable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import top.speedcubing.lib.bukkit.entity.NPC;

import java.io.IOException;

public class LocationRecordable implements EntityRecordable, RecordableSerializable {
    private int entityId;
    private Location location;

    public LocationRecordable(int entityId, Location location) {
        this.entityId = entityId;
        this.location = location;
    }

    public LocationRecordable() {
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
        npc.updateNpcLocation();
    }

    @Override
    public int getSerializedId() {
        return 1;
    }

    @Override
    public void write(ReplayOutputStream out) throws IOException {
        out.writeVarInt(entityId);
        out.writeLocation(location);
    }

    @Override
    public void read(ReplayInputStream in) throws IOException {
        entityId = in.readVarInt();
        location = in.readLocation();
    }
}
