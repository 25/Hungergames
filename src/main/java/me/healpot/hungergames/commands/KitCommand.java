package me.healpot.hungergames.commands;

import me.healpot.hungergames.Hungergames;
import me.healpot.hungergames.configs.TranslationConfig;
import me.healpot.hungergames.managers.KitManager;
import me.healpot.hungergames.types.HungergamesApi;
import me.healpot.hungergames.types.Kit;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KitCommand implements CommandExecutor {
    public String[] aliases = new String[]{"kits"};
    public String description = "Use this to select your kit or display them";
    private TranslationConfig cm = HungergamesApi.getConfigManager().getTranslationsConfig();
    private Hungergames hg = HungergamesApi.getHungergames();
    private KitManager kits = HungergamesApi.getKitManager();

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player p = Bukkit.getPlayerExact(sender.getName());
        if (args.length > 0) {
            if (hg.currentTime < 0) {
                String kitName = StringUtils.join(args, " ");
                Kit kit = kits.getKitByName(kitName);
                if (kit == null) {
                    sender.sendMessage(cm.getCommandKitKitDoesntExist());
                    return true;
                }
                if (!kits.ownsKit((Player) sender, kit)) {
                    sender.sendMessage(cm.getCommandKitNoPermission());
                    return true;
                }
                if (kit == kits.getKitByPlayer(p)) {
                    sender.sendMessage(String.format(cm.getCommandKitAlreadyUsing(), kit.getName()));
                    return true;
                }
                kits.setKit(p, kit.getName());
                sender.sendMessage(String.format(cm.getCommandKitNowUsingKit(), kit.getName()));
            } else {
                sender.sendMessage(cm.getCommandKitGameAlreadyStarted());
            }
        } else {
            List<String> hisKits = new ArrayList<String>();
            List<String> otherKits = new ArrayList<String>();
            String currentKit = cm.getMessagePlayerShowKitsNoKit();
            if (kits.getKitByPlayer(p) != null)
                currentKit = kits.getKitByPlayer(p).getName();
            for (Kit kit : kits.getKits())
                if (kits.ownsKit(p, kit))
                    hisKits.add(kit.getName());
                else
                    otherKits.add(kit.getName());
            Collections.sort(hisKits, String.CASE_INSENSITIVE_ORDER);
            Collections.sort(otherKits, String.CASE_INSENSITIVE_ORDER);
            if (kits.getKitByPlayer(p) != null)
                sender.sendMessage(String.format(cm.getMessagePlayerShowKitsCurrentSelectedKit(), currentKit));
            if (hisKits.size() == 0)
                sender.sendMessage(String.format(cm.getMessagePlayerShowKitsHisKits(), cm.getMessagePlayerShowKitsNoKits()));
            else {
                String list = StringUtils.join(hisKits, cm.getCommandKitKitsDivider());
                sender.sendMessage(String.format(cm.getMessagePlayerShowKitsHisKits(), list));
            }
            if (otherKits.size() == 0)
                sender.sendMessage(String.format(cm.getMessagePlayerShowKitsOtherKits(), cm.getMessagePlayerShowKitsNoKits()));
            else {
                String list = StringUtils.join(otherKits, cm.getCommandKitKitsDivider());
                sender.sendMessage(String.format(cm.getMessagePlayerShowKitsOtherKits(), list));
            }
            sender.sendMessage(cm.getMessagePlayerShowKitsUseKitInfo());
        }
        return true;
    }
}
