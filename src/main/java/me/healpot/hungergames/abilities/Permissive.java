package me.healpot.hungergames.abilities;

import me.healpot.hungergames.events.GameStartEvent;
import me.healpot.hungergames.events.PlayerKilledEvent;
import me.healpot.hungergames.interfaces.Disableable;
import me.healpot.hungergames.types.AbilityListener;
import me.healpot.hungergames.types.HungergamesApi;
import me.healpot.hungergames.types.Kit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;

public class Permissive extends AbilityListener implements Disableable {
    public String[] kitPermissions = new String[]{"KitName Permission Permission Permission", "NoCheatBypass nocheat.bypass"};
    private HashMap<Player, PermissionAttachment> attachments = new HashMap<Player, PermissionAttachment>();

    @EventHandler
    public void onGameStart(GameStartEvent event) {
        for (Player player : getMyPlayers()) {
            Kit hisKit = HungergamesApi.getKitManager().getKitByPlayer(player);
            PermissionAttachment attachment = null;
            for (String string : kitPermissions) {
                String[] strings = string.split(" ");
                if (strings[0].equalsIgnoreCase(hisKit.getName())) {
                    if (attachment == null)
                        attachment = player.addAttachment(HungergamesApi.getHungergames());
                    for (int i = 1; i < strings.length; i++)
                        attachment.setPermission(strings[i], true);
                }
            }
            if (attachment != null)
                attachments.put(player, attachment);
        }
    }

    @EventHandler
    public void onKilled(PlayerKilledEvent event) {
        if (hasAbility(event.getKilled().getPlayer())) {
            event.getKilled().getPlayer().removeAttachment(attachments.remove(event.getKilled().getPlayer()));
        }
    }
}
