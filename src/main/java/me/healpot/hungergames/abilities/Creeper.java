package me.healpot.hungergames.abilities;

import me.healpot.hungergames.events.PlayerKilledEvent;
import me.healpot.hungergames.interfaces.Disableable;
import me.healpot.hungergames.types.AbilityListener;
import org.bukkit.event.EventHandler;

import java.util.Random;

public class Creeper extends AbilityListener implements Disableable {

    public float baseExplosionStrength = 0.4F;
    public int randomizedStrength = 3;
    public boolean randomizeExplosion = true;

    @EventHandler
    public void onExplode(PlayerKilledEvent event) {
        if (hasAbility(event.getKilled().getPlayer())) {
            float strength = (randomizeExplosion ? new Random().nextInt(randomizedStrength + 1) : 0) + baseExplosionStrength;
            event.getDropsLocation().getWorld().createExplosion(event.getDropsLocation(), strength);
        }
    }

}
