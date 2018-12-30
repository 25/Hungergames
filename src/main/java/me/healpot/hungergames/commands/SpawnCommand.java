package me.healpot.hungergames.commands;


import me.healpot.hungergames.configs.TranslationConfig;
import me.healpot.hungergames.managers.PlayerManager;
import me.healpot.hungergames.types.Gamer;
import me.healpot.hungergames.types.HungergamesApi;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {
    public String[] aliases = new String[]{"hgspawn"};
    public String description = "If spectating you can teleport back to spawn";
    private TranslationConfig cm = HungergamesApi.getConfigManager().getTranslationsConfig();
    private PlayerManager pm = HungergamesApi.getPlayerManager();

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Gamer gamer = pm.getGamer(sender.getName());
        if (!gamer.isAlive()) {
            pm.sendToSpawn(gamer);
            return true;
        } else {
            ((Player) sender).setCompassTarget(HungergamesApi.getHungergames().world.getSpawnLocation());
            gamer.getPlayer().sendMessage(cm.getCommandSpawnPointingToSpawn());
        }
        return true;
    }

}
