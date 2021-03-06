package me.healpot.hungergames.abilities;

import me.healpot.hungergames.Sounds;
import me.healpot.hungergames.interfaces.Disableable;
import me.healpot.hungergames.types.AbilityListener;
import me.healpot.hungergames.types.HungergamesApi;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class libraryaddict extends AbilityListener implements Disableable {
    private static int i = 0;
    public String bookName = "Explosive Reading";
    public float explosionSize = 1.5F;
    public float grenadeTimer = 2;

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (event.getAction().name().contains("RIGHT") && isSpecialItem(item, bookName) && item.getType() == Material.BOOK
                && hasAbility(event.getPlayer())) {
            item.setAmount(item.getAmount() - 1);
            if (item.getAmount() == 0)
                event.getPlayer().setItemInHand(new ItemStack(0));
            final Item explodingBook = event.getPlayer().getWorld()
                    .dropItem(event.getPlayer().getEyeLocation(), new ItemStack(Material.BOOK));
            explodingBook.setVelocity(event.getPlayer().getEyeLocation().getDirection().multiply(0.6));
            explodingBook.setPickupDelay(99999);
            explodingBook.getItemStack().getItemMeta().setDisplayName("" + i++);
            for (int i = 0; i < grenadeTimer * 2; i++) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(HungergamesApi.getHungergames(), new Runnable() {
                    public void run() {
                        explodingBook.getWorld().playSound(explodingBook.getLocation(), Sounds.CLICK.bukkitSound(), 1, 10F);
                    }
                }, i * 10);
            }
            Bukkit.getScheduler().scheduleSyncDelayedTask(HungergamesApi.getHungergames(), new Runnable() {
                public void run() {
                    explodingBook.getWorld().createExplosion(explodingBook.getLocation(), explosionSize);
                    explodingBook.remove();
                }
            }, (int) (20 * grenadeTimer));
        }
    }

}
