package cf.pies.replay.api.npc;

import cf.pies.replay.api.entity.ReplaySkin;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import top.speedcubing.lib.bukkit.entity.NPC;

import java.util.UUID;

public class SpeedcubingNPC implements ReplayNPC {
    private final NPC npc;
    public SpeedcubingNPC(String name) {
        this.npc = new NPC(name, UUID.randomUUID(), true, false, false);
    }

    @Override
    public void addListener(Player player) {
        npc.addListener(player);
    }

    @Override
    public void removeListener(Player player) {
        npc.removeListener(player);
    }

    @Override
    public void spawn() {
        npc.spawn();
    }

    @Override
    public void setSkin(ReplaySkin skin) {
        npc.setSkin(skin.getValue(), skin.getSignature());
    }

    @Override
    public void remove() {
        npc.despawn();
        npc.delete();
    }

    @Override
    public void swingArm() {
        npc.animation(0);
    }

    @Override
    public void damageAnimation() {
        npc.animation(1);
    }

    @Override
    public void setSneaking(boolean sneaking) {
        npc.setSneaking(sneaking);
    }

    @Override
    public void setLocation(Location location) {
        npc.setLocation(location);
        npc.updateNpcLocation();
    }

    @Override
    public void setHolding(ItemStack stack) {
        npc.setItemInHand(stack);
    }
}
