package cf.pies.ReplayAPI.api;
// TODO Add NPC lib
public class ReplayPlayback {
    private final Replay replay;
    public ReplayPlayback(Replay replay) {
        this.replay = replay;
    }

    public Replay getReplay() {
        return this.replay;
    }

    /**
     * Start the replay loop that will run
     * @param delay If you wish to use slow motion you can set the delay low
     * @param forwards Direction the replay will play, use false to play in reverse
     */
    public void play(long delay, boolean forwards) {

    }

    /**
     * Stop the replay loop
     */
    public void stop() {

    }

    /**
     * Run at the start to spawn npc and that stuff
     */
    public void init() {

    }

    /**
     * End the replay and remove npcs, any blocks
     */
    public void end() {

    }

    /**
     * Add singular tick
     */

    public void addTick() {

    }

    /**
     * Remove singular tick
     */
    public void removeTick() {

    }
}
