package me.healpot.hungergames.utilities;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.TitleAction;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.google.common.base.Throwables;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/**
 * Represents a 1.8 title
 * <p>
 * Supports Real 1.8 and Fake 1.8
 *
 * @author Techcable
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Title {

    private String title;
    private String subtitle;
    private int fadeIn = 20;
    private int stay = 200;
    private int fadeOut = 20;

    public Title(String title, String subtitle) {
        setTitle(title);
        setSubtitle(subtitle);
    }

    /**
     * Display the players this title
     * Only shows to players who are on 1.8
     *
     * @param players players to display this title to
     */
    public void sendTo(Collection<? extends Player> players) {
        for (Player player : players) {
            if (!PacketType.Play.Server.TITLE.isSupported()) return;
            boolean shouldSend = false;
            if (title != null && !title.isEmpty()) {
                PacketContainer container = new PacketContainer(PacketType.Play.Server.TITLE);
                container.getTitleActions().write(0, TitleAction.TITLE);
                container.getChatComponents().write(0, WrappedChatComponent.fromText(title));
                try {
                    ProtocolLibrary.getProtocolManager().sendServerPacket(player, container);
                } catch (InvocationTargetException ex) {
                    throw Throwables.propagate(ex);
                }
                shouldSend = true;
            }
            if (subtitle != null && !subtitle.isEmpty()) {
                PacketContainer container = new PacketContainer(PacketType.Play.Server.TITLE);
                container.getTitleActions().write(0, TitleAction.SUBTITLE);
                container.getChatComponents().write(0, WrappedChatComponent.fromText(title));
                try {
                    ProtocolLibrary.getProtocolManager().sendServerPacket(player, container);
                } catch (InvocationTargetException ex) {
                    throw Throwables.propagate(ex);
                }
                shouldSend = true;
            }
            if (shouldSend) {
                PacketContainer container = new PacketContainer(PacketType.Play.Server.TITLE);
                container.getTitleActions().write(0, TitleAction.TIMES);
                container.getIntegers().write(0, fadeIn);
                container.getIntegers().write(1, stay);
                container.getIntegers().write(2, fadeOut);
                try {
                    ProtocolLibrary.getProtocolManager().sendServerPacket(player, container);
                } catch (InvocationTargetException ex) {
                    throw Throwables.propagate(ex);
                }
            }
        }
    }

    public void clear(Player p) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.TITLE);
        packet.getTitleActions().write(0, TitleAction.RESET);
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(p, packet);
        } catch (InvocationTargetException ex) {
            throw Throwables.propagate(ex);
        }
    }
}