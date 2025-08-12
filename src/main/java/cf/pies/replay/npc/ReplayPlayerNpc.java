package cf.pies.replay.npc;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

/**
 * This class acts as a wrapper for any NPC api.
 *
 */
public interface ReplayPlayerNpc extends ReplayNpc {
    void setLocation(Location location);

    void setHelmet(ItemStack stack);
    void setChestplate(ItemStack stack);
    void setLeggings(ItemStack stack);
    void setBoots(ItemStack stack);
}
