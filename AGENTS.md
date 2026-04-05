# AGENTS Guide for OresPlus

## Big picture
- This is a single-module NeoForge mod (`minecraft 1.21.4`, `neo 21.4.136`) with Java 21 (`build.gradle`, `gradle.properties`).
- Official NeoForge docs: https://docs.neoforged.net/docs/gettingstarted/ (source/docs repo: https://github.com/neoforged/Documentation/tree/main/docs)
- Runtime entrypoint is `src/main/java/com/splendidchip/oresplus/OresPlus.java`; nearly every registry is wired in its constructor.
- Content is split by domain packages: `item`, `block`, `block/entity`, `recipe`, `screen`, `worldgen`, `datagen`, `villager`, `util`.
- Generated assets/data are first-class in this repo: `src/generated/resources` is included in `sourceSets.main.resources`.

## Architecture and data flow
- Registry flow is centralized: add new content via `DeferredRegister` classes, then register in `OresPlus`.
- Machine flow follows this chain: block (`ModBlocks`) -> block item (`ModBlockItems`) -> block entity (`ModBlockEntities`) -> menu (`ModMenuTypes`) -> screen binding in `OresPlus.ClientModEvents.registerScreens`.
- Recipe system is custom and typed: `ModRecipes` registers serializer + type + display type for each machine recipe family (`crusher`, `simple_kiln`, `smelter`, `brick_mold`).
- Smelter behavior depends on multiblock validation (`MultiblockStructureValidator`) and upgrade gating in `SmelterControllerBlockEntity.getCurrentRecipe` (steel requires `SMELTER_UPGRADE_MODULE`).
- Worldgen pipeline is explicit: custom feature registration (`ModFeatures`) -> configured (`ModConfiguredFeatures`) -> placed (`ModPlacedFeatures`) -> biome modifiers (`ModBiomeModifiers`) -> datapack bootstrap (`ModDatapackProvider`).

## Developer workflows (verified tasks)
- Use Gradle wrapper on Windows from repo root:
  - `./gradlew.bat runClient`
  - `./gradlew.bat runServer`
  - `./gradlew.bat runData`
  - `./gradlew.bat build`
  - `./gradlew.bat test` (no `src/test` currently)
- `runData` writes into `src/generated/resources`; commit resulting JSONs when providers change.
- Mod metadata is templated: edit `gradle.properties` + `src/main/templates/META-INF/neoforge.mods.toml`; Gradle task `generateModMetadata` materializes it.
- Dev runs already enable registry-focused debug logging (`forge.logging.markers=REGISTRIES`, log level DEBUG in `build.gradle`).

## Project-specific conventions
- IDs are consistently lowercase snake_case and usually reused across Java constants, JSON file names, and translation keys.
- Tags are a key integration mechanism: see `ModTags` (`bauxite_ore_replacables`, `salt_ore_replacables`, `fluxes`, repair tags) and their generated JSON under `src/generated/resources/data/oresplus/tags`.
- Datagen is split into many focused providers (`DataGenerators`, `datagen/recipes/*`, advancement providers, model/tag/loot providers) instead of one monolithic provider.
- Keep translation keys in `src/main/resources/assets/oresplus/lang/en_us.json` synchronized with new blocks/items/GUI strings.
- There are intentional experimental/test registries (`ModTestItems`, `TEST_BLOCK_*`), so avoid deleting them unless requested.
- Commit messages in this repo are short, action-first, and pragmatic (usually `added`, `fixed`, `changed`, `Datagen - ...`).
- Prefer format: `<scope optional> <action> <what changed>` and include 1 key reason when useful.
- If a commit is primarily tracking follow-up work, use `todo:` and keep it actionable (what + where + why).
- Good examples:
  - `added smelter_io_block and recipe`
  - `Datagen - fixed Steel Axe recipe (was using pig_iron_ingot)`
  - `Fixed issue with SmelterScreen pointing to Simple smelter entity`
  - `todo: add recipe book categories for simple kiln, crusher, and smelter to improve discoverability`
- Avoid vague commit messages like `update` or `wip` for shared/history commits.

## Integration points to watch
- Vanilla integration: villager profession and POI in `ModVillagers` plus matching vanilla tag JSON (`src/main/resources/data/minecraft/tags/point_of_interest_type/acquirable_job_site.json`).
- Worldgen modifies vanilla ore distribution (example: `remove_iron_ore` biome modifier) in addition to adding OresPlus features.
- Automation/capabilities are implemented via `WorldlyContainer` and NeoForge capabilities in block entities (`CrusherBlockEntity`, `SmelterControllerBlockEntity`, `SmelterIOBlockEntity`).
