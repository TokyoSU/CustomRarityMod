# RarityJS — KubeJS Documentation

RarityJS exposes a KubeJS startup event named:

```js
RarityJSEvents.register(event => {
    // Rarity configuration goes here.
})
```

Place RarityJS configuration in:

```text
kubejs/startup_scripts/
```

Example file:

```text
kubejs/startup_scripts/rarities.js
```

RarityJS posts its registration event during mod startup, so restart the game/server after changing these scripts.

---

## Complete Example

```js
RarityJSEvents.register(event => {
    // Create a custom rarity.
    event.addRarity('example.ancient', 'dark_aqua')

    // Exact item.
    event.setRarity('minecraft:nether_star', 'example.ancient')

    // Item tag. Do not include '#'.
    event.setRarityByTag('forge:ingots/netherite', 'raritymod.legendary')

    // Specific NBT variant of an item.
    event.setRarityByNBT(
        'minecraft:diamond_sword',
        '{CustomModelData:123}',
        'raritymod.mythic'
    )

    // Every item from a mod.
    event.setRarityByMod('examplemod', 'raritymod.unique')

    // Replace Minecraft's native Rare rarity when no explicit rule above matched.
    event.replaceRarityBy('minecraft:rare', 'raritymod.legendary')

    // Optional global fallback.
    // event.setDefaultRarity('minecraft:common')
})
```

---

## Built-in Rarity IDs

The following rarities are available automatically.

### Minecraft

```text
minecraft:common
minecraft:uncommon
minecraft:rare
minecraft:epic
```

### RarityJS

```text
raritymod.common
raritymod.uncommon
raritymod.rare
raritymod.unique
raritymod.legendary
raritymod.epic
raritymod.mythic
raritymod.god
```

You do not need to call `addRarity()` for any of these IDs.

---

## `addRarity(name, formattingName)`

Creates a new runtime rarity.

```js
RarityJSEvents.register(event => {
    event.addRarity('example.ancient', 'dark_aqua')
})
```

### Parameters

| Parameter | Description |
| --- | --- |
| `name` | Unique rarity identifier. Example: `example.ancient` |
| `formattingName` | Minecraft `ChatFormatting` color name. Example: `dark_aqua` |

Common formatting names include:

```text
black
dark_blue
dark_green
dark_aqua
dark_red
dark_purple
gold
gray
dark_gray
blue
green
aqua
red
light_purple
yellow
white
```

Use a valid color name; RarityJS resolves it through Minecraft's `ChatFormatting.getByName()`.

### Registration behavior

`addRarity()` uses first-registration-wins behavior. Registering the same rarity name again does not replace the existing rarity.

---

## `setRarity(resourceName, rarityName)`

Assigns a rarity to one exact item ID.

```js
RarityJSEvents.register(event => {
    event.setRarity('minecraft:nether_star', 'raritymod.god')
    event.setRarity('minecraft:elytra', 'raritymod.legendary')
})
```

### Parameters

| Parameter | Description |
| --- | --- |
| `resourceName` | Item ID, such as `minecraft:nether_star` |
| `rarityName` | Registered rarity ID |

Calling `setRarity()` again for the same item replaces the previous item mapping.

---

## `setRarityByNBT(resourceName, nbt, rarityName)`

Assigns a rarity only when the specified item also matches the provided NBT data.

```js
RarityJSEvents.register(event => {
    event.setRarityByNBT(
        'minecraft:diamond_sword',
        '{CustomModelData:123}',
        'raritymod.mythic'
    )
})
```

Another example:

```js
RarityJSEvents.register(event => {
    event.setRarityByNBT(
        'minecraft:paper',
        '{display:{Name:\'{"text":"Ancient Scroll"}\'}}',
        'raritymod.legendary'
    )
})
```

The NBT argument is parsed as SNBT.

### Current limitation

RarityJS currently stores **one NBT rule per item ID**. Calling `setRarityByNBT()` again for the same item replaces the previous NBT rule.

If you need several NBT variants of the same item to have different rarities, the current 1.1.7 implementation would need to be extended to store a list of NBT rules per item.

---

## `setRarityByTag(tagID, rarityName)`

Assigns a rarity to items belonging to an item tag.

```js
RarityJSEvents.register(event => {
    event.setRarityByTag('forge:gems/diamond', 'raritymod.rare')
    event.setRarityByTag('forge:ingots/netherite', 'raritymod.legendary')
})
```

Do **not** prefix the tag with `#`.

Use:

```js
event.setRarityByTag('forge:gems/diamond', 'raritymod.rare')
```

Not:

```js
event.setRarityByTag('#forge:gems/diamond', 'raritymod.rare')
```

Calling `setRarityByTag()` again for the same tag replaces its previous mapping.

### Multiple matching tags

Tag rules have the highest RarityJS priority. If an item belongs to several tags that each have different RarityJS rarities, the first matching tag returned by Minecraft will be used. Avoid conflicting tag rarity rules when deterministic behavior matters.

---

## `setRarityByMod(modId, rarityName)`

Assigns one rarity to every otherwise-unmatched item from a mod namespace.

```js
RarityJSEvents.register(event => {
    event.setRarityByMod('examplemod', 'raritymod.unique')
})
```

Use the namespace only:

```text
examplemod
minecraft
create
mekanism
```

Do not use an item ID such as `examplemod:item_name` here.

### Registration behavior

