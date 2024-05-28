package cf.pies.replay.api.entity;

import cf.pies.replay.api.data.ReplaySerializable;
import cf.pies.replay.api.data.stream.ReplayInputStream;
import cf.pies.replay.api.data.stream.ReplayOutputStream;

import java.io.IOException;

public class EntityInfo implements ReplaySerializable {
    private String name;

    public EntityInfo(String name) {
        this.name = name;
    }
    public EntityInfo() {
    }

    public String getName() {
        return name;
    }

    @Override
    public void write(ReplayOutputStream out) throws IOException {
        out.writeString(name);
    }

    @Override
    public void read(ReplayInputStream in) throws IOException {
        name = in.readString();
    }
}
