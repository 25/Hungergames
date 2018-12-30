package me.healpot.hungergames.commands;

import me.healpot.hungergames.configs.TranslationConfig;
import me.healpot.hungergames.managers.ChatManager;
import me.healpot.hungergames.types.HungergamesApi;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReplyCommand implements CommandExecutor {

    public String[] aliases = new String[]{"r", "respond"};
    public String description = "Reply to a players private message";
    private transient ChatManager chat = HungergamesApi.getChatManager();
    private transient TranslationConfig tm = HungergamesApi.getConfigManager().getTranslationsConfig();

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(tm.getCommandReplyNoArgs());
            return true;
        }
        if (chat.hasOtherChatter(sender.getName())) {
            CommandSender otherSender = chat.getOtherChatter(sender.getName());
            chat.sendReply(sender, otherSender, StringUtils.join(args, " "));
        } else
            sender.sendMessage(tm.getCommandReplyNoReceiver());
        return true;
    }
}
