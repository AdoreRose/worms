package me.adorerose.worms.command;

import me.adorerose.worms.WormsPlugin;
import me.adorerose.worms.service.profile.PlayerProfile;
import me.adorerose.worms.service.profile.PlayerProfileManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import java.util.Arrays;

public abstract class PluginCommand implements CommandExecutor {
    protected static final WormsPlugin plugin = WormsPlugin.getInstance();
    private final String cmdName;
    private final int minArgs;
    private PluginCommand[] subCommands;
    private boolean consoleOnly;

    public void execute(PlayerProfile profile, String label, String[] args) { }

    public PluginCommand(String name, PluginCommand[] subCmds, int minArgs) {
        this.cmdName = name;
        this.subCommands = subCmds == null ? new PluginCommand[0] : subCmds;
        this.minArgs = minArgs;
    }

    public PluginCommand(String name, int minArgs) {
        this(name, null, minArgs);
    }

    public PluginCommand(String name) {
        this(name, null, 0);
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (consoleOnly && !sender.isOp()) sender.sendMessage(plugin.getLanguage().ONLY_CONSOLE);
        else if (args.length < minArgs) tooFewArgsAction(sender);
        else {
            PlayerProfile profile = PlayerProfileManager.getPlayers().get(sender);
            if (profile == null) return true;

            PluginCommand executable;
            if (isComposed()) {
                executable = matchSubcommand(args[0]);
                if (executable != null) {
                    args = Arrays.copyOfRange(args, 1, args.length);
                }
                else {
                    sender.sendMessage(plugin.getLanguage().CMD_NOT_FOUND);
                    return true;
                }
            }
            else executable = this;

            CommandPermission annotation = executable.getClass().getDeclaredAnnotation(CommandPermission.class);
            if (annotation != null && !sender.hasPermission(annotation.permission())) sender.sendMessage(plugin.getLanguage().NO_PERMISSION);
            else if (args.length < executable.minArgs) tooFewArgsAction(sender);
            else executable.execute(profile, label, args);
        }
        return true;
    }

    public void setConsoleOnly(boolean value) {
        this.consoleOnly = value;
    }

    public PluginCommand[] getSubcommands() {
        return subCommands;
    }

    public void addSubcommand(PluginCommand[] commands) {
        PluginCommand[] newCmds = new PluginCommand[subCommands.length + commands.length];
        System.arraycopy(this.subCommands, 0, newCmds, 0, this.subCommands.length);
        System.arraycopy(commands, 0, newCmds, this.subCommands.length, commands.length);
        this.subCommands = newCmds;
    }

    private PluginCommand matchSubcommand(String name) throws IllegalStateException {
        for (PluginCommand command: subCommands) {
            if (command.cmdName.equals(name)) return command;
        }
        return null;
    }

    public boolean isComposed() {
        return subCommands.length > 0;
    }

    public void tooFewArgsAction(CommandSender sender) {
        sender.sendMessage(plugin.getLanguage().NOT_ENOUGH_ARGS + minArgs);
    }

    public String getName() {
        return cmdName;
    }

}
