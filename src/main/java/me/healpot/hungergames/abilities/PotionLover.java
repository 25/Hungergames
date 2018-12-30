package me.healpot.hungergames.abilities;

import me.healpot.hungergames.events.GameStartEvent;
import me.healpot.hungergames.interfaces.Disableable;
import me.healpot.hungergames.managers.KitManager;
import me.healpot.hungergames.types.AbilityListener;
import me.healpot.hungergames.types.HungergamesApi;
import me.healpot.hungergames.types.Kit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionLover extends AbilityListener implements Disableable {

    public String[] potionEffects = new String[]{"Ghost INVISIBILITY 2600 0"};

    @EventHandler
    public void onGameStart(GameStartEvent event) {
        KitManager kits = HungergamesApi.getKitManager();
        for (String effect : potionEffects) {
            String[] split = effect.split(" ");
            PotionEffect potionEffect = new PotionEffect(PotionEffectType.getByName(split[1].toUpperCase()),
                    Integer.parseInt(split[2]), Integer.parseInt(split[3]));
            for (Player p : getMyPlayers()) {
                Kit kit = kits.getKitByPlayer(p);
                if (kit.getName().equals(split[0])) {
                    p.addPotionEffect(potionEffect);
                }
            }
        }
    }
}
