package me.healpot.hungergames.configs;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class LoggerConfig extends BaseConfig {
    private String abilityAlreadyExists = "[hungergames] Ability %s already exists!";
    private String abilityMissingConfigValue = "[hungergames] Added ability '%s' missing config value '%s'";
    private String addedMissingConfigValue = "[hungergames] Added missing config value '%s' for config '%s'";
    private String changedWorldHeight = "[hungergames] Changed the worlds height to %s";
    private String chunksGenerated = "[hungergames] Generated %s chunks";
    private String creatingConfigFile = "[hungergames] Creating config file %s.yml";
    private String dependencyNotFound = "[hungergames] Dependency %s not found";
    private String disabledEnd = "[hungergames] Disabled the end";
    private String disabledNether = "[hungergames] Disabled the nether";
    private String disabledSpawnRadius = "[hungergames] Changed spawn radius to 0";
    private String errorAbilityDoesntExist = "[hungergames] %s's kit attempted to use the ability %s which does not exist";
    private String errorWhenCheckingForUpdate = "[hungergames] Error while checking for a update - %s";
    private String errorWhileLoadingAbility = "[hungergames] Error while loading ability %s - %s";
    private String errorWhileLoadingCommand = "[hungergames] Error while loading command %s - %s";
    private String errorWhileLoadingConfig = "[hungergames] Error while loading config %s.yml - %s";
    private String errorWhileLoadingConfigValue = "[hungergames] Error while loading config %s.yml, %s threw a error - %s";
    private String errorWhileLoadingSpawns = "[hungergames] Error while loading spawns, Spawn %s is missing config value %s";
    private String errorWhileLoadingStatsCommand = "[hungergames] The command %s will not be loaded because either mysql or stats is disabled";
    private String errorWhileParsingItemStack = "[hungergames] Error while parsing itemstack line %s, %s";
    private String failedToFindVault = "[hungergames] Failed to find vault. Vault support not enabled";
    private String failedToRegisterVault = "[hungergames] Failed to register vault. No currency plugins found. (Example: Economy, iConomy, BOSEconomy, EssentialsEcon, 3Co, MultiCurrency, MineConomy)";
    private String generatingChunks = "[hungergames] Generating chunks.. %s";
    private String kitAlreadyExists = "[hungergames] Kit %s already exists!";
    private String loadAbilitiesPackage = "[hungergames] Adding abilities from plugin %s in package %s";
    private String loadCommandsPackage = "[hungergames] Adding commands from plugin %s in package %s";
    private String loadedSpawnOutsideBorder = "[hungergames] You may be interested to know that the spawn %s is outside the border."
            + " Try looking at turning 'forcedCords' in the main config off";
    private String loadedSpawns = "[hungergames] Loaded %s player spawns";
    private String loadedSpawnsConfig = "[hungergames] Loaded %s spawns";
    private String loadingConfigFile = "[hungergames] Loading config file %s.yml";
    private String loadingSpawns = "[hungergames] Loading player spawns";
    private String loadSpawnsConfig = "[hungergames] Loading the spawns";
    private String loadSpawnsConfigError = "[hungergames] Error while loading spawns, spawn %s is missing configuration %s";
    private String loadSpawnsConfigNotFound = "[hungergames] Spawns config not found";
    private String mapConfigChangedBorderCloseInRate = "[hungergames] Map config - Changed border close in rate to %s";
    private String mapConfigChangedBorderSize = "[hungergames] Map config - Changed border size to %s";
    private String mapConfigChangedFeastInformation = "[hungergames] Modified feast to spawn at X: %s and Z: %s";
    private String mapConfigChangedRoundedBorder = "[hungergames] Map config - Changed rounded border to %s";
    private String mapConfigChangedTimeOfDay = "[hungergames] Map config - Changed time of day game starts as %s";
    private String mapConfigLoaded = "[hungergames] Successfully loaded map config";
    private String mapConfigNotFound = "[hungergames] Map config not found";
    private String mapConfigNowLoading = "[hungergames] Now loading map config";
    private String metricsMessage = "[hungergames] Dangit. Think you can opt back into metrics for me? I do want to see how popular my plugin is..";
    private String modifiedForTerrainControl = "[hungergames] Modified bukkit.yml to use TerrainControl";
    private String mySqlClosing = "[%s] Disconnecting from MySQL database...";
    private String mySqlClosingError = "[%s] Error while closing the connection...";
    private String mySqlConnecting = "[%s] Connecting to MySQL database..";
    private String mySqlConnectingError = "[%s] Error while connecting to MySQL. %s";
    private String mySqlErrorLoadPlayer = "[PlayerJoinThread] Error while loading player %s - %s";
    private String mySqlErrorSaveStats = "[hungergames] Error while saving stats for player %s - %s";
    private String noMapsFound = "[hungergames] No maps found in %s";
    private String NowAttemptingToLoadAMap = "[hungergames] Now attempting to load a map from the path %s";
    private String registeredVault = "[hungergames] Found and registered vault!";
    private String restoredCommandsMissingConfigValue = "[hungergames] Restored missing config '%s' for command '%s'";
    private String shutdownCancelled = "[hungergames] Shutdown event was cancelled by some plugin!";
    private String shuttingDown = "[hungergames] hungergames is now shutting the server down!";
    private String successfullyLoadedMap = "[hungergames] Successfully loaded map %s";
    private String unrecognisedItemId = "[hungergames] Failed to recognise item ID %s";
    private String waitingForStatsToSave = "[hungergames] Waiting for stats to save. %s stats left. %s seconds before I give up";

    public LoggerConfig() {
        super("errors");
    }

}
