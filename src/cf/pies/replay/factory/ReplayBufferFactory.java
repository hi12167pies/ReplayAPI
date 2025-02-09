package cf.pies.replay.factory;

import cf.pies.replay.ReplayBuffer;
import cf.pies.replay.ReplayNodeRegistry;
import cf.pies.replay.ReplayTimeManager;
import cf.pies.replay.buffer.FileReplayBuffer;
import cf.pies.replay.node.LocationNode;
import cf.pies.replay.registry.MapNodeRegistry;
import lombok.Getter;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;

@Getter
public class ReplayBufferFactory {
    private static final ReplayNodeRegistry defaultRegistry = new MapNodeRegistry()
            .register(0x01, LocationNode.class);

    private final ReplayNodeRegistry nodeRegistry;
    private final ReplayTimeManager timeManager;

    /**
     * @param nodeRegistry The registry that will be used for writing nodes.
     *                     If the registry is null, it will copy the default registry (and you can modify it).
     */
    public ReplayBufferFactory(@Nullable ReplayNodeRegistry nodeRegistry, ReplayTimeManager timeManager) {
        if (nodeRegistry == null) {
            this.nodeRegistry = defaultRegistry.clone();
        } else {
            this.nodeRegistry = nodeRegistry;
        }
        this.timeManager = timeManager;
    }

    /**
     * Creates an underlying {@link cf.pies.replay.buffer.FileReplayBuffer} that will write to disk to buffer any data.
     * This buffer is opened once you create it.
     */
    public ReplayBuffer createFileBuffer(File file) throws IOException {
        return new FileReplayBuffer(nodeRegistry, timeManager, file);
    }
}
