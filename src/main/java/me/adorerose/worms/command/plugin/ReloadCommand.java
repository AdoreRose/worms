package me.adorerose.worms.command.plugin;

import me.adorerose.worms.WormsPlugin;
import me.adorerose.worms.command.CommandPermission;
import me.adorerose.worms.command.PluginCommand;
import me.adorerose.worms.service.profile.PlayerProfile;

@CommandPermission(permission = "worms.command.reload")
public class ReloadCommand extends PluginCommand {
    public ReloadCommand() {
        super("reload");
    }

    @Override
    public void execute(PlayerProfile profile, String label, String[] args) {
        WormsPlugin plugin = WormsPlugin.getInstance();
        plugin.getConfiguration().load();
        plugin.getLanguage().load();
        profile.sendMessage(plugin.getLanguage().CONFIG_RELOADED);
    }
}