`setRarityByMod()` uses first-registration-wins behavior. Calling it again for the same mod ID does not replace the existing mod mapping.

---

## `setDefaultRarity(rarityName)`

Sets a global fallback rarity.

```js
RarityJSEvents.register(event => {
    event.setDefaultRarity('raritymod.common')
})
```

Minecraft rarity IDs are also accepted:

```js
RarityJSEvents.register(event => {
    event.setDefaultRarity('minecraft:uncommon')
})
```

The default rarity is only used after tag, NBT, item, mod, and vanilla-rarity replacement rules fail to produce a rarity.

Calling `setDefaultRarity()` again replaces the previous default.

---

## `replaceRarityBy(oldRarity, newRarity)`

Replaces one of Minecraft's built-in rarities with another registered rarity.

```js
RarityJSEvents.register(event => {
    event.replaceRarityBy('minecraft:rare', 'raritymod.legendary')
    event.replaceRarityBy('minecraft:epic', 'raritymod.god')
})
```

The replacement target may also be another Minecraft rarity:

```js
RarityJSEvents.register(event => {
    event.replaceRarityBy('minecraft:uncommon', 'minecraft:rare')
})
```

### Supported source rarities

Only Minecraft's built-in source rarities can be replaced:

```text
minecraft:common
minecraft:uncommon
minecraft:rare
minecraft:epic
```

A custom rarity cannot be used as the `oldRarity` source.

### Explicit assignments are not replaced

`replaceRarityBy()` is deliberately checked **after** tag, NBT, item, and mod assignments.

For example:

```js
RarityJSEvents.register(event => {
    event.setRarity('minecraft:diamond', 'raritymod.unique')
    event.replaceRarityBy('minecraft:rare', 'raritymod.legendary')
})
```

The diamond remains `raritymod.unique`. The replacement rule does not overwrite the result of `setRarity()`.

### Registration behavior

`replaceRarityBy()` uses first-registration-wins behavior for each source rarity.

---

## Rule Priority

RarityJS evaluates rarity rules in the following order:

| Priority | Rule |
| ---: | --- |
| 1 | `setRarityByTag()` |
| 2 | `setRarityByNBT()` |
| 3 | `setRarity()` |
| 4 | `setRarityByMod()` |
| 5 | `replaceRarityBy()` |
| 6 | `setDefaultRarity()` |
| 7 | Original item rarity |

Example:

```js
RarityJSEvents.register(event => {
    event.setRarityByMod('minecraft', 'raritymod.uncommon')
    event.setRarity('minecraft:diamond', 'raritymod.rare')
    event.setRarityByTag('forge:gems/diamond', 'raritymod.legendary')
})
```

If `minecraft:diamond` belongs to `forge:gems/diamond`, it becomes `raritymod.legendary` because the tag rule has higher priority than the exact item and mod rules.

---

## Rarity Editor + KubeJS Export

RarityJS includes a Creative-mode item rarity editor.

1. Enter Creative mode.
2. Press **F7**.
3. Right-click an item.
4. Select a rarity.

If the selected item stack contains NBT, the editor creates an NBT rarity rule. Otherwise it creates an exact item rarity rule.

To export the current mappings:

```text
/rarityjs export <filename>
```

Example:

```text
/rarityjs export generated_rarities
```

Output:

```text
kubejs/startup_scripts/generated_rarities.js
```

The exporter currently writes:

```text
setRarity(...)
setRarityByMod(...)
setRarityByTag(...)
setRarityByNBT(...)
setDefaultRarity(...)
```

It currently does **not** export:

```text
addRarity(...)
replaceRarityBy(...)
```

Keep custom rarity declarations and rarity replacement rules in a separate startup script, or add them manually to the exported file.

---

## Recommended Script Layout

For larger modpacks, separating rarity definitions from assignments keeps the configuration easier to maintain.

### `kubejs/startup_scripts/rarity_definitions.js`

```js
RarityJSEvents.register(event => {
    event.addRarity('pack.ancient', 'dark_aqua')
    event.addRarity('pack.relic', 'gold')

    event.replaceRarityBy('minecraft:epic', 'pack.relic')
})
```

### `kubejs/startup_scripts/rarity_items.js`

```js
RarityJSEvents.register(event => {
    event.setRarity('minecraft:nether_star', 'pack.relic')
    event.setRarity('minecraft:heart_of_the_sea', 'pack.ancient')
    event.setRarityByMod('examplemod', 'raritymod.unique')
})
```

---

## Troubleshooting

### My rarity does not appear

Make sure the rarity exists before using its ID. Either use one of RarityJS's built-in IDs or register your own rarity with `addRarity()`.

Also verify that the script is inside `kubejs/startup_scripts/` and restart the game/server.

### My item rarity is being overridden

Check the priority table. Tag and NBT rules take priority over exact item rules, and exact item rules take priority over mod-wide rules.

### `replaceRarityBy()` does not change an explicitly assigned item

This is expected. Replacement only processes the original Minecraft rarity after RarityJS tag/NBT/item/mod assignments have been checked.

### My second NBT rule for the same item replaced the first one

This is a current implementation limitation. RarityJS 1.1.7 stores a single NBT rule for each item ID.

### The enhanced tooltip style is missing

Install the optional **Fragmentum** and **Obscure Tooltips** integrations. Fragmentum allows RarityJS to expose its bundled **Rarity Tooltips** client resource pack.
