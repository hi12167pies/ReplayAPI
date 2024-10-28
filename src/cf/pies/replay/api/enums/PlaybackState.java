package cf.pies.replay.api.enums;

public enum PlaybackState {
    /**
     * This state is used when the playback is initially setup.
     */
    NONE,

    /**
     * Used when the playback is paused.
     */
    PAUSED,

    /**
     * Used when the playback is currently playing.
     */
    PLAYING,

    /**
     * Used if the replay is over.
     */
    ENDED
}
