package me.healpot.hungergames.abilities;

import me.healpot.hungergames.events.PlayerKilledEvent;
import me.healpot.hungergames.interfaces.Disableable;
import me.healpot.hungergames.managers.EnchantmentManager;
import me.healpot.hungergames.types.AbilityListener;
import me.healpot.hungergames.types.HungergamesApi;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;

public class Gravedigger extends AbilityListener implements Disableable {
    private transient BlockFace[] faces = new BlockFace[]{BlockFace.SOUTH, BlockFace.NORTH, BlockFace.EAST, BlockFace.WEST};

    @EventHandler
    public void onKilled(PlayerKilledEvent event) {
        if (event.getKillerPlayer() != null && hasAbility(event.getKillerPlayer().getPlayer())) {
            Block center = event.getDropsLocation().getBlock();
            if (event.getDrops().size() > 54)
                for (BlockFace face : faces)
                    if (center.getRelative(face).getType() == Material.AIR) {
                        center.getRelative(face).setType(Material.CHEST);
                        break;
                    }
            center.setType(Material.CHEST);
            Inventory inv = ((InventoryHolder) center.getState()).getInventory();
            Iterator<ItemStack> itel = event.getDrops().iterator();
            while (itel.hasNext()) {
                ItemStack item = itel.next();
                if (item == null || item.getType() == Material.AIR || item.containsEnchantment(EnchantmentManager.UNLOOTABLE))
                    continue;
                if (HungergamesApi.getKitManager().canFit(inv, new ItemStack[]{item}))
                    inv.addItem(item);
                else {
                    if (item.hasItemMeta())
                        event.getDropsLocation().getWorld().dropItemNaturally(event.getDropsLocation(), item.clone())
                                .getItemStack().setItemMeta(item.getItemMeta());
                    else
                        event.getDropsLocation().getWorld().dropItemNaturally(event.getDropsLocation(), item);
                }
            }
            event.getDrops().clear();
            center.getState().update();
        }
    }
}
