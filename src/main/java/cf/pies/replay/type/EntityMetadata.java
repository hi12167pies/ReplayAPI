package cf.pies.replay.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EntityMetadata {
    private final int id;
    private final EntityType type;
}
