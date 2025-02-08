package cf.pies.replay;

import java.io.IOException;

/**
 * This interface represents a replay that can be buffered to a disk / external location before being commited to a database.
 */
public interface ReplayBuffer {
    /**
     * Writes a node to the buffer.
     */
    void write(Node node) throws IOException;

    /**
     * Closes the buffer.
     */
    void close() throws Exception;
}
