ReplayAPI made for spigot 1.8.8

Requires [speedcubingLib](https://github.com/speedcubing-top/speedcubingLib) for NPCS (Optional, you can fork to use your own npc lib)

```java
Replay replay = new Replay(origin);

replay.addPlayer(player);

// by default the replays do not record them self,
// this will add it to a list and use bukkit events to record
replay.addRecording();
replay.start();

replay.removeRecording();
replay.end();
```

```java
ReplayPlayback playback = new ReplayPlayback(plugin, replay, origin);

playback.addListener(player);

playback.setup();
playback.play();

playback.pause();

playback.end();
```
