package me.healpot.death;

import org.bukkit.entity.Player;

public abstract class DeathListener {
    private static DeathHandlerListener deathListener;

    public boolean canRemove(Player p, Damage damage) {
        return true;
    }

    public abstract void checkDamages(Player p);

    public abstract void onDamage(Player p, Damage damage);

    public DeathHandlerListener getListener() {
        return deathListener;
    }

    public static void setListener(DeathHandlerListener listener) {
        deathListener = listener;
    }
}
