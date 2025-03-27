
ReplayAPI made for spigot 1.8.8
---

Requirements:
- [speedcubingLib](https://github.com/speedcubing-top/speedcubingLib) for NPCS.
  - If you do not want to use speedcubingLib you can implement your own npc in `cf.pies.replay.npc` - Usage is in the `ReplayPlayback`

# Table of contents
- [Table of contents](#table-of-contents)
- [Recording](#recording)
- [Playback](#playback)
- [Events](#events)
  - [`PlaybackEndEvent`](#playbackendevent)
  - [`PlaybackStateChangeEvent`](#playbackstatechangeevent)
- [Saving / Loading replays.](#saving--loading-replays)
  - [Saving](#saving)
  - [Loading](#loading)

# Initialize 
To set up all the recording, and recordables you must initialize the api with a plugin.

```java
ReplayAPI.getApi().initalize(plugin);
```

# Recording
```java
// Create a replay object.
// The replay object stores all the data for the replay.
// Also is used for recording the replay.
Replay replay = new Replay(origin);

// If you add a player before the replay starts they are a default player.
replay.addPlayer(player);

// Add the repaly to a recording list.
// The recording list uses the default bukkit events to record the players actions.
// You can use a custom recorder using replay#record(Recordable)
replay.addRecording();

// This will start recording the replay, anything done from now on will be recorded.
replay.start();

// Once the replay is finished, we no longer need to record it.
// To prevent a memory leak we need to remove it from the recording list.
replay.removeRecording();

// Now you can end the replay.
replay.end();
```

# Playback
```java
// This will create the playback object.
ReplayPlayback playback = new ReplayPlayback(plugin, replay, origin);

// The playback can be watched by any amount of players.
// Each player must be added as a listener.
playback.addListener(player);

// Now that there are listeners we can setup the replay. This creates NPCS and does any setup work required.
playback.setup();

// Now that it's setup we can play the replay.
playback.play();

// If we needed to we can pause it.
playback.pause();

// This will undo the replay, remove all the npcs and reset the map.
playback.end();
```
Notes:
- Once the replay is finished it will go into the `PAUSED` state, you will have to run end yourself.

# Events
## `PlaybackEndEvent`
Called when the playback ends

## `PlaybackStateChangeEvent`
Called when the state of a playback is changed.

# Saving / Loading replays.
When saving and loading replays you are using the `Replay` object. This object contains all the data for the replay.

## Saving
To save a replay you will use the `ReplayOutputStream`.
```java
ReplayOutputStream replayOutputStream = new ReplayOutputStream(...);
replayOutputStream.writeReplay(replay);
```

## Loading
To load the replay back you must use a `ReplayInputStream`
```java
ReplayInputStream replayInputStream = new ReplayInputStream(...);

// The origin is required mostly for the world. You can provide (0, 0, 0) if you wanted to you only need a world.
Replay replay = replayInputStream.readReplay(origin);
```