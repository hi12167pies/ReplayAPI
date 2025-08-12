package cf.pies.replay.npc;

import org.bukkit.inventory.ItemStack;

/**
 * This class acts as a wrapper for any NPC api.
 *
 */
public interface ReplayNpcPlayer extends ReplayNpc, ReplayNpcEntity {
    void animateSwing();

    void setHelmet(ItemStack stack);
    void setChestplate(ItemStack stack);
    void setLeggings(ItemStack stack);
    void setBoots(ItemStack stack);
}
