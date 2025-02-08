package cf.pies.replay;

public interface ReplayNodeRegistry {
    /**
     * Gets the id for the specified node. If there is no assigned id return -1.
     */
    int getNodeId(Class<? extends Node> nodeClass);
}
