package cf.pies.replay.api.exception;

public class UnknownRecordableTypeException extends ReplayException {
    public UnknownRecordableTypeException(int type) {
        super("Unknown recordable type " + type);
    }
}
