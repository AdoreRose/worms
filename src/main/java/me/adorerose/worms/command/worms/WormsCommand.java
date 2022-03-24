package me.adorerose.worms.command.worms;

import me.adorerose.worms.command.PluginCommand;
import me.adorerose.worms.command.plugin.ReloadCommand;

public class WormsCommand extends PluginCommand {
    public WormsCommand() {
        super("worms", 1);
        addSubcommand(new PluginCommand[] {
                new ReloadCommand(),
                new MapCommand()
        });
    }
}
