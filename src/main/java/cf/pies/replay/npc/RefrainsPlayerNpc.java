package cf.pies.replay.npc;

import cf.pies.replay.ReplayAPI;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import nz.blair.npcs.NpcsApi;
import nz.blair.npcs.npcs.Animation;
import nz.blair.npcs.npcs.Npc;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RefrainsPlayerNpc implements ReplayNpcPlayer {
    private static PlayerConnection getNMSConnection(Player player) {
        return ((CraftPlayer) player).getHandle().playerConnection;
    }

    private final NpcsApi api;

    // Marked protected in-case someone wants to extend this
    protected Npc npc;

    public RefrainsPlayerNpc(ReplayAPI api) {
        this.api = api.getNpcsApi();
    }

    public void create(String name, Location location) {
        this.npc = api.createNpc(name, location, false);
    }

    @Override
    public void delete() {
        api.deleteNpc(npc);
    }

    @Override
    public void addListener(Player player) {
        npc.addAllowedConnection(getNMSConnection(player));
    }

    @Override
    public void removeListener(Player player) {
        npc.removeAllowedConnection(getNMSConnection(player));
    }

    @Override
    public void setLocation(Location location) {
        npc.setLocation(location);
    }

    @Override
    public void animateDamage() {
        npc.playAnimation(Animation.TAKE_DAMAGE);
    }

    @Override
    public void animateSwing() {
        npc.playAnimation(Animation.SWING_ARM);
    }

    @Override
    public void setHelmet(ItemStack stack) {
        npc.setHelmet(stack);
    }

    @Override
    public void setChestplate(ItemStack stack) {
        npc.setChestplate(stack);
    }

    @Override
    public void setLeggings(ItemStack stack) {
        npc.setLeggings(stack);
    }

    @Override
    public void setBoots(ItemStack stack) {
        npc.setBoots(stack);
    }
}
