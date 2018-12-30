package me.healpot.hungergames.commands;

import me.healpot.hungergames.Hungergames;
import me.healpot.hungergames.configs.TranslationConfig;
import me.healpot.hungergames.managers.PlayerManager;
import me.healpot.hungergames.types.Gamer;
import me.healpot.hungergames.types.HungergamesApi;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayersCommand implements CommandExecutor {
    public String[] aliases = new String[]{"list", "who", "gamers"};
    public String description = "See the gamers and spectators online";
    private TranslationConfig cm = HungergamesApi.getConfigManager().getTranslationsConfig();
    private Hungergames hg = HungergamesApi.getHungergames();
    private PlayerManager pm = HungergamesApi.getPlayerManager();

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        List<Gamer> gamers = pm.getAliveGamers();
        ArrayList<String> gamerNames = new ArrayList<String>();
        for (Gamer game : gamers)
            gamerNames.add(game.getPlayer().getDisplayName());
        Collections.sort(gamerNames);
        String gamersName = "No gamers";
        if (gamerNames.size() > 0)
            gamersName = StringUtils.join(gamerNames, ChatColor.GRAY + ", " + ChatColor.GRAY);
        sender.sendMessage(String.format(cm.getCommandPlayers(), gamers.size(), (pm.getGamers().size() - gamers.size()),
                gamersName));
        if (hg.currentTime >= 0)
            sender.sendMessage(String.format(cm.getCommandPlayersTimeStatusStarted(), hg.returnTime(hg.currentTime)));
        else
            sender.sendMessage(String.format(cm.getCommandPlayersTimeStatusStarting(), hg.returnTime(hg.currentTime)));
        return true;
    }
}
