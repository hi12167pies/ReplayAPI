package cf.pies.replay.api.exception;

public class UnsupportedCodecException extends Exception {
    public UnsupportedCodecException(int codec) {
        super("Codec version " + codec + " is not supported.");
    }
}
