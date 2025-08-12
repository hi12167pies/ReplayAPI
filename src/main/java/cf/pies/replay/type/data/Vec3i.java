package cf.pies.replay.type.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
public class Vec3i implements Serializable {
    private static final long serialVersionUID = 1;

    private final int x;
    private final int y;
    private final int z;
}
