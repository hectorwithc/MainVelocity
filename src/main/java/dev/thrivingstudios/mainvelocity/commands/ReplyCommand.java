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
import java.util.UUID;

public class ReplyCommand implements SimpleCommand {

    private final ProxyServer server;


    public ReplyCommand(ProxyServer server) {
        this.server = server;
    }

    @Override
    public void execute(final Invocation invocation) {
        CommandSource source = invocation.source();

        if (!(source instanceof Player)) {
            source.sendMessage(Component.text("§cThis command can only be run in-game."));
            return;
        }

        String senderName = ((Player) source).getUsername();
        ArrayList<String> args = new ArrayList<>(List.of(invocation.arguments()));

        if (args.isEmpty()) {
            source.sendMessage(Component.text("§cUsage: /reply <message>"));
            return;
        }

        if (!(MainVelocity.lastMessaged.containsKey(((Player) source).getUniqueId()))) {
            source.sendMessage(Component.text("§cYou haven't messaged anyone recently."));

            return;
        }

        UUID targetPlayerUUID = MainVelocity.lastMessaged.get(((Player) source).getUniqueId());
        String targetPlayerName = server.getPlayer(targetPlayerUUID).get().getUsername();

        ArrayList<String> messageArgs = new ArrayList<>(args);
        String message = String.join(" ", messageArgs);

        MessageUtil.messageTarget(server, targetPlayerName, source, senderName, message);
    }

    public boolean hasPermission(final Invocation invocation, String permission) {
        return invocation.source().hasPermission(permission);
    }
}
