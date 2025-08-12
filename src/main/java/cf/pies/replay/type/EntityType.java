package cf.pies.replay.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

@Getter
@RequiredArgsConstructor
public enum EntityType {
    PLAYER(1, Player.class);

    private final int id;
    private final Class<? extends Entity> entityClass;
}
