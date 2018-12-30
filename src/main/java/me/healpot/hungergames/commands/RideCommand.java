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

public class RideCommand implements CommandExecutor {
    public String description = "Toggle the ability to ride on top of mobs and players";
    private TranslationConfig cm = HungergamesApi.getConfigManager().getTranslationsConfig();
    private PlayerManager pm = HungergamesApi.getPlayerManager();

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Gamer gamer = pm.getGamer(sender.getName());
        if (args.length == 1 && args[0].equalsIgnoreCase(cm.getCommandRideNameOfRideall()) && sender.isOp()) {
            Player p = Bukkit.getPlayerExact(sender.getName());
            Player last = p;
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player != p) {
                    player.leaveVehicle();
                    last.setPassenger(player);
                    last = player;
                }
            }
            Bukkit.broadcastMessage(cm.getCommandRideRideAll());
        } else {
            gamer.setRiding(!gamer.canRide());
            sender.sendMessage(String.format(cm.getCommandRideToggle(), gamer.canRide()));
        }
        return true;
    }
}
