package fr.mrmicky.bettercommands;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface TabExecutor {

    List<String> tabComplete(CommandSender sender, String args[]);
}
