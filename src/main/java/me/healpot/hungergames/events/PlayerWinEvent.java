package me.healpot.hungergames.events;

import me.healpot.hungergames.types.Gamer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerWinEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Gamer winner;

    public PlayerWinEvent(Gamer winner) {
        this.winner = winner;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public Gamer getWinner() {
        return winner;
    }
}