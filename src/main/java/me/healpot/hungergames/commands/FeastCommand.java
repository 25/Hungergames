package me.healpot.hungergames.commands;

import me.healpot.hungergames.configs.TranslationConfig;
import me.healpot.hungergames.listeners.LibsFeastManager;
import me.healpot.hungergames.types.HungergamesApi;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FeastCommand implements CommandExecutor {
    public String description = "Point your compass towards the feast";
    private TranslationConfig cm = HungergamesApi.getConfigManager().getTranslationsConfig();

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (LibsFeastManager.getFeastManager().getFeastLocation().getY() > 0) {
            ((Player) sender).setCompassTarget(LibsFeastManager.getFeastManager().getFeastLocation());
            sender.sendMessage(cm.getCommandFeastHappened());
        } else {
            sender.sendMessage(cm.getCommandFeastNotHappened());
        }
        return true;
    }
}
