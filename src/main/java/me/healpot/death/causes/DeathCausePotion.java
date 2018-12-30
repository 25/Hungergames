package me.healpot.death.causes;

import me.healpot.death.DeathCause;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class DeathCausePotion extends DeathCause {

    @Override
    public Entity getKiller(EntityDamageEvent event) {
        EntityDamageByEntityEvent eventDamage = (EntityDamageByEntityEvent) event;
        ThrownPotion potion = (ThrownPotion) eventDamage.getDamager();
        if (potion.getShooter() instanceof Entity)
            return (Entity) potion.getShooter();
        return null;
    }

    @Override
    public boolean isCauseOfDeath(EntityDamageEvent event) {
        return event.getCause() == DamageCause.MAGIC;
    }

}
