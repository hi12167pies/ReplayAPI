# v2 Branch

This branch is designed to be more flexible and allow for more features such as buffering to disk, or live replay that only records last 10 seconds (e.g. used for ban system)  

Currently, this branch is in development and should not be used unless you would like to experiment.

Breaking changes are very likely to happen

The README currently contains notes about the structure of the project, this will probably move to another file or be deleted.
This is mostly just a brain dump of information I might need later

# Initialize and disable api


The api object is not used for much unless a specific part of the api requires it.

This object is primarily used for if a part of the api needs access to the plugin, otherwise there is no other data stored in here.

You can get away without registering this stuff if you are not using playback (for now)
```java
ReplayAPI api = new ReplayAPI(plugin);

api.enable();

// remember to disable the plugin
api.disable();
```

# Replay Buffer
Replay buffers write to disk or to another location to buffer off data that is no longer needed, this will be loaded back later

Current implementations:
- `DiskBuffer` - Write to disk file

# Replay Time
Replay time is a way to track time in a replay and can be implemented specific to your use case if needed

Current implementations:
- `MinecraftTime` uses 1.8 tick system to track time

# Recorders
These are optional classes that can be used to help record replays without needing to manually record every recordable

# Entity Types
Currently, the only entity supported is players, however this plugin will be designed for multiple entity types.

# Saving
Objects that need to be buffered will be serializable, otherwise it is expected the saver handles the object saving.

# RecId vs EntityId
RecId is the entities id in the replay.
EntityId is the actual bukkit id is of entity

# RecordingReplay usage

1. Create a buffer (e.g DiskBuffer)
2. Create a time (e.g MinecraftTime)
3. Create, make your own, or use an existing recorder and add the RecordingReplay class to it (e.g BukkitRecorder)
4. Add entity to replay if needed
5. Start / Stop / Use this replay

# Origin
The origin is a location that all other locations in the replay are relative to
Example: Player spawn (for 1 player), map center (for multiplayer) or just a random location

Why? When playing the replay back the origin is needed so your playback in another location