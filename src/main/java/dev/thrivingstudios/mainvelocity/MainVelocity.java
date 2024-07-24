package dev.thrivingstudios.mainvelocity;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.thrivingstudios.mainvelocity.commands.MessageCommand;
import dev.thrivingstudios.mainvelocity.commands.ReplyCommand;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@Plugin(
        id = "mainvelocity",
        name = "MainVelocity",
        version = "1.0-SNAPSHOT",
        authors = "Thriving Studios",
        description = "Main velocity plugin."
)
public class MainVelocity {
    private final ProxyServer server;
    private final Logger logger;

    public static HashMap<UUID, UUID> lastMessaged = new HashMap<UUID, UUID>();

    @Inject
    public MainVelocity(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        this.server = server;
        this.logger = logger;
    }

    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) {
        server.getCommandManager().register("msg", new MessageCommand(server), "message", "tell", "whisper");
        server.getCommandManager().register("reply", new ReplyCommand(server), "r");
    }
}
