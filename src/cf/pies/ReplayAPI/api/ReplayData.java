package cf.pies.ReplayAPI.api;

import cf.pies.ReplayAPI.api.data.ReplayBlock;
import cf.pies.ReplayAPI.api.data.ReplayItem;
import cf.pies.ReplayAPI.api.data.ReplayRecordable;
import org.bukkit.Location;

import java.util.HashMap;

public class ReplayData {
    public HashMap<ReplayPlayer, ReplayRecordable<Location>> playerLocations = new HashMap<>();
    public HashMap<ReplayPlayer, ReplayRecordable<ReplayItem>> playerItem = new HashMap<>();

    public ReplayRecordable<ReplayBlock> blocks = new ReplayRecordable<>();
}
