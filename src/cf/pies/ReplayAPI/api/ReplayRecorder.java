package cf.pies.ReplayAPI.api;

import cf.pies.ReplayAPI.api.errors.ReplayCannotRecordError;
import cf.pies.ReplayAPI.api.recordable.ReplayMultiRecordable;
import cf.pies.ReplayAPI.api.recordable.ReplayRecordable;
import org.bukkit.Location;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class ReplayRecorder {
    private final Replay replay;
    public final ReplayRecordable<Location> movementRecordable;
    public final ReplayRecordable<Boolean> sneakRecordable;
    public final ReplayMultiRecordable<ReplayBlockData> blocksRecordable;
    public ReplayRecorder(Replay replay) {
        this.replay = replay;
        this.movementRecordable = new ReplayRecordable<>(this.replay);
        this.sneakRecordable = new ReplayRecordable<>(this.replay);
        this.blocksRecordable = new ReplayMultiRecordable<>(this.replay);
    }

    public void initRecording() {
        this.sneakRecordable.add(this.replay.getPlayer().isSneaking());
    }

    public void record(PlayerMoveEvent event) throws ReplayCannotRecordError {
        if (!this.replay.canRecord()) {
            throw new ReplayCannotRecordError();
        }
        Location location = event
                .getPlayer()
                .getLocation()
                .clone()
                .subtract(this.replay.getOrigin());

        this.movementRecordable.add(location);
    }
    public void record(BlockBreakEvent event) throws ReplayCannotRecordError {
        if (!this.replay.canRecord()) {
            throw new ReplayCannotRecordError();
        }
        this.blocksRecordable.add(new ReplayBlockData(
                event.getBlock().getType(),
                event.getBlock().getData(),
                event.getBlock().getLocation().clone().subtract(this.replay.getOrigin()),
                true // placed
        ));
    }

    public void record(BlockPlaceEvent event) throws ReplayCannotRecordError {
        if (!this.replay.canRecord()) {
            throw new ReplayCannotRecordError();
        }
        this.blocksRecordable.add(new ReplayBlockData(
                event.getBlock().getType(),
                event.getBlock().getData(),
                event.getBlock().getLocation().clone().subtract(this.replay.getOrigin()),
                false // placed (false = broken)
        ));
    }

    public void record(PlayerToggleSneakEvent event) throws ReplayCannotRecordError {
        if (!this.replay.canRecord()) {
            throw new ReplayCannotRecordError();
        }
        this.sneakRecordable.add(event.isSneaking());
    }
}
