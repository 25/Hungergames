package me.healpot.hungergames.events;

import me.healpot.death.DeathCause;
import me.healpot.hungergames.managers.PlayerManager;
import me.healpot.hungergames.types.Gamer;
import me.healpot.hungergames.types.HungergamesApi;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Tameable;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PlayerKilledEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;
    private DeathCause deathCause;
    private String deathMessage;
    private Location dropItems;
    private List<ItemStack> items;
    private Gamer killed;
    private Entity killer;
    private Gamer killerGamer;

    public PlayerKilledEvent(Gamer killed, Entity killer, Gamer backupKiller, DeathCause cause, String deathMessage,
                             Location itemsDrop, List<ItemStack> itemsToDrop) {
        PlayerManager pm = HungergamesApi.getPlayerManager();
        this.deathCause = cause;
        this.killed = killed;
        this.killer = killer;
        this.deathMessage = deathMessage;
        this.dropItems = itemsDrop;
        this.items = itemsToDrop;
        if (killer instanceof Projectile && ((Projectile) killer).getShooter() instanceof Entity) {
            killerGamer = pm.getGamer((Entity) ((Projectile) killer).getShooter());
            if (killerGamer == killed)
                killerGamer = null;
        } else if (killer instanceof Tameable) {
            killerGamer = pm.getGamer(((Tameable) killer).getOwner().getName());
            if (killerGamer == killed)
                killerGamer = null;
        } else if (killer instanceof Player) {
            killerGamer = pm.getGamer(killer);
            if (killerGamer == killed)
                killerGamer = null;
        }
        if (killerGamer == null)
            killerGamer = backupKiller;

        HungergamesApi.getSpectatorManager().updateCanSeeSpectators(killed);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public DeathCause getDeathCause() {
        return deathCause;
    }

    public String getDeathMessage() {
        return deathMessage;
    }

    public void setDeathMessage(String newDeathMessage) {
        deathMessage = newDeathMessage;
    }

    public List<ItemStack> getDrops() {
        return items;
    }

    public Location getDropsLocation() {
        return dropItems;
    }

    public void setDropsLocation(Location newLocation) {
        dropItems = newLocation;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public Gamer getKilled() {
        return killed;
    }

    public Entity getKiller() {
        return killer;
    }

    public Gamer getKillerPlayer() {
        return killerGamer;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean isCancelled) {
        cancelled = isCancelled;
    }

}
