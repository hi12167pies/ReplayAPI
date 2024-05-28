package cf.pies.replay.api.data.codec;

import cf.pies.replay.api.Replay;
import cf.pies.replay.api.data.ReplayCodec;
import cf.pies.replay.api.data.ReplaySerializable;
import cf.pies.replay.api.data.stream.ReplayInputStream;
import cf.pies.replay.api.data.stream.ReplayOutputStream;
import cf.pies.replay.api.entity.EntityInfo;
import cf.pies.replay.api.recordable.Recordable;
import cf.pies.replay.api.recordable.RecordableSerializable;

import java.io.IOException;
import java.util.List;

public class ReplayCodec1 implements ReplayCodec {
    @Override
    public void read(Replay replay, ReplayInputStream in) throws IOException {
        replay.setLength(in.readVarInt());

        // entity info
        for (int i = 0; i < in.readVarInt(); i++) {
            int id = in.readVarInt();
            EntityInfo info = new EntityInfo();
            info.read(in);
            replay.getEntityInfo().put(id, info);
        }
    }

    @Override
    public void write(Replay replay, ReplayOutputStream out) throws IOException {
        // length
        out.writeVarInt(replay.getLength());

        // entity Info
        out.writeVarInt(replay.getEntityInfo().size());
        for (Integer id : replay.getEntityInfo().keySet()) {
            EntityInfo info = replay.getEntityInfo().get(id);
            out.writeInt(id);
            info.write(out);
        }

        for (Integer tickNumber : replay.getReplayData().keySet()) {
            // tick id
            out.writeVarInt(tickNumber);

            // info
            List<Recordable> tickData = replay.getReplayData().get(tickNumber);

            for (Recordable recordable : tickData) {
                if (recordable instanceof RecordableSerializable) {
                    RecordableSerializable serializable = (RecordableSerializable) recordable;
                    out.writeVarInt(serializable.getSerializedId());
                    serializable.write(out);
                }
            }

        }
    }
}
