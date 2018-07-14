package fr.mrmicky.bettercommandstest;

import fr.mrmicky.bettercommands.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class BetterCommandsTest extends JavaPlugin {

    private CommandManager commandManager;

    @Override
    public void onEnable() {
        commandManager = new CommandManager(this);
        commandManager.registerCommand(new TestCommand(this));
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }
}
