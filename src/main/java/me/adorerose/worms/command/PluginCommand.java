package me.adorerose.worms.command;

import me.adorerose.worms.WormsPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import java.util.Arrays;

public abstract class PluginCommand implements CommandExecutor {
    private static final WormsPlugin plugin = WormsPlugin.getInstance();
    private String cmdName;
    private int minArgs;
    private PluginCommand[] subCommands;
    private boolean consoleOnly;

    public void execute(CommandSender sender, String label, String[] args) { }

    public PluginCommand(String name, PluginCommand[] subCmds, int minArgs) {
        this.cmdName = name;
        this.subCommands = subCmds == null ? new PluginCommand[0] : subCmds;
        this.minArgs = minArgs;
    }

    public PluginCommand(String name, int minArgs) {
        this(name, null, minArgs);
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (consoleOnly && !sender.isOp()) sender.sendMessage(plugin.getLanguage().ONLY_CONSOLE);
        else if (args.length < minArgs) tooFewArgsAction(sender);
        else {
            if (isComposed()) {
                PluginCommand executable = matchSubcommand(args[0]);
                if (executable != null) {
                    String[] commandArgs = Arrays.copyOfRange(args, 1, args.length);
                    executable.execute(sender, args[0], commandArgs);
                } else sender.sendMessage(plugin.getLanguage().CMD_NOT_FOUND);
            }
            else execute(sender, label, args);
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
