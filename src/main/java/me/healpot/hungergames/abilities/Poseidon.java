package me.healpot.hungergames.abilities;

import me.healpot.hungergames.events.TimeSecondEvent;
import me.healpot.hungergames.interfaces.Disableable;
import me.healpot.hungergames.types.AbilityListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Poseidon extends AbilityListener implements Disableable {
    public int potionMultiplier = 0;

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (hasAbility(event.getPlayer()) && event.getPlayer().getRemainingAir() < 200)
            event.getPlayer().setRemainingAir(200);
    }

    @EventHandler
    public void onSecond(TimeSecondEvent event) {
        for (Player p : getMyPlayers()) {
            if (p.getLocation().getBlock().isLiquid()) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 40, potionMultiplier), true);
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, potionMultiplier), true);
            }
        }
    }

}
