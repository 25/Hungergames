package me.healpot.hungergames.commands;

import me.healpot.hungergames.Hungergames;
import me.healpot.hungergames.configs.TranslationConfig;
import me.healpot.hungergames.types.HungergamesApi;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TimeCommand implements CommandExecutor {
    public String description = "View the current game time";
    private TranslationConfig cm = HungergamesApi.getConfigManager().getTranslationsConfig();
    private Hungergames hg = HungergamesApi.getHungergames();

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (hg.currentTime >= 0)
            sender.sendMessage(String.format(cm.getCommandTimeStatusStarted(), hg.returnTime(hg.currentTime)));
        else
            sender.sendMessage(String.format(cm.getCommandTimeStatusStarting(), hg.returnTime(hg.currentTime)));
        return true;
    }
}
