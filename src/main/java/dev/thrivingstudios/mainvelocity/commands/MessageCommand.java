package dev.thrivingstudios.mainvelocity.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MessageCommand implements SimpleCommand {

    private final ProxyServer server;

    public MessageCommand(ProxyServer server) {
        this.server = server;
    }

    @Override
    public void execute(final Invocation invocation) {
        CommandSource source = invocation.source();
        String senderName = (source instanceof Player) ? ((Player) source).getUsername() : "Console";
        ArrayList<String> args = new ArrayList<>(List.of(invocation.arguments()));

        if (args.size() < 2) {
            source.sendMessage(Component.text("§cUsage: /msg <player> <message>"));
            return;
        }

        String targetPlayerName = args.get(0);
        ArrayList<String> messageArgs = new ArrayList<>(args);
        messageArgs.remove(0);
        String message = String.join(" ", messageArgs);

        Optional<Player> targetPlayer = server.getPlayer(targetPlayerName);
        if (targetPlayer.isPresent()) {
            Player target = targetPlayer.get();
            target.sendMessage(Component.text(String.format("§e✉ §fFrom %s: §e%s", senderName, message)));
            source.sendMessage(Component.text(String.format("§e✉ §fTo %s: §e%s", targetPlayerName, message)));
        } else {
            source.sendMessage(Component.text(String.format("§e%s is not online.", targetPlayerName)));
        }
    }

    public boolean hasPermission(final Invocation invocation, String permission) {
        return invocation.source().hasPermission(permission);
    }
}
