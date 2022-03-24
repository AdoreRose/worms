package me.adorerose.worms.command.plugin;

import me.adorerose.worms.WormsPlugin;
import me.adorerose.worms.command.PluginCommand;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends PluginCommand {
    public ReloadCommand() {
        super("reload", 0);
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        WormsPlugin plugin = WormsPlugin.getInstance();
        plugin.getConfiguration().load();
        plugin.getLanguage().load();
        sender.sendMessage(plugin.getLanguage().CONFIG_RELOADED);
    }
}
