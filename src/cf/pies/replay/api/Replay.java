package cf.pies.replay.api;

import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;

public class Replay {
    public Recordable<Location> movementRecorder = new Recordable<>(this);
    private int startTick;

    public void startRecording() {
        startTick = MinecraftServer.currentTick;
    }

    public int getTick() {
        return MinecraftServer.currentTick - startTick;
    }

    public void record(PlayerMoveEvent event) {
        movementRecorder.record(event.getPlayer(), event.getTo());
    }
}
