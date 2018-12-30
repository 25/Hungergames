package me.healpot.hungergames.abilities;

import me.healpot.hungergames.interfaces.Disableable;
import me.healpot.hungergames.types.AbilityListener;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Necro extends AbilityListener implements Disableable {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if ((event.getEntity() instanceof Skeleton || event.getEntity() instanceof Zombie)
                && event.getDamager() instanceof Player && hasAbility((Player) event.getDamager()))
            event.setDamage(999);
    }
}
