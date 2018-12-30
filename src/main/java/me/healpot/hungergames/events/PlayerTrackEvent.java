package me.healpot.hungergames.events;

import me.healpot.hungergames.types.Gamer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerTrackEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;
    private Location compassLoc;
    private String message;
    private Gamer tracker;
    private Player victim;

    public PlayerTrackEvent(Gamer tracker, Player victim, String trackMessage) {
        this.tracker = tracker;
        this.victim = victim;
        message = trackMessage;
        if (victim != null)
            compassLoc = victim.getLocation().clone();
        else
            compassLoc = tracker.getPlayer().getWorld().getSpawnLocation().clone();
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public Location getLocation() {
        return compassLoc.clone();
    }

    public void setLocation(Location loc) {
        compassLoc = loc.clone();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Gamer getTracker() {
        return tracker;
    }

    public Player getVictim() {
        return victim;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean isCancelled) {
        cancelled = isCancelled;
    }
}