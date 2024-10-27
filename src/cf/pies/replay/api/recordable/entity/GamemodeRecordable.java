package cf.pies.replay.api.recordable.entity;

import cf.pies.replay.api.ReplayPlayback;
import cf.pies.replay.api.data.SaveRecordable;
import cf.pies.replay.api.data.stream.ReplayInputStream;
import cf.pies.replay.api.data.stream.ReplayOutputStream;
import cf.pies.replay.api.npc.ReplayNPC;
import cf.pies.replay.api.recordable.Recordable;
import org.bukkit.GameMode;
import org.bukkit.World;

import java.io.IOException;

public class GamemodeRecordable implements Recordable, SaveRecordable {
    private int entityId;
    private GameMode gamemode;

    public GamemodeRecordable() {
    }

    public GamemodeRecordable(int entityId, GameMode gamemode) {
        this.entityId = entityId;
        this.gamemode = gamemode;
    }

    @Override
    public void play(ReplayPlayback playback) {
        ReplayNPC npc = playback.getNPC(entityId);
        if (npc == null) return;
        npc.setGamemode(gamemode);
    }

    @Override
    public void write(ReplayOutputStream stream) throws IOException {
        stream.writeInt(entityId);
        stream.writeGameMode(gamemode);
    }

    @Override
    public void read(ReplayInputStream stream, World world) throws IOException {
        entityId = stream.readInt();
        gamemode = stream.readGameMode();
    }
}
