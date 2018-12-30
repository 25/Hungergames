package me.healpot.hungergames.abilities;

import me.healpot.hungergames.events.PlayerKilledEvent;
import me.healpot.hungergames.interfaces.Disableable;
import me.healpot.hungergames.types.AbilityListener;
import me.healpot.hungergames.types.HungergamesApi;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSprintEvent;

import java.util.HashMap;

public class Pussy extends AbilityListener implements Disableable {
    public int timeToTransform = 5;
    public String transformedFromCat = ChatColor.BLUE + "HISS!";
    public String transformedIntoCat = ChatColor.BLUE + "Meow!";
    private transient HashMap<Player, Integer> pussys = new HashMap<Player, Integer>();

    public Pussy() throws Exception {
        if (Bukkit.getPluginManager().getPlugin("LibsDisguises") == null)
            throw new Exception(String.format(HungergamesApi.getConfigManager().getLoggerConfig().getDependencyNotFound(),
                    "Plugin LibsDisguises"));
        if (Bukkit.getPluginManager().getPlugin("ProtocolLib") == null)
            throw new Exception(String.format(HungergamesApi.getConfigManager().getLoggerConfig().getDependencyNotFound(),
                    "Plugin ProtocolLib"));
    }

    @EventHandler
    public void onKilled(PlayerKilledEvent event) {
        Player p = event.getKilled().getPlayer();
        if (pussys.containsKey(p)) {
            Bukkit.getScheduler().cancelTask(pussys.remove(p));
        }
    }

    @EventHandler
    public void onSprint(PlayerToggleSprintEvent event) {
        final Player p = event.getPlayer();
        if (hasAbility(p)) {
            if (event.isSprinting()) {
                int id = Bukkit.getScheduler().scheduleSyncDelayedTask(HungergamesApi.getHungergames(), new Runnable() {
                    public void run() {
                        p.sendMessage(transformedIntoCat);
                        DisguiseAPI.disguiseToAll(p, new MobDisguise(DisguiseType.OCELOT, true));
                    }
                }, timeToTransform * 20);
                pussys.put(p, id);
            } else if (pussys.containsKey(p)) {
                Bukkit.getScheduler().cancelTask(pussys.remove(p));
                if (DisguiseAPI.isDisguised(p)) {
                    p.sendMessage(transformedFromCat);
                    DisguiseAPI.undisguiseToAll(p);
                }
            }
        }
    }

}
