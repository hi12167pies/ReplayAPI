package cf.pies.replay.node;

import cf.pies.replay.Node;
import cf.pies.replay.stream.ReplayOutputStream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LocationNode implements Node {
    private int entityId;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    @Override
    public void write(ReplayOutputStream stream) throws IOException {
        stream.writeVarInt(entityId);
        stream.writeDouble(x);
        stream.writeDouble(y);
        stream.writeDouble(z);
        stream.writeFloat(yaw);
        stream.writeFloat(pitch);
    }
}

