package me.healpot.hungergames.commands;

import me.healpot.hungergames.configs.TranslationConfig;
import me.healpot.hungergames.listeners.LibsFeastManager;
import me.healpot.hungergames.managers.PlayerManager;
import me.healpot.hungergames.types.Gamer;
import me.healpot.hungergames.types.HungergamesApi;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GoToCommand implements CommandExecutor {
    public String[] aliases = new String[]{"watch"};
    public String description = "A command for spectators to teleport to people";
    private TranslationConfig cm = HungergamesApi.getConfigManager().getTranslationsConfig();
    private PlayerManager pm = HungergamesApi.getPlayerManager();

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Gamer gamer = pm.getGamer(sender.getName());
        if (!gamer.isAlive()) {
            if (args.length > 0) {
                if (Bukkit.getPlayer(args[0]) != null) {
                    gamer.getPlayer().eject();
                    gamer.getPlayer().teleport(sender.getServer().getPlayer(args[0]).getLocation().add(0, 0.1, 0));
                    return true;
                } else if (args[0].equalsIgnoreCase(cm.getCommandGotoNameOfFeast())) {
                    Location feastLoc = LibsFeastManager.getFeastManager().getFeastLocation();
                    if (feastLoc.getBlockY() > 0) {
                        gamer.getPlayer().eject();
                        gamer.getPlayer().teleport(
                                feastLoc.getWorld().getHighestBlockAt(feastLoc).getLocation().clone().add(0.5, 1, 0.5));
                    } else
                        sender.sendMessage(cm.getCommandGotoFeastFailed());
                    return true;
                } else
                    sender.sendMessage(cm.getCommandGotoPlayerDoesntExist());
            } else
                sender.sendMessage(cm.getCommandGotoNotEnoughArgs());
        } else
            sender.sendMessage(cm.getCommandGotoNotSpectator());
        return true;
    }
}
