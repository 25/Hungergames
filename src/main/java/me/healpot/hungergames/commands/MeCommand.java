package me.healpot.hungergames.commands;

import me.healpot.hungergames.configs.TranslationConfig;
import me.healpot.hungergames.managers.ConfigManager;
import me.healpot.hungergames.managers.PlayerManager;
import me.healpot.hungergames.types.HungergamesApi;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MeCommand implements CommandExecutor {
    public String description = "Act out a message";
    private ConfigManager config = HungergamesApi.getConfigManager();
    private PlayerManager pm = HungergamesApi.getPlayerManager();
    private TranslationConfig tm = HungergamesApi.getConfigManager().getTranslationsConfig();

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (config.getMainConfig().isSpectatorsChatHidden() && pm.getGamer((Player) sender).isSpectator()) {
            sender.sendMessage(tm.getCommandMeSpectating());
            return true;
        }
        if (args.length == 0) {
            return false;
        } else {
            Bukkit.broadcastMessage("* " + ((Player) sender).getDisplayName() + ChatColor.RESET + " " + StringUtils.join(args, " "));
            return true;
        }
    }
}
