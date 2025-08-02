package cf.pies.replay.recordable;

/**
 * An entity recordable is a recordable that contains an entity in it.
 * Any recordables implementing this class should be able to handle all types of entities and not just players (even if currently the plugin only supports players).
 */
public interface EntityRecordable {
    int getEntityId();
}
