package me.adorerose.worms;

import me.adorerose.worms.command.CommandRegistry;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class WormsPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        CommandRegistry.registerCommands();
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
        CommandRegistry.unregisterCommands();
    }
}
