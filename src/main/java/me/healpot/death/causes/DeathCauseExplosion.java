package me.healpot.death.causes;

import me.healpot.death.DeathCause;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class DeathCauseExplosion extends DeathCause {

    @Override
    public Entity getKiller(EntityDamageEvent event) {
        if (event instanceof EntityDamageByEntityEvent)
            return ((EntityDamageByEntityEvent) event).getDamager();
        return null;
    }

    @Override
    public boolean isCauseOfDeath(EntityDamageEvent event) {
        return event.getCause() == DamageCause.ENTITY_EXPLOSION || event.getCause() == DamageCause.BLOCK_EXPLOSION;
    }
}
