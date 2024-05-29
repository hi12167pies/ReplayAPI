package cf.pies.replay.api.utils;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

@SuppressWarnings("deprecation")
public class NMS {
    public static BlockPosition toNMS(Location location) {
        return new BlockPosition(location.getX(), location.getY(), location.getZ());
    }
    public static void sendPacket(Player player, Packet<?> packet) {
        NMS.getHandle(player).playerConnection.sendPacket(packet);
    }

    public static EntityPlayer getHandle(Player player) {
        return ((CraftPlayer) player).getHandle();
    }

    public static int getCombined(Material material, byte data) {
        int combined;
        if (data == 0) {
            combined = material.getId();
        } else {
            combined = material.getId() + (data << 12);
        }
        return combined;
    }

    public static IBlockData getIBlockData(Material material, byte data) {
        return Block.getByCombinedId(getCombined(material, data));
    }

    public static void updateBlock(Player player, Location location, Material material, byte data) {
        World world = ((CraftWorld) player.getWorld()).getHandle();
        PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(world, toNMS(location));
        packet.block = getIBlockData(material, data);
        sendPacket(player, packet);
    }
}
