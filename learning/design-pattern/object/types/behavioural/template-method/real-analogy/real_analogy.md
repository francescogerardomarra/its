# Real Life Analogy


Imagine you’re a **game developer** working on the **loading screens** for different AAA games like *World of Warcraft* and *Diablo*.

Every game, no matter how different it looks, follows **the same general sequence** when loading:

1. load local data (code, assets, textures) from storage into memory;
2. create all the necessary in-game objects;
3. download any missing or external data (updates, online content);
4. clean up temporary files that are no longer needed;
5. initialize user profiles or saved progress.

These **steps happen in the same order** for every game, but **how** each step is performed varies, for instance:

* *World of Warcraft* might load a huge world map and hundreds of NPCs;
* *Diablo* might load darker textures and specific dungeon assets;
* both still need to clean up temporary files and that process might be identical.

To avoid rewriting the same structure each time, you define a **base loader class**, let’s call it `BaseGameLoader`.
This class defines the **template method** `loadGame()`, which calls each step in a fixed order:

```plaintext
loadGame():
    loadLocalData()
    createObjects()
    downloadAdditionalFiles()
    cleanTempFiles()
    initializeProfiles()
```

Some steps (like `cleanTempFiles()`) have a **default implementation**, while others are **abstract**, meaning each specific game must define its own version.

Then, each game has its own subclass:

* `WorldOfWarcraftLoader`;
* `DiabloLoader`.

They **override** only the steps they need to customize but **never change** the order or structure defined in `loadGame()`.
