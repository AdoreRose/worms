package me.adorerose.worms.command.test;

import me.adorerose.worms.command.CommandPermission;
import me.adorerose.worms.command.PluginCommand;
import me.adorerose.worms.service.profile.PlayerProfile;
import me.adorerose.worms.service.profile.PlayerProfileManager;

@CommandPermission(permission = "worms.admin")
public class ProfilesCommand extends PluginCommand {
    public ProfilesCommand() {
        super("profiles");
    }

    @Override
    public void execute(PlayerProfile profile, String label, String[] args) {
        profile.sendMessage("§aСписок профилей онлайн:");
        for (PlayerProfile playerProfile: PlayerProfileManager.getPlayers().values()) {
            profile.sendMessage(playerProfile.toString());
        }
    }
}
