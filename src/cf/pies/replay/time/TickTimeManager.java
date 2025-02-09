package cf.pies.replay.time;

import cf.pies.replay.ReplayTimeManager;
import net.minecraft.server.v1_8_R3.MinecraftServer;

public class TickTimeManager implements ReplayTimeManager {
    /**
     * As the ticks can roll over to -1 this tracks if the timer is currently timing.
     */
    private boolean timing;

    /**
     * The tick when the timer has started.
     */
    private int start;

    /**
     * The tick on which the timer has ended.
     */
    private int end;

    @Override
    public void start() {
        start = MinecraftServer.currentTick;
        timing = true;
    }

    @Override
    public void end() {
        end = MinecraftServer.currentTick;
        timing = false;
    }

    @Override
    public int getTime() {
        if (timing) {
            return MinecraftServer.currentTick - start;
        } else {
            return end - start;
        }
    }

    @Override
    public boolean isTiming() {
        return timing;
    }
}
