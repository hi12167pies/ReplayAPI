package cf.pies.replay.api.data.stream;

import cf.pies.replay.api.Replay;
import cf.pies.replay.api.ReplayAPI;
import cf.pies.replay.api.data.SaveRecordable;
import cf.pies.replay.api.entity.EntityInfo;
import cf.pies.replay.api.entity.ReplaySkin;
import cf.pies.replay.api.recordable.Recordable;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class ReplayOutputStream extends DataOutputStream {
    public ReplayOutputStream(OutputStream out) {
        super(out);
    }

    /**
     * Writes a replay to the output stream.
     */
    public void writeReplay(Replay replay) throws IOException {
        // Write length
        writeInt(replay.getLength());

        // Write metadata
        Map<String, String> metadata = replay.getMetadata();

        writeInt(metadata.size());
        for (Map.Entry<String, String> entry : metadata.entrySet()) {
            // Write metadata key and values
            writeString(entry.getKey());
            writeString(entry.getValue());
        }

        // Write default entity info
        writeInt(replay.getEntityInfo().size());
        for (Map.Entry<Integer, EntityInfo> entry : replay.getEntityInfo().entrySet()) {
            writeInt(entry.getKey());
            writeEntityInfo(entry.getValue());
        }

        // Get map of recordables to id
        Map<Class<? extends SaveRecordable>, Integer> recordableToIdMap = ReplayAPI.getApi().getRecordableToIdMap();

        // Write replay data
        Map<Integer, List<Recordable>> data = replay.getReplayData();

        writeInt(data.size());
        for (Map.Entry<Integer, List<Recordable>> entry : data.entrySet()) {
            // Write the tick
            writeInt(entry.getKey());

            // Write all the recordables
            List<Recordable> recordables = entry.getValue();

            writeInt(recordables.size());
            for (Recordable recordable : recordables) {
                // The boolean says if the recordable is valid or not.
                if (!(recordable instanceof SaveRecordable) || !recordableToIdMap.containsKey(recordable.getClass())) {
                    writeBoolean(false);
                    continue;
                }
                writeBoolean(true);

                int recordableId = recordableToIdMap.get(recordable.getClass());
                writeInt(recordableId);
                writeRecordable((SaveRecordable) recordable);
            }
        }
    }

    /**
     * Writes a {@link cf.pies.replay.api.data.SaveRecordable} to the stream.
     */
    public void writeRecordable(SaveRecordable recordable) throws IOException {
        recordable.write(this);
    }

    /**
     * Writes a UTF-8 encoded string to the stream.
     */
    public void writeString(String string) throws IOException {
        byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
        writeInt(bytes.length);
        write(bytes);
    }

    /**
     * Writes an {@link org.bukkit.inventory.ItemStack} to the stream.
     */
    @SuppressWarnings("deprecation")
    public void writeItemStack(ItemStack stack) throws IOException {
        // Write item info
        writeMaterial(stack.getType());
        writeByte(stack.getData().getData());
    }

    /**
     * Writes a {@link cf.pies.replay.api.entity.EntityInfo} object to the stream.
     */
    public void writeEntityInfo(EntityInfo info) throws IOException {
        writeString(info.getName());
        writeBoolean(info.hasSkin());
        if (info.hasSkin()) {
            writeSkin(info.getSkin());
        }
    }

    /**
     * Writes a {@link cf.pies.replay.api.entity.ReplaySkin} to the stream.
     */
    public void writeSkin(ReplaySkin skin) throws IOException {
        writeString(skin.getValue());
        writeString(skin.getSignature());
    }

    /**
     * Writes the {@link org.bukkit.GameMode} to the stream.
     */
    @SuppressWarnings("deprecation")
    public void writeGameMode(GameMode gameMode) throws IOException {
        // Use byte as GameMode value only goes from 0-3
        writeByte(gameMode.getValue());
    }

    /**
     * Writes out a {@link org.bukkit.Material} to the stream.
     */
    @SuppressWarnings("deprecation")
    public void writeMaterial(Material material)  throws IOException {
        writeInt(material.getId());
    }

    /**
     * Writes out a {@link org.bukkit.Location} to the stream
     */
    public void writeLocation(Location location) throws IOException {
        writeDouble(location.getX());
        writeDouble(location.getY());
        writeDouble(location.getZ());
        writeFloat(location.getPitch());
        writeFloat(location.getYaw());
    }
}
