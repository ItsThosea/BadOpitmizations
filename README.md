## BadOptimizations

This mod started off optimizing minor things and only increasing performance by a tiny bit (hence the name), but later started doing some bigger optimizations.
Most optimization mods focus on rendering performance and optimizing Minecraft's render engine, but this mod's focus is on the things other than that. This mod is to be used in conjunction with other optimization mods like Sodium.<p>

## Wait, what does this even do?
It does multiple things:<p>

- **Don't do F3 calculations if we're not in the F3 menu**<p>
  This is the biggest optimization. You'd be surprised to see how expensive the calculations are to F3, even when it's not open. Notably, the FPS string uses `String.format`, a very slow function call. This mod makes that function only get called if you're actually in the F3 menu. Don't worry, this won't break FPS counter mods.
- **Remove unnecessary thread synchronization from DataTracker / SyncedEntityData**<p>
  Minecraft uses thread locks to make sure only one thread accesses DataTracker at a time, which uses (somewhat) expensive thread locks. These are completely redundant. I wrote a temporary script that would crash the game if more than one thread accesses an entity's DataTracker, ever. The game didn't crash at all.
- **Don't do unnecessary FOV calculations if we don't need to**<p>
  Whether you're spectating a player or not, the game calculates your FOV factor (e.g. charging bow / potion effects), even if your FOV effect scale is 0. This mod removes this calculation if your FOV effect scale is 0.<p>

<p>
That's all the big optimizations. The rest don't do a lot, but still help (e.g. caching entity flags, avoiding unnecessary lerp calls)
</p>
