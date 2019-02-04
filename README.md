# BetterCommands
A small Bukkit library that allow you to create commands a lot faster.

**Currently the project is still in beta and you should not use it if you don't know what you are doing as it can break compatibility with some plugins**

## How to use

First you need to create a new `CommandManager` for your plugin and store it
```java
private CommandManager commandManager;

@Override
public void onEnable() {
    commandManager = new CommandManager(this);
}
```

Now you will need to create a new Command, just create a class that extend `Command` (you need to use `fr.mrmicky.bettercommands.Command`)

The `Command` class has two constructors:
* One basic, with only a `String` (the name of the command)
* One advanced, with the name of the command, the permission (can be `null`), a descirption (can be `null`) and aliases.

In your command class you will need to implement the method `execute` wich will be call when the command is exectute.

If you want to have tab completion in your command, just make your command class implements `TabExecutor`, then implements the `tabComplete` method.

You should have something like this
```java
import fr.mrmicky.bettercommands.Command;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ComandTest extends Command {

    private BetterCommandsTest plugin;

    public ComandTest(BetterCommandsTest plugin) {
        super("testbetter", "bettercommands.test", "Test", "testbetters");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("remove")) {
            plugin.getCommandManager().unregisterCommand(this);
            sender.sendMessage(ChatColor.GREEN + "Command removed");
            return;
        }

        sender.sendMessage(ChatColor.GOLD + "It's work =)");
    }
}
```

When your command is created you can register it with your `CommandManager`
```java
    commandManager.registerCommand(new ComandTest());
```

## Features
* The API is in only 3 class
* Easy to use
* Dynamic command registration
* A command can be unregister
* You don't need to add the commands in your `plugin.yml`
