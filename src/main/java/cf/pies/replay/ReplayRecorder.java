package cf.pies.replay;

import cf.pies.replay.recordable.Recordable;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is designed for recording replays only.
 * Its data is designed to be buffered off to a disk or deleted.
 * The playback class will ensure to keep replay data loaded or load it dynamically.
 */
@RequiredArgsConstructor
public class ReplayRecorder {
    private final ReplayBuffer buffer;

    /**
     * A list of recordables in the current tick.
     */
    private List<Recordable> currentTickRecordables = new ArrayList<>();

    /**
     * The current tick of the replay.
     * Increment with {@link ReplayRecorder#nextTick()}
     */
    private int currentTick = 0;

    /**
     * This will finish the tick by clearing the current tick array, and moving it to the buffer as well as anything else needed to complete the tick.
     * The difference is this will not advance the replay and is only for internal use.
     */
    private void finishTick() {
        // If there is no recordables, there is no point saving to the buffer or updating the array list.
        if (!currentTickRecordables.isEmpty()) {
            // Move the old tick into the completed buffer
            buffer.submit(currentTick, currentTickRecordables);

            // Create new array for current tick
            currentTickRecordables = new ArrayList<>();
        }
    }

    public void start() {
        try {
            buffer.begin();
        } catch (Exception exception) {

        }
    }

    /**
     * Advances to the next tick in the replay.
     */
    public void nextTick() {
        finishTick();
        currentTick++;
    }

    public void record(Recordable recordable) {
        currentTickRecordables.add(recordable);
    }
}
