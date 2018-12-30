package me.healpot.hungergames;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class NetEase {
    public static void kickPlayer(Plugin p, Player player, String str) {

        try {
            p.getServer().getMessenger().registerOutgoingPluginChannel(p, "BungeeCord");
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF("lobby_hg_01");
            player.sendPluginMessage(p, "BungeeCord", out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            sendMessage(str, player);
        } catch (Exception e) {

        }
    }

    public static void sendMessage(String msg, Player player) {
        String message = ChatColor.translateAlternateColorCodes('&', msg);
        player.sendMessage(message);
    }
}