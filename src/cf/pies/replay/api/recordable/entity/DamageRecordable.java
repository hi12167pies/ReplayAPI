package cf.pies.replay.api.recordable.entity;

import cf.pies.replay.api.Replay;
import cf.pies.replay.api.ReplayPlayback;
import cf.pies.replay.api.npc.ReplayNPC;
import cf.pies.replay.api.recordable.Recordable;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class DamageRecordable implements Recordable {
    private final int entityId;

    public DamageRecordable(int entityId) {
        this.entityId = entityId;
    }

    @Override
    public void onRecord(Replay replay) {

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
}
