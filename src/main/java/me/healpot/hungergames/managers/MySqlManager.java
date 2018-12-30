package me.healpot.hungergames.managers;

import me.healpot.hungergames.types.HungergamesApi;
import me.healpot.hungergames.types.PlayerJoinThread;
import me.healpot.hungergames.types.PlayerQuitThread;

/**
 * This class is basically useless -.-
 */
public class MySqlManager {
    private PlayerJoinThread joinThread;
    private PlayerQuitThread quitThread;

    public MySqlManager() {
        startJoinThread();
        startQuitThread();
    }

    public PlayerJoinThread getPlayerJoinThread() {
        return joinThread;
    }

    public PlayerQuitThread getPlayerQuitThread() {
        return quitThread;
    }

    public void startJoinThread() {
        joinThread = new PlayerJoinThread(HungergamesApi.getConfigManager().getMySqlConfig().isUseUUIDs());
        joinThread.start();
    }

    public void startQuitThread() {
        quitThread = new PlayerQuitThread(HungergamesApi.getConfigManager().getMySqlConfig().isUseUUIDs());
        quitThread.start();
    }

}
