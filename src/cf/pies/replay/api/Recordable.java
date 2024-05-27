package cf.pies.replay.api;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class Recordable<T> {
    private final Replay replay;
    private HashMap<Integer, T> info = new HashMap<>();
    public Recordable(Replay replay) {
        this.replay = replay;
    }

    public void record(Player player, T data) {

    }
}
