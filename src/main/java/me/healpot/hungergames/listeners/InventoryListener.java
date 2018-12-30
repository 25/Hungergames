package me.healpot.hungergames.listeners;

import me.healpot.hungergames.configs.TranslationConfig;
import me.healpot.hungergames.events.PagesClickEvent;
import me.healpot.hungergames.managers.KitManager;
import me.healpot.hungergames.managers.PlayerManager;
import me.healpot.hungergames.types.Gamer;
import me.healpot.hungergames.types.HGPageInventory.InventoryType;
import me.healpot.hungergames.types.HungergamesApi;
import me.healpot.hungergames.types.Kit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {
    private KitManager kits = HungergamesApi.getKitManager();
    private PlayerManager pm = HungergamesApi.getPlayerManager();
    private TranslationConfig tm = HungergamesApi.getConfigManager().getTranslationsConfig();

    @EventHandler
    public void onClick(PagesClickEvent event) {
        ItemStack item = event.getItemStack();
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
            Player p = event.getPlayer();
            InventoryType type = event.getInventory().getType();
            if (type == InventoryType.KIT) {
                String name = item.getItemMeta().getDisplayName();
                Kit kit = null;
                for (Kit k : kits.getKits()) {
                    String kitName = ChatColor.WHITE + k.getName()
                            + (kits.ownsKit(p, k) ? tm.getInventoryOwnKit() : tm.getInventoryDontOwnKit());
                    if (kitName.equals(name)) {
                        kit = k;
                        break;
                    }
                }
                if (kit != null) {
                    Bukkit.dispatchCommand(p, "kit " + kit.getName());
                }
            } else if (type == InventoryType.SPECTATOR) {
                if (item.getItemMeta().getDisplayName().equals(tm.getSpectatorInventoryFeastName())) {
                    p.teleport(LibsFeastManager.getFeastManager().getFeastLocation().getWorld()
                            .getHighestBlockAt(LibsFeastManager.getFeastManager().getFeastLocation()).getLocation().clone()
                            .add(0.5, 1, 0.5));
                } else {
                    Gamer toTeleport = pm.getGamer(ChatColor.stripColor(item.getItemMeta().getDisplayName()));
                    if (toTeleport != null) {
                        p.teleport(toTeleport.getPlayer());
                    }
                }
            } else if (type == InventoryType.BUYKIT) {
                Kit kit = kits.getKitByName(ChatColor.stripColor(item.getItemMeta().getDisplayName()));
                if (kit != null) {
                    Bukkit.dispatchCommand(p, "buykit " + kit.getName());
                    HungergamesApi.getInventoryManager().openBuyKitInventory(p);
                }
            }
        }
    }
}
