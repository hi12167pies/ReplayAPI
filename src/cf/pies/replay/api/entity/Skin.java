package cf.pies.replay.api.entity;

import cf.pies.replay.api.utils.NMS;
import com.mojang.authlib.properties.Property;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Iterator;

public class Skin {
    public static Skin from(Player player) {
        // Offline servers do not have a texture property since it is gotten in the player verification
        Iterator<Property> iterator = NMS.getHandle(player).getProfile().getProperties().get("textures").iterator();
        if (!iterator.hasNext()) return null;
        Property texturesProperty = iterator.next();
        return new Skin(texturesProperty.getValue(), texturesProperty.getSignature());
    }

    private final String value;
    private final String signature;

    public Skin(String value, String signature) {
        this.value = value;
        this.signature = signature;
    }

    public String getValue() {
        return value;
    }

    public String getSignature() {
        return signature;
    }
}