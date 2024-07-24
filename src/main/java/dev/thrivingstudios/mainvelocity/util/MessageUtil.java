package dev.thrivingstudios.mainvelocity.util;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.thrivingstudios.mainvelocity.MainVelocity;
import net.kyori.adventure.text.Component;

import java.util.Optional;

public class MessageUtil {

    public static Boolean messageTarget(ProxyServer server, String targetPlayerName, CommandSource source, String senderName, String message) {
        Optional<Player> targetPlayer = server.getPlayer(targetPlayerName);
        if (targetPlayer.isPresent()) {
            Player target = targetPlayer.get();
            target.sendMessage(Component.text(String.format("§e✉ §fFrom %s: §e%s", senderName, message)));
            source.sendMessage(Component.text(String.format("§e✉ §fTo %s: §e%s", targetPlayerName, message)));

            if (source instanceof Player) {
                MainVelocity.lastMessaged.put(server.getPlayer(targetPlayerName).get().getUniqueId(), ((Player) source).getUniqueId());
            }

            return true;
        } else {
            source.sendMessage(Component.text(String.format("§e%s is not online.", targetPlayerName)));

            return false;
        }
    }
}
