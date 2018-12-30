package me.healpot.hungergames.abilities;

import me.healpot.hungergames.events.PlayerKilledEvent;
import me.healpot.hungergames.interfaces.Disableable;
import me.healpot.hungergames.types.AbilityListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class Miser extends AbilityListener implements Disableable {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onKilled(PlayerKilledEvent event) {
        if (hasAbility(event.getKilled().getPlayer().getName()))
            event.getDrops().clear();
    }

}
