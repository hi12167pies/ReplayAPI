package cf.pies.replay.registry;

import cf.pies.replay.Node;
import cf.pies.replay.ReplayNodeRegistry;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class MapNodeRegistry implements ReplayNodeRegistry, Cloneable {
    private Map<Integer, Class<? extends Node>> idToNode = new HashMap<>();
    private Map<Class<? extends Node>, Integer> nodeToId = new HashMap<>();

    @Override
    public int getIdFromNode(Class<? extends Node> nodeClass) {
        return nodeToId.getOrDefault(nodeClass, -1);
    }

    @Override
    @Nullable
    public Class<? extends Node> getNodeFromId(int id) {
        return idToNode.getOrDefault(id, null);
    }

    @Override
    public ReplayNodeRegistry register(int id, Class<? extends Node> nodeClass) {
        nodeToId.put(nodeClass, id);
        idToNode.put(id, nodeClass);
        return this;
    }

    @Override
    public ReplayNodeRegistry clone() {
        try {
            MapNodeRegistry cloned = (MapNodeRegistry) super.clone();
            cloned.idToNode = new HashMap<>(idToNode);
            cloned.nodeToId = new HashMap<>(nodeToId);
            return cloned;
        } catch (CloneNotSupportedException exception) {
            throw new IllegalStateException(exception);
        }
    }
}
