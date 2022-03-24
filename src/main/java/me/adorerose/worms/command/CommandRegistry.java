package me.adorerose.worms.command;

import me.adorerose.worms.command.worms.WormsCommand;
import org.bukkit.Bukkit;

public class CommandRegistry {
    public static final PluginCommand WORMS = new WormsCommand();
    private static PluginCommand[] commands = { WORMS };

    public static void registerCommands() {
        for (PluginCommand command: commands) Bukkit.getPluginCommand(command.getName()).setExecutor(command);
    }

    public static void unregisterCommands() {
        for (PluginCommand command: commands) Bukkit.getPluginCommand(command.getName()).setExecutor(null);
    }
}
