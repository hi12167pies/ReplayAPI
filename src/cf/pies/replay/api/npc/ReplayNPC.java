package cf.pies.replay.api.npc;

import cf.pies.replay.api.entity.ReplaySkin;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface ReplayNPC {
    /**
     * Adds a player that can see and listen to npc actions
     */
    void addListener(Player player);

    /**
     * Removes a player from the listeners
     */
    void removeListener(Player player);

    /**
     * Spawns the npc, called after listeners are added
     */
    void spawn();

    /**
     * Sets the skin of the npc
     */
    void setSkin(ReplaySkin skin);

    /**
     * Removes and deletes the npc
     */
    void remove();

    /**
     * Swings the npc arm
     */
    void swingArm();

    /**
     * Plays the damage animation to the npc
     */
    void damageAnimation();

    /**
     * Changes if the npc is sneaking or not
     */
    void setSneaking(boolean sneaking);

    /**
     * Teleports the npc to the new location
     */
    void setLocation(Location location);

    /**
     * Sets the item in the npc hand
     */
    void setHolding(ItemStack stack);
}
