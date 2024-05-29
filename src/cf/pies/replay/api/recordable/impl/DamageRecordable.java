package cf.pies.replay.api.recordable.impl;

import cf.pies.replay.api.Replay;
import cf.pies.replay.api.ReplayPlayback;
import cf.pies.replay.api.recordable.Recordable;
import top.speedcubing.lib.bukkit.entity.NPC;

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
        NPC npc = playback.getNPC(entityId);
        if (npc == null) return;
        npc.animation(1);
    }
}
