package me.healpot.hungergames.commands;

import me.healpot.hungergames.Hungergames;
import me.healpot.hungergames.configs.FeastConfig;
import me.healpot.hungergames.configs.TranslationConfig;
import me.healpot.hungergames.listeners.LibsFeastManager;
import me.healpot.hungergames.managers.GenerationManager;
import me.healpot.hungergames.types.HungergamesApi;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ForceFeastCommand implements CommandExecutor {
    public String[] aliases = new String[]{"ffeast"};
    public String description = "Force a feast to generate at your feet";
    private TranslationConfig cm = HungergamesApi.getConfigManager().getTranslationsConfig();
    private Hungergames hg = HungergamesApi.getHungergames();

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("hungergames.forcefeast")) {
            FeastConfig feast = HungergamesApi.getConfigManager().getFeastConfig();
            int radius = feast.getFeastSize();
            int chestLayers = feast.getChestLayersHeight();
            if (args.length > 0) {
                if (hg.isNumeric(args[0])) {
                    radius = Integer.parseInt(args[0]);
                    if (radius <= 0) {
                        sender.sendMessage(cm.getCommandForceFeastStupidInput());
                        return true;
                    }
                } else {
                    sender.sendMessage(String.format(cm.getCommandForceFeastNotANumber(), args[0]));
                    return true;
                }
                if (args.length > 1) {
                    if (hg.isNumeric(args[1])) {
                        chestLayers = Integer.parseInt(args[1]);
                        if (radius <= 0) {
                            sender.sendMessage(cm.getCommandForceFeastStupidInput());
                            return true;
                        }
                    } else {
                        sender.sendMessage(String.format(cm.getCommandForceFeastNotANumber(), args[1]));
                        return true;
                    }
                }
            }
            GenerationManager gen = HungergamesApi.getGenerationManager();
            if (!(sender instanceof Player)) {
                sender.sendMessage("You must be a player to run this command");
            }
            Location loc = ((Player) sender).getLocation().clone();
            int height = gen.getSpawnHeight(loc, radius);
            loc.setY(height);
            LibsFeastManager.getFeastManager().generatePlatform(loc, height, radius);
            LibsFeastManager.getFeastManager().generateChests(loc, chestLayers);
            Bukkit.broadcastMessage(String.format(cm.getCommandForceFeastGenerated(), loc.getBlockX(), loc.getBlockY(),
                    loc.getBlockZ()));
        } else
            sender.sendMessage(cm.getCommandForceFeastNoPermission());
        return true;
    }
}
