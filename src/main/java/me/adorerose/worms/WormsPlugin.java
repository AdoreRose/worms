package me.adorerose.worms;

import me.adorerose.worms.command.CommandRegistry;
import me.adorerose.worms.event.GeneralListener;
import me.adorerose.worms.storage.DataStorage;
import me.adorerose.worms.storage.file.Configuration;
import me.adorerose.worms.storage.file.Language;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class WormsPlugin extends JavaPlugin {
    private static WormsPlugin instance;
    private Configuration config;
    private Language language;

    public static WormsPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        try {
            config = DataStorage.getStorage(Configuration.class);
            language = DataStorage.getStorage(Language.class);
            config.load();
            language.load();

            CommandRegistry.registerCommands();
            Bukkit.getPluginManager().registerEvents(new GeneralListener(this), this);
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
        Bukkit.getScheduler().cancelTasks(this);
        CommandRegistry.unregisterCommands();
    }

    public Configuration getConfiguration() {
        return config;
    }

    public Language getLanguage() {
        return language;
    }

}
