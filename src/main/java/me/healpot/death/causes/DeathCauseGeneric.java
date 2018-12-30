package me.healpot.death.causes;

import me.healpot.death.DeathCause;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Used when isCauseOfDeath does not matter. getKiller does not matter. Just needs a object to fed.
 */
public class DeathCauseGeneric extends DeathCause {


    @Override
    public Entity getKiller(EntityDamageEvent event) {
        return null;
    }

    @Override
    public boolean isCauseOfDeath(EntityDamageEvent event) {
        return false;
    }

}
