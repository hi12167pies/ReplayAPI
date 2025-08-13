package cf.pies.replay.saver;

import cf.pies.replay.recording.ReplaySession;
import cf.pies.replay.stream.ReplayOutputStream;

import java.io.OutputStream;

public abstract class BaseStreamSaver implements ReplaySaver {
    private void writeReplay(ReplaySession session, OutputStream outputStream) {
        ReplayOutputStream stream = new ReplayOutputStream(outputStream);


    }
}
