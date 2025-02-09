package cf.pies.replay;

import javax.annotation.Nullable;

/**
 * A register of all available nodes and their ids.
 * This should be cloneable if the user wishes to edit the registry.
 */
public interface ReplayNodeRegistry {
    /**
     * @return The id for the specified node.
     *         If the class is not registered, this method will return -1.
     */
    int getIdFromNode(Class<? extends Node> nodeClass);

    /**
     * @return The node class for the specified id.
     *         If the id has no node registered to it, this method will return null.
     */
    @Nullable
    Class<? extends Node> getNodeFromId(int id);

    /**
     * Add the node to the registry with the specified id.
     * @return Returns itself to allow for chaining registrations.
     */
    ReplayNodeRegistry register(int id, Class<? extends Node> nodeClass);

    /**
     * Makes a copy of the registry with any underlying maps or data copied.
     */
    ReplayNodeRegistry clone();
}
