package cf.pies.replay.api.entity;

public class EntityInfo {
    private final String name;
    private ReplaySkin skin = null;

    public EntityInfo(String name) {
        this.name = name;
    }

    public EntityInfo(String name, ReplaySkin skin) {
        this.name = name;
        this.skin = skin;
    }

    public String getName() {
        return name;
    }

    public ReplaySkin getSkin() {
        return skin;
    }

    public boolean hasSkin() {
        return skin != null;
    }
}
