package fr.mrmicky.bettercommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CommandManager {

    private static final CommandMap BUKKIT_COMMAND_MAP;
    private static final Field KNOWN_COMMANDS_FIELD;

    private static String noPermissionMessage = ChatColor.RED + "You don't have the permission to perform this command!";

    private Plugin plugin;
    private Set<Command> commands = new HashSet<>();

    public CommandManager(Plugin plugin) {
        this.plugin = plugin;
    }

    static {
        try {
            BUKKIT_COMMAND_MAP = (CommandMap) Bukkit.getServer().getClass().getDeclaredMethod("getCommandMap").invoke(Bukkit.getServer());

            KNOWN_COMMANDS_FIELD = SimpleCommandMap.class.getDeclaredField("knownCommands");
            KNOWN_COMMANDS_FIELD.setAccessible(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void registerCommand(Command command) {
        if (commands.contains(command)) {
            return; // TODO do something ?
        }

        BUKKIT_COMMAND_MAP.register(plugin.getName(), command.bukkitCommand);
        commands.add(command);
    }

    public void unregisterAll() {
        getCommands().forEach(this::unregisterCommand);
    }

    public void unregisterCommand(Command command) {
        if (commands.contains(command)) {
            return; // TODO do something ?
        }

        org.bukkit.command.Command bukkitCommand = command.bukkitCommand;

        try {
            Map<?, ?> knownCommands = (Map<?, ?>) KNOWN_COMMANDS_FIELD.get(BUKKIT_COMMAND_MAP);

            knownCommands.values().removeIf(bukkitCommand::equals);

            bukkitCommand.unregister(BUKKIT_COMMAND_MAP);
            commands.remove(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Set<Command> getCommands() {
        return new HashSet<>(commands);
    }

    public static String getNoPermissionMessage() {
        return noPermissionMessage;
    }

    public static void setNoPermissionMessage(String noPermissionMessage) {
        CommandManager.noPermissionMessage = noPermissionMessage;
    }
}
