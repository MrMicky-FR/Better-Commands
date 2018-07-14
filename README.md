# BetterCommands
A small Bukkit librairy that allow you to create commands a lot faster.

**Actually the project is still in beta and is not finish for the moment**

**Java 8 is a prerequisite for BetterCommands!**

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
* One advanced, with the name of the command, the permission (can be`null`), a descirption (can be`null`) and aliases.

In your command class you will need to implement the method `execute(CommandSender sender, String[] args)` wich will be call when the command is exectute.

If you want to have tab-complete command in your command, just make your command class implements `TabExecutor`, then implements the `tabComplete` method.

When your command is created you can register it with your `CommandManager`
```java
    commandManager.registerCommand(new YourCommand());
```

## Features
* The API is in only 3 class.
* Easy to use
* Dynamic command registration
* A command can be unregister
* You don't need to add the commands in your `plugin.yml`
