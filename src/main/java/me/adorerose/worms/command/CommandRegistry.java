package me.adorerose.worms.command;

import me.adorerose.worms.command.plugin.ReloadCommand;
import me.adorerose.worms.command.worms.MapCommand;
import me.adorerose.worms.command.worms.WormsCommand;
import org.bukkit.Bukkit;

public class CommandRegistry {
    public static final PluginCommand
            WORMS = new WormsCommand(),
            RELOAD = new ReloadCommand(),
            MAP = new MapCommand();
    private static PluginCommand[] commands = { WORMS, RELOAD, MAP };

    public static void registerCommands() {
        for (PluginCommand command: commands) Bukkit.getPluginCommand(command.getName()).setExecutor(command);
    }

    public static void unregisterCommands() {
        for (PluginCommand command: commands) Bukkit.getPluginCommand(command.getName()).setExecutor(null);
    }
}
