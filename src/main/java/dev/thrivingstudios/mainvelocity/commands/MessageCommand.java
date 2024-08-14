package dev.thrivingstudios.mainvelocity.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.thrivingstudios.mainvelocity.MainVelocity;
import dev.thrivingstudios.mainvelocity.util.MessageUtil;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

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

        Boolean sentMessage = MessageUtil.messageTarget(server, targetPlayerName, source, senderName, message);

        if (source instanceof Player && sentMessage) {
            MainVelocity.lastMessaged.put(((Player) source).getUniqueId(), server.getPlayer(targetPlayerName).get().getUniqueId());
        }
    }

    public boolean hasPermission(final Invocation invocation, String permission) {
        return invocation.source().hasPermission(permission);
    }

    @Override
    public CompletableFuture<List<String>> suggestAsync(final Invocation invocation) {
        if (invocation.arguments().length == 0) {
            return CompletableFuture.supplyAsync(() -> {
                List<String> suggestions = new ArrayList<>();
                for (Player player : server.getAllPlayers()) {
                    suggestions.add(player.getUsername());
                }
                return suggestions;
            });
        } else {
            return CompletableFuture.completedFuture(List.of());
        }
    }
}
