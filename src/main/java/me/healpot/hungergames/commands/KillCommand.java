package me.healpot.hungergames.commands;


import me.healpot.hungergames.configs.TranslationConfig;
import me.healpot.hungergames.managers.PlayerManager;
import me.healpot.hungergames.types.Gamer;
import me.healpot.hungergames.types.HungergamesApi;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KillCommand implements CommandExecutor {
    public String description = "Use /suicide instead to suicide";
    private TranslationConfig cm = HungergamesApi.getConfigManager().getTranslationsConfig();
    private PlayerManager pm = HungergamesApi.getPlayerManager();

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(cm.getCommandKillUseSuicide());
        } else {
            if (!sender.hasPermission("hungergames.kill"))
                sender.sendMessage(cm.getCommandKillSomeoneNoPermission());
            else {
                Player player = Bukkit.getPlayer(args[0]);
                if (player == null)
                    sender.sendMessage(cm.getCommandKillPlayerNotFound());
                else {
                    Gamer murdered = pm.getGamer(Bukkit.getPlayer(args[0]));
                    if (murdered.isAlive())
                        pm.killPlayer(murdered, null, murdered.getPlayer().getLocation(), murdered.getInventory(),
                                String.format(cm.getCommandKillMurderMessage(), murdered.getName()));
                    else
                        sender.sendMessage(cm.getCommandKillNotAlive());
                }
            }
        }
        return true;
    }
}
