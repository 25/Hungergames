package me.healpot.hungergames.commands;

import me.healpot.hungergames.configs.TranslationConfig;
import me.healpot.hungergames.managers.ChatManager;
import me.healpot.hungergames.types.HungergamesApi;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageCommand implements CommandExecutor {

    public String[] aliases = new String[]{"msg", "mail", "tell", "pm", "whisper", "w", "privatemessage"};
    public String description = "Message a player your secret plans";
    private transient ChatManager chat = HungergamesApi.getChatManager();
    private transient TranslationConfig tm = HungergamesApi.getConfigManager().getTranslationsConfig();

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length == 0)
            sender.sendMessage(tm.getCommandMessageNoArgs());
        else if (args.length > 1) {
            Player player = Bukkit.getPlayer(args[0]);
            if (player == null) {
                sender.sendMessage(tm.getCommandMessageNoReceiver());
                return true;
            }
            chat.sendMessage(sender, player, StringUtils.join(args, " ").substring(args[0].length() + 1));
        } else
            sender.sendMessage(tm.getCommandMessagePlayerNoArgs());
        return true;
    }
}
