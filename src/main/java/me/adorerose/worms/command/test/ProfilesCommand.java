package me.adorerose.worms.command.test;

import me.adorerose.worms.command.PluginCommand;
import me.adorerose.worms.service.profile.PlayerProfile;
import me.adorerose.worms.service.profile.PlayerProfileManager;
import org.bukkit.entity.Player;

public class ProfilesCommand extends PluginCommand {
    public ProfilesCommand() {
        super("profiles");
        setPermission("worms.admin");
    }

    @Override
    public void execute(PlayerProfile profile, String label, String[] args) {
        Player player = profile.getPlayer();
        player.sendMessage("§aСписок профилей онлайн:");
        for (PlayerProfile playerProfile: PlayerProfileManager.getPlayers().values()) {
            player.sendMessage(playerProfile.toString());
        }
    }
}
