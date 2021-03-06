package me.healpot.hungergames.abilities;

import me.healpot.hungergames.Hungergames;
import me.healpot.hungergames.events.PlayerKilledEvent;
import me.healpot.hungergames.events.TimeSecondEvent;
import me.healpot.hungergames.interfaces.Disableable;
import me.healpot.hungergames.types.AbilityListener;
import me.healpot.hungergames.types.HungergamesApi;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.HashMap;
import java.util.Iterator;

public class Phantom extends AbilityListener implements Disableable {
    public boolean addNoCheatPlusBypass = true;
    public String cooldownMessage = ChatColor.RED + "You cannot use that just yet! %s seconds of cooldown remaining!";
    public int cooldownTime = 60;
    public boolean doubleFallDamage = true;
    public String flightLeftMessage = ChatColor.RED + "You have %s of flight left!";
    public String flightWoreOff = ChatColor.RED + "Your flight disappeared!";
    public boolean giveFlightArmor = true;
    public int phantomFeatherId = Material.FEATHER.getId();
    public String phantomFeatherName = "Condor's Feather";
    public int secondsOfFlight = 5;
    private HashMap<ItemStack, Integer> cooldown = new HashMap<ItemStack, Integer>();
    private HashMap<Player, Integer> flightLeft = new HashMap<Player, Integer>();
    private Hungergames hg = HungergamesApi.getHungergames();
    private HashMap<Player, ItemStack[]> playerArmor = new HashMap<Player, ItemStack[]>();

    private ItemStack colorIn(Material mat) {
        ItemStack armor = new ItemStack(mat);
        LeatherArmorMeta meta = (LeatherArmorMeta) armor.getItemMeta();
        meta.setColor(Color.WHITE);
        armor.setItemMeta(meta);
        return armor;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (playerArmor.containsKey(event.getWhoClicked())) {
            if (event.getCurrentItem().getType().name().contains("LEATHER_")
                    && ((LeatherArmorMeta) event.getCurrentItem().getItemMeta()).getColor().equals(Color.WHITE))
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction().name().contains("RIGHT") && isSpecialItem(event.getItem(), phantomFeatherName)
                && event.getItem().getTypeId() == phantomFeatherId && hasAbility(event.getPlayer())) {
            Player p = event.getPlayer();
            if (cooldown.containsKey(event.getItem()) && cooldown.get(event.getItem()) > hg.currentTime) {
                p.sendMessage(String.format(cooldownMessage, hg.returnTime(cooldown.get(event.getItem()) - hg.currentTime)));
                return;
            }
            cooldown.put(event.getItem(), hg.currentTime + cooldownTime);
            flightLeft.put(p, secondsOfFlight + 1);
            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 2, 1);
            p.setAllowFlight(true);
            p.setFlying(true);
            if (giveFlightArmor) {
                PlayerInventory inv = p.getInventory();
                playerArmor.put(p, inv.getArmorContents());
                inv.setHelmet(colorIn(Material.LEATHER_HELMET));
                inv.setChestplate(colorIn(Material.LEATHER_CHESTPLATE));
                inv.setLeggings(colorIn(Material.LEATHER_LEGGINGS));
                inv.setBoots(colorIn(Material.LEATHER_BOOTS));
                p.updateInventory();
            }
        }
    }

    @EventHandler
    public void onKilled(PlayerKilledEvent event) {
        flightLeft.remove(event.getKilled().getPlayer());
        if (playerArmor.containsKey(event.getKilled().getPlayer())) {
            Iterator<ItemStack> itel = event.getDrops().iterator();
            while (itel.hasNext()) {
                ItemStack item = itel.next();
                if (item.getType().name().contains("LEATHER_")
                        && ((LeatherArmorMeta) item.getItemMeta()).getColor().equals(Color.WHITE))
                    itel.remove();
            }
            for (ItemStack item : playerArmor.remove(event.getKilled().getPlayer()))
                event.getDrops().add(item);
        }
    }

    @EventHandler
    public void onSecond(TimeSecondEvent event) {
        Iterator<Player> itel = flightLeft.keySet().iterator();
        while (itel.hasNext()) {
            Player p = itel.next();
            flightLeft.put(p, flightLeft.get(p) - 1);
            if (flightLeft.get(p) <= 0) {
                itel.remove();
                p.setAllowFlight(false);
                p.setFallDistance(0);
                if (giveFlightArmor)
                    p.getInventory().setArmorContents(playerArmor.remove(p));
                p.getWorld().playSound(p.getLocation(), Sound.WEATHER_RAIN, 3, 4);
                p.sendMessage(flightWoreOff);
                if (this.addNoCheatPlusBypass)
                    p.addAttachment(HungergamesApi.getHungergames(), "nocheatplus.checks.moving.nofall", true, 100);
            } else
                p.sendMessage(String.format(flightLeftMessage, flightLeft.get(p)));
        }
    }

}
