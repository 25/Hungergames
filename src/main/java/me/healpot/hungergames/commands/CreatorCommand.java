package me.healpot.hungergames.commands;

import me.healpot.hungergames.configs.TranslationConfig;
import me.healpot.hungergames.types.HungergamesApi;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CreatorCommand implements CommandExecutor {
    public String[] aliases = new String[]{"download"};
    public String description = "View the author of this great plugin";
    private TranslationConfig cm = HungergamesApi.getConfigManager().getTranslationsConfig();

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        String msg = cm.getCommandCreator().replace("%s", "libraryaddict");
        if (!msg.toLowerCase().contains("libraryaddict")) {
            msg = ChatColor.GOLD + "All worship king libraryaddict!";
        }
        sender.sendMessage(msg);
        return true;
    }
}
