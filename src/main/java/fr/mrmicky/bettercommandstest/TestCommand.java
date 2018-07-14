package fr.mrmicky.bettercommandstest;

import fr.mrmicky.bettercommands.Command;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class TestCommand extends Command {

    private BetterCommandsTest plugin;

    public TestCommand(BetterCommandsTest plugin) {
        super("testbetter", "bettercommands.test", "Test ", "testbetters");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0 || !args[0].equalsIgnoreCase("remove")) {
            sender.sendMessage(ChatColor.GOLD + "It s work =)");
        } else {
            plugin.getCommandManager().unregisterCommand(this);
        }
    }
}
