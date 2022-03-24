package me.adorerose.worms.command;

import me.adorerose.worms.command.worms.WormsCommand;
import org.bukkit.Bukkit;

public class CommandRegistry {
    public static final PluginCommand WORMS = new WormsCommand();

    public static void registerCommands() {
        PluginCommand[] commands = { WORMS };
        for (PluginCommand command: commands) Bukkit.getPluginCommand(command.getName()).setExecutor(command);
    }

    public static void unregisterCommands() {
        PluginCommand[] commands = { WORMS };
        for (PluginCommand command: commands) Bukkit.getPluginCommand(command.getName()).setExecutor(null);
    }
}
