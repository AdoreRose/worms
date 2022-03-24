package me.adorerose.worms.command.worms;

import me.adorerose.worms.command.PluginCommand;

public class WormsCommand extends PluginCommand {
    public WormsCommand() {
        super("worms", new PluginCommand[] { new MapCommand() } , 1);
    }
}
