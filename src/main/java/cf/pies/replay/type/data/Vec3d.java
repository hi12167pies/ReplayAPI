package cf.pies.replay.type.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
public class Vec3d implements Serializable {
    private static final long serialVersionUID = 1;

    private final double x;
    private final double y;
    private final double z;
}
