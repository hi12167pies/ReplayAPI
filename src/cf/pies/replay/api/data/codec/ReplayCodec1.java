package cf.pies.replay.api.data.codec;

import cf.pies.replay.api.Replay;
import cf.pies.replay.api.data.ReplayCodec;
import cf.pies.replay.api.data.stream.ReplayInputStream;
import cf.pies.replay.api.data.stream.ReplayOutputStream;

import java.io.IOException;

public class ReplayCodec1 implements ReplayCodec {
    @Override
    public void read(Replay replay, ReplayInputStream in) throws IOException {

    }

    @Override
    public void write(Replay replay, ReplayOutputStream out) throws IOException {
        for (Integer id : replay.getEntityInfo().keySet()) {
            out.writeInt(id);
        }
    }
}
