package me.adorerose.worms.command.worms;

import me.adorerose.worms.command.PluginCommand;
import org.bukkit.command.CommandSender;

public class MapCommand extends PluginCommand {
    public MapCommand() {
        super("map", 0);
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        sender.sendMessage("Getting a virtue in a vice.");
    }
}
