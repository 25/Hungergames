package me.healpot.hungergames.interfaces;

import me.healpot.hungergames.types.RandomItem;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public interface ChestManager {
    /**
     * Fills the chest with items
     *
     * @param inv
     */
    public void fillChest(Inventory inv);

    /**
     * Clears the random items to stick in
     */
    public void clearRandomItems();

    /**
     * Adds a random item to put in the feast
     */
    public void addRandomItem(RandomItem item);

    /**
     * Gets the random items
     *
     * @return
     */
    public ArrayList<RandomItem> getRandomItems();

    /**
     * Sets the random items in the feast generator
     */
    public void setRandomItems(ArrayList<RandomItem> items);
}
