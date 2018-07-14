package fr.mrmicky.bettercommands;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public abstract class Command {

    private final String name;
    private final String permission;
    private final String[] aliases;

    BukkitCommandExecutor bukkitCommand;

    public Command(String name) {
        this(name, null, null);
    }

    public Command(String name, String permission, String description, String... aliases) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }

        this.name = name;
        this.permission = permission;
        this.aliases = aliases;

        bukkitCommand = new BukkitCommandExecutor(description == null ? "" : description);
    }

    public abstract void execute(CommandSender sender, String[] args);

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public String[] getAliases() {
        return aliases;
    }

    protected class BukkitCommandExecutor extends org.bukkit.command.Command {

        private BukkitCommandExecutor(String description) {
            super(name, description, "", Arrays.asList(aliases));
        }

        @Override
        public boolean execute(CommandSender sender, String commandLabel, String[] args) {
            if (testPermission(sender)) {
                try {
                    Command.this.execute(sender, args);
                } catch (Exception e) {
                    throw new CommandException("Error in dispatching command '" + name + "'", e);
                }
            }
            return true;
        }

        @Override
        public boolean testPermission(CommandSender target) {
            if (permission == null || target.hasPermission(permission)) {
                return true;
            }

            target.sendMessage(CommandManager.getNoPermissionMessage());

            return false;
        }

        @Override
        public boolean testPermissionSilent(CommandSender target) {
            return true;
        }

        @Override
        public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
            if (Command.this instanceof TabExecutor) {
                try {
                    List<String> completions = ((TabExecutor) Command.this).tabComplete(sender, args);

                    if (completions != null) {
                        return completions;
                    }
                } catch (Exception e) {
                    throw new CommandException("Error in tab completing command '" + name + "'", e);
                }
            }
            return super.tabComplete(sender, alias, args);
        }
    }
}