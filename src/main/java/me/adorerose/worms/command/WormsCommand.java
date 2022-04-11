package me.adorerose.worms.command;

import me.adorerose.worms.command.plugin.ReloadCommand;
import me.adorerose.worms.command.test.ProfilesCommand;
import me.adorerose.worms.command.worms.MapCommand;

public class WormsCommand extends PluginCommand {
    public static ReloadCommand reloadCommand = new ReloadCommand();
    public static MapCommand mapCommand = new MapCommand();
    public static ProfilesCommand profilesCommand = new ProfilesCommand();

    public WormsCommand() {
        super("worms", 1);
        addSubcommand(new PluginCommand[] {
                reloadCommand,
                mapCommand,
                profilesCommand
        });
    }
}
