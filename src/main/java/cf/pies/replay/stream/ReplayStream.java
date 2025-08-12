package cf.pies.replay.stream;

/**
 * Contains static constants for {@link ReplayInputStream} and {@link ReplayOutputStream}
 */
public interface ReplayStream {
    int SEGMENT_BITS = 0x7F;
    int CONTINUE_BIT = 0x80;

    /**
     * Provides 3 decimals worth of accuracy for VarFloats
     */
    int MANTISSA_LENGTH = (int) Math.pow(10, 3);
}
