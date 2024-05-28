package cf.pies.replay.api.exception;

/**
 * Common exception that you can use to catch all replay errors
 */
public class ReplayException extends Exception {
    public ReplayException(String message) {
        super(message);
    }
}
