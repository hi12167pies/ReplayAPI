package cf.pies.replay.api.data;

import cf.pies.replay.api.Replay;
import cf.pies.replay.api.data.stream.ReplayInputStream;
import cf.pies.replay.api.data.stream.ReplayOutputStream;
import cf.pies.replay.api.exception.ReplayException;
import cf.pies.replay.api.exception.UnknownRecordableTypeException;

import java.io.IOException;

public interface ReplayCodec {
    void read(Replay replay, ReplayInputStream in) throws IOException, ReplayException;
    void write(Replay replay, ReplayOutputStream out) throws IOException;
}
