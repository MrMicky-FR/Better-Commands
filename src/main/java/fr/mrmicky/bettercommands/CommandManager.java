package fr.mrmicky.bettercommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class CommandManager {

    private static final CommandMap BUKKIT_COMMAND_MAP;
    private static final Method REGISTER_COMMAND_METHOD;
    private static final Field KNOWN_COMMANDS_FIELD;

    private static String noPermissionMessage = ChatColor.RED + "You don't have the permission to perform this command!";

    private Plugin plugin;
    private Set<Command> commands = new HashSet<>();

    public CommandManager(Plugin plugin) {
        this.plugin = plugin;
    }

    static {
        Object commandMap = null;
        Method registerCommandMethod = null;
        Field knownCommandField = null;

        try {
            commandMap = Bukkit.getServer().getClass().getDeclaredMethod("getCommandMap").invoke(Bukkit.getServer());

            registerCommandMethod = SimpleCommandMap.class.getDeclaredMethod("register", String.class, org.bukkit.command.Command.class);
            knownCommandField = SimpleCommandMap.class.getDeclaredField("knownCommands");
            knownCommandField.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        BUKKIT_COMMAND_MAP = (CommandMap) commandMap;
        REGISTER_COMMAND_METHOD = registerCommandMethod;
        KNOWN_COMMANDS_FIELD = knownCommandField;
    }

    public void registerCommand(Command command) {
        if (commands.contains(command)) {
            return; // TODO do something ?
        }

        try {
            REGISTER_COMMAND_METHOD.invoke(BUKKIT_COMMAND_MAP, plugin.getName(), command.bukkitCommand);
            commands.add(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unregisterAll() {
        getCommands().forEach(this::unregisterCommand);
    }

    public void unregisterCommand(Command command) {
        if (commands.contains(command)) {
            return; // TODO do something ?
        }

        try {
            org.bukkit.command.Command bukkitCommand = command.bukkitCommand;

            Map<?, ?> knownCommands = (Map<?, ?>) KNOWN_COMMANDS_FIELD.get(BUKKIT_COMMAND_MAP);

            knownCommands.values().removeIf(bukkitCommand::equals);

            bukkitCommand.unregister(BUKKIT_COMMAND_MAP);
            commands.remove(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Set<Command> getCommands() {
        return new LinkedHashSet<>(commands);
    }

    public static String getNoPermissionMessage() {
        return noPermissionMessage;
    }

    public static void setNoPermissionMessage(String noPermissionMessage) {
        CommandManager.noPermissionMessage = noPermissionMessage;
    }
}
