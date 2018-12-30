package me.healpot.hungergames.abilities;

import me.healpot.hungergames.events.PlayerKilledEvent;
import me.healpot.hungergames.interfaces.Disableable;
import me.healpot.hungergames.types.AbilityListener;
import me.healpot.hungergames.types.HungergamesApi;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class Berserker extends AbilityListener implements Disableable {
    public int berserkerLength = 30;
    public int extraDamageMob = 2;
    public int extraDamagePlayer = 4;
    public boolean giveConfusion = true;
    private HashMap<Player, DmgBoost> dmgBuff = new HashMap<Player, DmgBoost>();

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event) {
        if (dmgBuff.containsKey(event.getDamager())) {
            DmgBoost boost = dmgBuff.get(event.getDamager());
            if (boost.expires < HungergamesApi.getHungergames().currentTime)
                dmgBuff.remove(event.getDamager());
            else
                event.setDamage(event.getDamage() + boost.extraDamage);
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null && hasAbility(event.getEntity().getKiller())) {
            Player p = event.getEntity().getKiller();
            if (event.getEntity() instanceof Creature) {
                setDamage(p, extraDamageMob);
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerKilledEvent event) {
        dmgBuff.remove(event.getKilled().getPlayer());
        if (event.getKillerPlayer() != null) {
            Player p = event.getKillerPlayer().getPlayer();
            if (hasAbility(p))
                setDamage(p, extraDamagePlayer);
        }
    }

    private void setDamage(Player p, int damage) {
        DmgBoost boost = new DmgBoost();
        boost.expires = HungergamesApi.getHungergames().currentTime + berserkerLength;
        boost.extraDamage = damage;
        dmgBuff.put(p, boost);
        if (giveConfusion)
            p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, berserkerLength * 20, 0), true);
    }

    private class DmgBoost {
        int expires;
        int extraDamage;
    }

}
