package me.healpot.hungergames.commands;


import me.healpot.hungergames.configs.TranslationConfig;
import me.healpot.hungergames.managers.PlayerManager;
import me.healpot.hungergames.types.Gamer;
import me.healpot.hungergames.types.HungergamesApi;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChunkCommand implements CommandExecutor {
    public String[] aliases = new String[]{"stuck", "refresh"};
    public String description = "This command refreshes the chunk of the command user";
    private TranslationConfig cm = HungergamesApi.getConfigManager().getTranslationsConfig();
    private PlayerManager pm = HungergamesApi.getPlayerManager();

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Gamer gamer = pm.getGamer(sender.getName());
        if (gamer.getChunkCooldown() < System.currentTimeMillis() / 1000L) {
            Player p = gamer.getPlayer();
            gamer.setChunkCooldown((System.currentTimeMillis() / 1000) + 10);
            System.out.print(String.format(cm.getCommandChunkLoggerReloadingChunks(), p.getName()));
            org.bukkit.Chunk chunk = p.getWorld().getChunkAt(p.getLocation());
            for (int i = -16; i <= 16; i = i + 16) {
                for (int a = -16; a <= 16; a = a + 16) {
                    chunk = p.getWorld().getChunkAt((p.getLocation().getBlockX() + i) >> 4,
                            (p.getLocation().getBlockZ() + a) >> 4);
                    HungergamesApi.getReflectionManager().sendChunk(p, chunk.getX(), chunk.getZ());
                }
            }
            p.teleport(p.getLocation().add(0, 0.5, 0));
            sender.sendMessage(cm.getCommandChunkReloadedChunks());
        } else
            sender.sendMessage(cm.getCommandChunkCooldown());
        return true;
    }
}
