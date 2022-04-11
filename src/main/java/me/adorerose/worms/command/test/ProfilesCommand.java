package me.adorerose.worms.command.test;

import me.adorerose.worms.command.PluginCommand;
import me.adorerose.worms.service.profile.PlayerProfile;
import me.adorerose.worms.service.profile.PlayerProfileManager;

public class ProfilesCommand extends PluginCommand {
    public ProfilesCommand() {
        super("profiles");
        setPermission("worms.admin");
    }

    @Override
    public void execute(PlayerProfile profile, String label, String[] args) {
        profile.sendMessage("§aСписок профилей онлайн:");
        for (PlayerProfile playerProfile: PlayerProfileManager.getPlayers().values()) {
            profile.sendMessage(playerProfile.toString());
        }
    }
}
