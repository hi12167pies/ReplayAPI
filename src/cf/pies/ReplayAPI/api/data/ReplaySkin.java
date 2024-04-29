package cf.pies.ReplayAPI.api.data;

import com.mojang.authlib.properties.Property;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ReplaySkin {
    public static ReplaySkin from(Player player) {
        Property texturesProperty = ((CraftPlayer) player).getProfile().getProperties().get("textures").iterator().next();
        return new ReplaySkin(texturesProperty.getValue(), texturesProperty.getSignature());
    }

    public ReplaySkin(String value, String signature) {
        this.value = value;
        this.signature = signature;
    }

    private final String value;
    private final String signature;

    public String getSignature() {
        return signature;
    }

    public String getValue() {
        return value;
    }
}
