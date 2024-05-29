package cf.pies.replay.api.recordable.world;

import cf.pies.replay.api.Replay;
import cf.pies.replay.api.ReplayPlayback;
import cf.pies.replay.api.recordable.Recordable;
import cf.pies.replay.api.utils.NMS;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class BlockRecordable implements Recordable {
    private Location location;
    private final Material material;
    private final byte data;
    private final boolean isBroken;

    public BlockRecordable(Location location, Material material, byte data, boolean isBroken) {
        this.location = location;
        this.material = material;
        this.data = data;
        this.isBroken = isBroken;
    }

    @Override
    public void onRecord(Replay replay) {
        location = location.clone().subtract(replay.getOrigin());
    }

    @Override
    public void play(ReplayPlayback playback) {
        for (Player player : playback.getListeners()) {
            if (isBroken) {
                NMS.updateBlock(player, location.clone().add(playback.getOrigin()), Material.AIR, (byte) 0);
            } else {
                NMS.updateBlock(player, location.clone().add(playback.getOrigin()), material, data);
            }
        }
    }
}
