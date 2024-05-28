package cf.pies.replay.api.data.codec;

import cf.pies.replay.api.Replay;
import cf.pies.replay.api.data.ReplayCodec;
import cf.pies.replay.api.data.stream.ReplayInputStream;
import cf.pies.replay.api.data.stream.ReplayOutputStream;
import cf.pies.replay.api.entity.EntityInfo;
import cf.pies.replay.api.exception.UnknownRecordableTypeException;
import cf.pies.replay.api.recordable.Recordable;
import cf.pies.replay.api.recordable.RecordableSerializable;
import cf.pies.replay.api.recordable.impl.LocationRecordable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReplayCodec1 implements ReplayCodec {
    private final static HashMap<Integer, Class<? extends Recordable>> RECORDABLE_TYPES = new HashMap<>();

    static {
        RECORDABLE_TYPES.put(1, LocationRecordable.class);
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public void read(Replay replay, ReplayInputStream in) throws IOException, UnknownRecordableTypeException, IllegalAccessException, InstantiationException {
        replay.setLength(in.readVarInt());

        // entity info - read array length
        for (int i = 0; i < in.readVarInt(); i++) {
            int id = in.readVarInt();
            EntityInfo info = new EntityInfo();
            info.read(in);
            replay.getEntityInfo().put(id, info);
        }

        // tick data - read array length
        for (int i = 0; i < in.readVarInt(); i++) {
            // read tick id
            int tickId = in.readVarInt();

            int dataSize = in.readVarInt();

            List<Recordable> data = new ArrayList<>(dataSize);

            // read list
            for (int j = 0; j < dataSize; j++) {
                // read type
                int recordableType = in.readVarInt();
                if (!RECORDABLE_TYPES.containsKey(recordableType)) {
                    throw new UnknownRecordableTypeException(recordableType);
                }

                Recordable recordable = RECORDABLE_TYPES.get(recordableType).newInstance();

                ((RecordableSerializable) recordable).read(in);
                data.add(recordable);
            }

            replay.getReplayData().put(tickId, data);
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
            out.writeVarInt(id);
            info.write(out);
        }

        // tick data size
        out.writeVarInt(replay.getReplayData().size());

        for (Integer tickNumber : replay.getReplayData().keySet()) {
            // tick id
            out.writeVarInt(tickNumber);

            // info
            List<Recordable> tickData = replay.getReplayData().get(tickNumber);

            // write data length
            out.writeVarInt(tickData.size());

            // write data
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
