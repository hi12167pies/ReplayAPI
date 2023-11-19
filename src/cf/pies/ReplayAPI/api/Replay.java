package cf.pies.ReplayAPI.api;

import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Replay {
    private final Player player;
    private final Location origin;
    private int startTick = -1;
    private int endTick = -1;
    private final ReplayRecorder recorder = new ReplayRecorder(this);
    public Replay(Player player, Location origin) {
        this.player = player;
        this.origin = origin;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Location getOrigin() {
        return this.origin;
    }

    public int getCurrentTick() {
        return MinecraftServer.currentTick - this.startTick;
    }

    public ReplayRecorder getRecorder() {
        return this.recorder;
    }

    public void startRecording() {
        this.startTick = MinecraftServer.currentTick;
        this.getRecorder().initRecording();
    }

    public void endRecording() {
        this.endTick = MinecraftServer.currentTick;
    }

    public boolean hasStarted() {
        return this.startTick != -1;
    }

    public boolean hasEnded() {
        return this.endTick != -1;
    }

    public boolean canRecord() {
        return this.hasStarted() && !this.hasEnded();
    }
}
