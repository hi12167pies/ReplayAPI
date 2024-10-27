package cf.pies.replay.api.recordable.entity;

import cf.pies.replay.api.ReplayPlayback;
import cf.pies.replay.api.data.SaveRecordable;
import cf.pies.replay.api.data.stream.ReplayInputStream;
import cf.pies.replay.api.data.stream.ReplayOutputStream;
import cf.pies.replay.api.npc.ReplayNPC;
import cf.pies.replay.api.recordable.Recordable;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.IOException;

public class DamageRecordable implements Recordable, SaveRecordable {
    private int entityId;

    public DamageRecordable() {
    }

    public DamageRecordable(int entityId) {
        this.entityId = entityId;
    }

    @Override
    public void play(ReplayPlayback playback) {
        ReplayNPC npc = playback.getNPC(entityId);
        if (npc == null) return;
        npc.damageAnimation();
        for (Player player : playback.getListeners()) {
            player.playSound(npc.getLocation(), Sound.HURT_FLESH, 1, 1);
        }
    }


    @Override
    public void write(ReplayOutputStream stream) throws IOException {
        stream.writeInt(entityId);
    }

    @Override
    public void read(ReplayInputStream stream, World world) throws IOException {
        entityId = stream.readInt();
    }
}
