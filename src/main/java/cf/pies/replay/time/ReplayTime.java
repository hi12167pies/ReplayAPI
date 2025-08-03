package cf.pies.replay.time;

public interface ReplayTime {
    void start();

    int getCurrentTick();

    void end();
}
