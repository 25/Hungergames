package me.healpot.hungergames.abilities;

import me.healpot.hungergames.interfaces.Disableable;
import me.healpot.hungergames.types.AbilityListener;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class Kangaroo extends AbilityListener implements Disableable {
    public float jumpHeight = 4F;
    public String kangarooBootsName = "Kangaroo Fireworks";
    public boolean preventFallDamageEntirely = false;

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (event.getAction().name().contains("RIGHT") && hasAbility(p) && isSpecialItem(event.getItem(), kangarooBootsName)) {
            event.setCancelled(true);
            Block b = p.getLocation().getBlock();
            if (b.getType() != Material.AIR || b.getRelative(BlockFace.DOWN).getType() != Material.AIR) {
                if (preventFallDamageEntirely) {
                    p.setFallDistance(-99999);
                } else {
                    p.setFallDistance(-(jumpHeight + 1));
                }
                Vector vector = p.getEyeLocation().getDirection();
                vector.multiply(0.6F);
                vector.setY(jumpHeight / 4F);
                p.setVelocity(vector);
            }
        }
    }
}
