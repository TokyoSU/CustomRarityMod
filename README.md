# RarityJS

**RarityJS** is a Minecraft Forge mod for **Minecraft 1.20.1** that lets modpack authors create and assign custom item rarities through **KubeJS**.

It can apply rarities to individual items, whole mods, item tags, or specific NBT variants, and it also includes a Creative-mode rarity editor for configuring items directly in-game.

## Features

- Create custom rarities from KubeJS startup scripts.
- Assign rarities to:
  - individual items;
  - item tags;
  - specific item NBT;
  - every item from a mod;
  - every otherwise-unmatched item through a default rarity.
- Replace Minecraft's built-in rarities without overriding explicit RarityJS assignments.
- Includes extra built-in rarities such as **Unique**, **Legendary**, **Mythic**, and **God**.
- Creative-mode **Rarity Editor** opened with **F7** by default.
- Export editor changes to a KubeJS startup script with `/rarityjs export <filename>`.
- Optional **Obscure Tooltips** visuals through the bundled **Rarity Tooltips** resource pack when Fragmentum is installed.
- JEI-aware editor GUI integration when JEI is present.

## Requirements

RarityJS 1.1.7 targets:

- Minecraft **1.20.1**
- Minecraft Forge **47+**
- KubeJS **2001.6.5-build.26+**
- ApocalypseLib **1.1.7+**

Optional integrations:

- Fragmentum **5.0.0+**
- Obscure Tooltips **3.10.1+**
- JEI

## Quick Start

Create a file such as:

```text
kubejs/startup_scripts/rarities.js
```

Then register your rarity configuration:

```js
RarityJSEvents.register(event => {
    event.addRarity('example.ancient', 'dark_aqua')

    event.setRarity('minecraft:nether_star', 'example.ancient')
    event.setRarityByTag('forge:ingots/netherite', 'raritymod.legendary')
    event.setRarityByMod('examplemod', 'raritymod.unique')
})
```

RarityJS registers its event during startup, so restart the game/server after changing RarityJS startup configuration.

For the complete API, examples, rule priority, and NBT usage, see [kubejs.md](kubejs.md).

## Built-in Rarities

RarityJS automatically exposes Minecraft's normal rarities and several additional rarities.

| ID | Display color |
| --- | --- |
| `minecraft:common` | White |
| `minecraft:uncommon` | Yellow |
| `minecraft:rare` | Aqua |
| `minecraft:epic` | Light Purple |
| `raritymod.common` | White |
| `raritymod.uncommon` | Green |
| `raritymod.rare` | Blue |
| `raritymod.unique` | Gold |
| `raritymod.legendary` | Yellow |
| `raritymod.epic` | Light Purple |
| `raritymod.mythic` | Red |
| `raritymod.god` | Red |

You can use these IDs directly without calling `addRarity()` first.

## Rarity Rule Priority

When more than one rule could apply to an item, RarityJS evaluates them in this order:

1. Item tag rarity
2. NBT rarity
3. Exact item rarity
4. Mod rarity
5. Minecraft rarity replacement
6. Default rarity
7. The item's original rarity

For example, an exact item assignment overrides a mod-wide assignment, while an NBT assignment overrides both of them.

A rarity created by `setRarity`, `setRarityByNBT`, `setRarityByTag`, or `setRarityByMod` is returned before Minecraft rarity replacement is checked. This means `replaceRarityBy()` does not overwrite your explicit RarityJS assignments.

## Rarity Editor

RarityJS includes an in-game editor intended for modpack development.

### Opening the editor

- Enter Creative mode.
- Press **F7**.
- The key can be changed from Minecraft's Controls menu.

### Assigning a rarity

Right-click an item in the editor and select a rarity from the dropdown.

- If the selected stack has NBT, the editor creates an NBT-based rarity assignment.
- If the selected stack has no NBT, the editor assigns the rarity directly to the item ID.

The editor can use Minecraft rarities, RarityJS built-in rarities, and custom rarities that were registered through KubeJS during startup.

## Exporting Editor Changes

Use:

```text
/rarityjs export <filename>
```

Example:

```text
/rarityjs export my_rarities
```

This creates:

```text
kubejs/startup_scripts/my_rarities.js
```

The exporter writes the currently registered:

- item assignments;
- mod assignments;
- tag assignments;
- NBT assignments;
- default rarity.

> **Important:** the current exporter does not write `addRarity()` declarations or `replaceRarityBy()` rules. Keep those definitions in your own startup script when you use custom rarities or rarity replacements.

The `/rarityjs export` command requires command permission level **1**.

## Obscure Tooltips Integration

RarityJS contains a built-in client resource pack named **Rarity Tooltips**.

When **Fragmentum** is installed, RarityJS registers this bundled resource pack automatically. The pack contains Obscure Tooltips definitions, frames, panels, labels, icons, and effects for RarityJS rarities.

For the intended enhanced tooltip visuals, install both **Fragmentum** and **Obscure Tooltips**.

## KubeJS Documentation

See [kubejs.md](kubejs.md) for:

- `addRarity()`
- `setRarity()`
- `setRarityByNBT()`
- `setRarityByTag()`
- `setRarityByMod()`
- `setDefaultRarity()`
- `replaceRarityBy()`
- complete examples and rule priority

## Building From Source

The project uses Gradle and the Minecraft Forge development environment.

On Windows:

```bat
gradlew.bat build
```

On Linux/macOS:

```sh
./gradlew build
```

The compiled mod will be placed in:

```text
build/libs/
```

## CurseForge

RarityJS was previously published under the Custom Rarity CurseForge project:

https://www.curseforge.com/minecraft/mc-mods/custom-rarity

## Credits

Created by **TokyoSU**.

Minecraft Forge, KubeJS, ApocalypseLib, Fragmentum, Obscure Tooltips, and JEI are separate projects owned by their respective authors.
