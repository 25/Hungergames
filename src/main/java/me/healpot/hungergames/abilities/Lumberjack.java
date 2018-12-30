package me.healpot.hungergames.abilities;

import me.healpot.hungergames.interfaces.Disableable;
import me.healpot.hungergames.types.AbilityListener;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class Lumberjack extends AbilityListener implements Disableable {
    public String[] materialsEffected = new String[]{"LOG", "LOG_2"};

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (hasAbility(event.getPlayer())) {
            for (String mat : materialsEffected) {
                if (event.getBlock().getType().name().equalsIgnoreCase(mat)) {
                    Block b = event.getBlock().getRelative(BlockFace.UP);
                    boolean hasUp = true;
                    while (hasUp) {
                        hasUp = false;
                        for (String type : materialsEffected) {
                            if (b.getType().name().equalsIgnoreCase(type)) {
                                b.breakNaturally();
                                b = b.getRelative(BlockFace.UP);
                                hasUp = true;
                                break;
                            }
                        }
                    }
                    break;
                }
            }
        }
    }

}
