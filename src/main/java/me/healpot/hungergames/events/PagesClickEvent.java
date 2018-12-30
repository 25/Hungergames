package me.healpot.hungergames.events;

import me.healpot.hungergames.types.HGPageInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class PagesClickEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    protected int slot;
    private boolean cancelled;
    private HGPageInventory inv;
    private InventoryClickEvent invEvent;

    public PagesClickEvent(HGPageInventory inventory, int slot, InventoryClickEvent invEvent) {
        this.slot = slot;
        this.invEvent = invEvent;
        this.inv = inventory;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public InventoryClickEvent getEvent() {
        return invEvent;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public HGPageInventory getInventory() {
        return inv;
    }

    public ItemStack getItemStack() {
        if (slot >= 0)
            return inv.getItem(slot);
        return null;
    }

    public Player getPlayer() {
        return inv.getPlayer();
    }

    public int getSlot() {
        return slot;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
}
