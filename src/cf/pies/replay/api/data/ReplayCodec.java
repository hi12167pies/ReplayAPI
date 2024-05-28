package cf.pies.replay.api.data;

import cf.pies.replay.api.Replay;
import cf.pies.replay.api.data.stream.ReplayInputStream;
import cf.pies.replay.api.data.stream.ReplayOutputStream;
import cf.pies.replay.api.exception.ReplayException;
import cf.pies.replay.api.exception.UnknownRecordableTypeException;

import java.io.IOException;

public interface ReplayCodec {
    int getVersion();
    void read(Replay replay, ReplayInputStream in) throws IOException, ReplayException, IllegalAccessException, InstantiationException;
    void write(Replay replay, ReplayOutputStream out) throws IOException;
}
