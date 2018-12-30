package me.healpot.hungergames.nms;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface FakeFurnace {
    void tick();

    void showTo(Player player);

    List<ItemStack> getItems();
}
