package me.healpot.hungergames.abilities;

import me.healpot.hungergames.types.AbilityListener;
import me.healpot.hungergames.types.HungergamesApi;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class Trapper extends AbilityListener {
    public boolean immuneToOwnTraps = false;
    public int trapperItem = Material.STRING.getId();
    public String trapperItemName = "Trapper's String";

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Block b = event.getTo().getBlock();
        if (b.getType() == Material.TRIPWIRE && b.hasMetadata("Trapper")
                && HungergamesApi.getPlayerManager().getGamer(event.getPlayer()).isAlive()) {
            if (immuneToOwnTraps && b.getMetadata("Trapper").get(0).asString().equals(event.getPlayer().getName()))
                return;
            b.setType(Material.WEB);
            b.removeMetadata("Trapper", HungergamesApi.getHungergames());
            b.getWorld().playSound(b.getLocation().clone(), Sound.UI_BUTTON_CLICK, 1, 10);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        if (isSpecialItem(item, trapperItemName) && item.getTypeId() == trapperItem) {
            event.getBlock().setMetadata("Trapper",
                    new FixedMetadataValue(HungergamesApi.getHungergames(), event.getPlayer().getName()));
        }
    }

}
