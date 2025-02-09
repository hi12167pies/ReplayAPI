package cf.pies.replay;

/**
 * This interface will manage all time in the replay.
 * All time is saved in integers.
 */
public interface ReplayTimeManager {
    /**
     * This will start the replay.
     * After this all times should be relative to when this method was called.
     */
    void start();

    /**
     * This will stop the timer from ticking any further.
     */
    void end();

    /**
     * @return The current time relative to the start.
     */
    int getTime();

    /**
     * @return If the timer is currently tracking time.
     */
    boolean isTiming();
}
