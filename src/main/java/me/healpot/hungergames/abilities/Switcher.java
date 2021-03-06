package me.healpot.hungergames.abilities;

import me.healpot.hungergames.Hungergames;
import me.healpot.hungergames.events.PlayerKilledEvent;
import me.healpot.hungergames.interfaces.Disableable;
import me.healpot.hungergames.types.AbilityListener;
import me.healpot.hungergames.types.Gamer;
import me.healpot.hungergames.types.HungergamesApi;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;

public class Switcher extends AbilityListener implements Disableable {
    public int cooldownLength = 10;
    public String cooldownMessage = ChatColor.BLUE + "You can throw another snowball in %s seconds";
    private HashMap<Player, Integer> cooldown = new HashMap<Player, Integer>();
    private Hungergames hg = HungergamesApi.getHungergames();

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager().hasMetadata("Switcher")) {
            Gamer thrower = HungergamesApi.getPlayerManager().getGamer(
                    ((Player) ((Snowball) event.getDamager()).getShooter()).getName());
            if (thrower != null && thrower.isAlive()) {
                Location loc1 = thrower.getPlayer().getLocation().clone();
                Location loc2 = event.getEntity().getLocation().clone();
                thrower.getPlayer().teleport(loc2);
                event.getEntity().teleport(loc1);
            }
        }
    }

    @EventHandler
    public void onKilled(PlayerKilledEvent event) {
        if (hasAbility(event.getKilled().getPlayer()))
            cooldown.remove(event.getKilled().getPlayer());
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (event.getEntityType() == EntityType.SNOWBALL) {
            if (event.getEntity().getShooter() != null && event.getEntity().getShooter() instanceof Player) {
                Player p = (Player) event.getEntity().getShooter();
                if (!hasAbility(p))
                    return;
                if (cooldown.containsKey(p) && cooldown.get(p) > hg.currentTime) {
                    event.setCancelled(true);
                    ItemStack snowball = HungergamesApi.getKitManager().parseItem(
                            "SNOW_BALL 0 1 Unlootable 1 Name=Switcher_Snowball")[0];
                    HungergamesApi.getKitManager().addItem(p, snowball);
                    p.updateInventory();
                    p.sendMessage(String.format(cooldownMessage, cooldown.get(p) - hg.currentTime));
                    return;
                }
                event.getEntity().setMetadata("Switcher", new FixedMetadataValue(hg, p.getName()));
                cooldown.put(p, hg.currentTime + cooldownLength);
            }
        }
    }
}
