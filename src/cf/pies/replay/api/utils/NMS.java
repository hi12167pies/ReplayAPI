package cf.pies.replay.api.utils;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

@SuppressWarnings("deprecation")
public class NMS {
    public static BlockPosition toNMS(Location location) {
        return new BlockPosition(location.getX(), location.getY(), location.getZ());
    }
    public static void sendPacket(Player player, Packet<?> packet) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    public static IBlockData getIBlockData(Material material, int data) {
        int combined;
        if (data == 0) {
            combined = material.getId();
        } else {
            combined = material.getId() + (data << 12);
        }
        return Block.getByCombinedId(combined);
    }

    public static void updateBlock(Player player, Location location, Material material, byte data) {
        player.sendBlockChange(location, material, data);
//        World world = ((CraftWorld) player.getWorld()).getHandle();
//        PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(world, toNMS(location));
//        packet.block = data;
//        sendPacket(player, packet);
    }
}
