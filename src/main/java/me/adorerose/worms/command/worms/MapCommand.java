package me.adorerose.worms.command.worms;

import me.adorerose.worms.command.PluginCommand;
import me.adorerose.worms.service.profile.PlayerProfile;

public class MapCommand extends PluginCommand {
    public MapCommand() {
        super("map");
    }

    @Override
    public void execute(PlayerProfile profile, String label, String[] args) {
        profile.getPlayer().sendMessage("Getting a virtue in a vice.");
    }
}
