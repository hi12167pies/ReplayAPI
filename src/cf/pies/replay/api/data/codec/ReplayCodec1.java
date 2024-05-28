package cf.pies.replay.api.data.codec;

import cf.pies.replay.api.Replay;
import cf.pies.replay.api.data.ReplayCodec;
import cf.pies.replay.api.data.stream.ReplayInputStream;
import cf.pies.replay.api.data.stream.ReplayOutputStream;
import cf.pies.replay.api.entity.EntityInfo;

import java.io.IOException;

public class ReplayCodec1 implements ReplayCodec {
    @Override
    public void read(Replay replay, ReplayInputStream in) throws IOException {
        // Entity info
        for (int i = 0; i < in.readVarInt(); i++) {
            int id = in.readVarInt();
            EntityInfo info = new EntityInfo();
            info.read(in);
            replay.getEntityInfo().put(id, info);
        }
    }

    @Override
    public void write(Replay replay, ReplayOutputStream out) throws IOException {
        // Entity Info
        out.writeVarInt(replay.getEntityInfo().size());
        for (Integer id : replay.getEntityInfo().keySet()) {
            EntityInfo info = replay.getEntityInfo().get(id);
            out.writeInt(id);
            info.write(out);
        }
    }
}
