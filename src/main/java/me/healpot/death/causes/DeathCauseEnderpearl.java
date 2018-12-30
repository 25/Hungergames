package me.healpot.death.causes;

import me.healpot.death.DeathCause;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DeathCauseEnderpearl extends DeathCause {

    @Override
    public Entity getKiller(EntityDamageEvent event) {
        return null;
    }

    @Override
    public boolean isCauseOfDeath(EntityDamageEvent event) {
        if (event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
            return e.getDamager() instanceof EnderPearl && ((EnderPearl) e.getDamager()).getShooter() == event.getEntity();
        }
        return false;
    }

}
