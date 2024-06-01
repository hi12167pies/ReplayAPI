package cf.pies.replay.api.recordable.entity;

import cf.pies.replay.api.Replay;
import cf.pies.replay.api.ReplayPlayback;
import cf.pies.replay.api.npc.ReplayNPC;
import cf.pies.replay.api.recordable.Recordable;
import org.bukkit.GameMode;

public class GamemodeRecordable implements Recordable {
    private final int entityId;
    private final GameMode gamemode;

    public GamemodeRecordable(int entityId, GameMode gamemode) {
        this.entityId = entityId;
        this.gamemode = gamemode;
    }


    @Override
    public void onRecord(Replay replay) {

    }

    @Override
    public void play(ReplayPlayback playback) {
        ReplayNPC npc = playback.getNPC(entityId);
        if (npc == null) return;
        npc.setGamemode(gamemode);
    }
}
