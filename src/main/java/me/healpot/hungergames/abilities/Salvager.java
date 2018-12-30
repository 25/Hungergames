package me.healpot.hungergames.abilities;

import me.healpot.hungergames.interfaces.Disableable;
import me.healpot.hungergames.managers.EnchantmentManager;
import me.healpot.hungergames.types.AbilityListener;
import me.healpot.hungergames.types.HungergamesApi;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public class Salvager extends AbilityListener implements Disableable {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.ANVIL && hasAbility(p)) {
            ItemStack item = event.getItem();
            if (item != null && item.getType() != Material.AIR && item.getType() != Material.ANVIL
                    && item.getType() != Material.COMPASS && !item.containsEnchantment(EnchantmentManager.UNLOOTABLE)) {
                for (Recipe recipe : Bukkit.getRecipesFor(item)) {
                    if (recipe.getResult().getAmount() > item.getAmount())
                        continue;
                    if (recipe instanceof ShapelessRecipe) {
                        item.setAmount(item.getAmount() - recipe.getResult().getAmount());
                        if (item.getAmount() <= 0)
                            p.setItemInHand(new ItemStack(0));
                        event.setCancelled(true);
                        for (ItemStack items : ((ShapelessRecipe) recipe).getIngredientList())
                            if (items != null && items.getType() != Material.AIR)
                                HungergamesApi.getKitManager().addItem(p, items.clone());
                        p.updateInventory();
                        break;
                    }
                    if (recipe instanceof ShapedRecipe) {
                        item.setAmount(item.getAmount() - recipe.getResult().getAmount());
                        if (item.getAmount() <= 0)
                            p.setItemInHand(new ItemStack(0));
                        event.setCancelled(true);
                        for (ItemStack items : ((ShapedRecipe) recipe).getIngredientMap().values())
                            if (items != null && items.getType() != Material.AIR)
                                HungergamesApi.getKitManager().addItem(p, items.clone());
                        p.updateInventory();
                        break;
                    }
                }
            }
        }
    }

}
