package me.healpot.hungergames.commands;

import me.healpot.hungergames.configs.TranslationConfig;
import me.healpot.hungergames.managers.KitManager;
import me.healpot.hungergames.managers.PlayerManager;
import me.healpot.hungergames.managers.SpectatorManager;
import me.healpot.hungergames.types.Gamer;
import me.healpot.hungergames.types.HungergamesApi;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RespawnCommand implements CommandExecutor {
    public String description = "Respawn a player after they have died";
    private TranslationConfig cm = HungergamesApi.getConfigManager().getTranslationsConfig();
    private PlayerManager pm = HungergamesApi.getPlayerManager();

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("hungergames.respawn")) {
            Gamer gamer = pm.getGamer(sender.getName());
            if (args.length > 0) {
                Player player = sender.getServer().getPlayer(args[0]);
                if (player == null) {
                    sender.sendMessage(cm.getCommandRespawnPlayerDoesntExist());
                    return true;
                }
                gamer = pm.getGamer(player);
            }
            if (HungergamesApi.getHungergames().currentTime >= 0 && HungergamesApi.getHungergames().doSeconds) {
                if (!gamer.isAlive()) {
                    gamer.clearInventory();
                    SpectatorManager.getInstance().deactiveSpectating(gamer);
                    KitManager kits = HungergamesApi.getKitManager();
                    Player p = gamer.getPlayer();
                    p.setNoDamageTicks(200);
                    p.getInventory().addItem(new ItemStack(Material.COMPASS));
                    kits.setKit(p, kits.getKitByPlayer(p).getName());
                    kits.getKitByPlayer(p).giveKit(p);
                    p.sendMessage(cm.getCommandRespawnYouHaveBeenRespawned());
                    if (p != sender)
                        sender.sendMessage(String.format(cm.getCommandRespawnRespawnedPlayer(), gamer.getName()));
                } else
                    sender.sendMessage(cm.getCommandRespawnPlayerIsAlive());
            } else
                sender.sendMessage(cm.getCommandRespawnGameHasntStarted());
        } else
            sender.sendMessage(cm.getCommandRespawnNoPermission());
        return true;
    }
}