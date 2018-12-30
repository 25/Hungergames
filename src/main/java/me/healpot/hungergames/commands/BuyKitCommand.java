package me.healpot.hungergames.commands;

import me.healpot.hungergames.configs.TranslationConfig;
import me.healpot.hungergames.managers.KitManager;
import me.healpot.hungergames.managers.PlayerManager;
import me.healpot.hungergames.types.Gamer;
import me.healpot.hungergames.types.GiveKitThread;
import me.healpot.hungergames.types.HungergamesApi;
import me.healpot.hungergames.types.Kit;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BuyKitCommand implements CommandExecutor {
    public String description = "When mysql is enabled you can use this command to buy kits";
    private TranslationConfig cm = HungergamesApi.getConfigManager().getTranslationsConfig();
    private KitManager kits = HungergamesApi.getKitManager();
    private PlayerManager pm = HungergamesApi.getPlayerManager();

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Gamer gamer = pm.getGamer(sender.getName());
        if (args.length > 0) {
            Kit kit = kits.getKitByName(StringUtils.join(args, " "));
            if (kit != null) {
                if (gamer.getBalance() < kit.getPrice()) {
                    sender.sendMessage(cm.getCommandBuyKitCantAfford());
                    return true;
                }
                if (kit.getPrice() <= 0 || kit.isFree()) {
                    sender.sendMessage(cm.getCommandBuyKitCantBuyKit());
                    return true;
                }
                if (kits.ownsKit(gamer.getPlayer(), kit)) {
                    sender.sendMessage(cm.getCommandBuyKitAlreadyOwn());
                    return true;
                }
                if (!HungergamesApi.getConfigManager().getMySqlConfig().isMysqlKitsEnabled()) {
                    sender.sendMessage(cm.getCommandBuyKitMysqlNotEnabled());
                    return true;
                }
                if (!kits.addKitToPlayer(gamer.getPlayer(), kit)) {
                    sender.sendMessage(cm.getCommandBuyKitKitsNotLoaded());
                } else {
                    new GiveKitThread(gamer.getName(), gamer.getPlayer().getUniqueId().toString(), kit.getName()).start();
                    gamer.addBalance(-kit.getPrice());
                    sender.sendMessage(String.format(cm.getCommandBuyKitPurchasedKit(), kit.getName()));
                }
                return true;
            }
        }
        sender.sendMessage(cm.getCommandBuyKitNoArgs());
        return true;
    }
}
