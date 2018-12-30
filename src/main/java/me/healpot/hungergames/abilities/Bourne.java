package me.healpot.hungergames.abilities;

import me.healpot.hungergames.events.PlayerTrackEvent;
import me.healpot.hungergames.interfaces.Disableable;
import me.healpot.hungergames.types.AbilityListener;
import org.bukkit.event.EventHandler;

import java.util.Random;

public class Bourne extends AbilityListener implements Disableable {
    public int bourneRange = 15;
    public int compassModifier = 10;

    @EventHandler
    public void onTrack(PlayerTrackEvent event) {
        if (event.getVictim() != null && hasAbility(event.getVictim())
                && event.getTracker().getPlayer().getLocation().distance(event.getLocation()) <= compassModifier) {
            event.setLocation(event.getLocation().add(new Random().nextInt((compassModifier * 2) + 1) - compassModifier,
                    new Random().nextInt((compassModifier * 2) + 1) - compassModifier,
                    new Random().nextInt((compassModifier * 2) + 1) - compassModifier));
        }
    }

}
