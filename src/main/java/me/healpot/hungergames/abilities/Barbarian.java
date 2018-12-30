package me.healpot.hungergames.abilities;

import me.healpot.hungergames.events.PlayerKilledEvent;
import me.healpot.hungergames.types.AbilityListener;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class Barbarian extends AbilityListener {
    public int killsPerLevel = 3;
    public String swordName = "Bloody Bane";
    public String[] weaponTypeUpgrades = new String[]{"STONE_SWORD", "IRON_SWORD", "DIAMOND_SWORD"};
    private HashMap<ItemStack, Integer> kills = new HashMap<ItemStack, Integer>();

    @EventHandler
    public void onKilled(PlayerKilledEvent event) {
        if (event.getKillerPlayer() != null) {
            ItemStack item = event.getKillerPlayer().getPlayer().getItemInHand();
            if (isSpecialItem(item, swordName) && hasAbility(event.getKillerPlayer().getPlayer())) {
                if (!kills.containsKey(item))
                    kills.put(item, 0);
                kills.put(item, kills.get(item) + 1);
                if (kills.get(item) % killsPerLevel == 0) {
                    int level = (kills.get(item) / killsPerLevel) - 1;
                    if (level < weaponTypeUpgrades.length) {
                        item.setType(Material.valueOf(weaponTypeUpgrades[level]));
                        event.getKillerPlayer().getPlayer().setItemInHand(item);
                    }
                }
            }
        }
    }
}
