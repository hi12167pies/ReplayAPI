package cf.pies.replay.time;

import net.minecraft.server.v1_8_R3.MinecraftServer;

public class MinecraftTime implements ReplayTime {
    private int start;

    @Override
    public void start() {
        this.start = MinecraftServer.currentTick;
    }

    @Override
    public int getCurrentTick() {
        return MinecraftServer.currentTick - start;
    }

    @Override
    public void end() {

    }
}
