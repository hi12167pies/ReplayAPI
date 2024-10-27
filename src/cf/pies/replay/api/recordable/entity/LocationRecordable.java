package cf.pies.replay.api.recordable.entity;

import cf.pies.replay.api.Replay;
import cf.pies.replay.api.ReplayPlayback;
import cf.pies.replay.api.data.SaveRecordable;
import cf.pies.replay.api.data.stream.ReplayInputStream;
import cf.pies.replay.api.data.stream.ReplayOutputStream;
import cf.pies.replay.api.npc.ReplayNPC;
import cf.pies.replay.api.recordable.Recordable;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.IOException;

public class LocationRecordable implements Recordable, SaveRecordable {
    private int entityId;
    private Location location;

    public LocationRecordable() {
    }

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
        ReplayNPC npc = playback.getNPC(entityId);
        if (npc == null) return;
        if (!location.getWorld().equals(playback.getOrigin().getWorld())) {
            location.setWorld(playback.getOrigin().getWorld());
        }
        npc.setLocation(location.clone().add(playback.getOrigin()));
    }

    @Override
    public void write(ReplayOutputStream stream) throws IOException {
        stream.writeInt(entityId);
        stream.writeLocation(location);
    }

    @Override
    public void read(ReplayInputStream stream, World world) throws IOException {
        entityId = stream.readInt();
        location = stream.readLocation(world);
    }
}
