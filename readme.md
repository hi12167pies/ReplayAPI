ReplayAPI made for spigot 1.8.8

```java
Replay replay = new Replay(origin);

replay.addPlayer(player);

// by default the replays do not record them self,
// this will add it to a list and use bukkit events to record
replay.addRecording();

replay.start();

replay.end();
```

```java
ReplayPlayback playback = new ReplayPlayback(plugin, replay, origin,);

playback.addListener(player);

playback.start();

playback.pause();

playback.end();
```