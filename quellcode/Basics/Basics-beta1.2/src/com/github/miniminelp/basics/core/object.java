package com.github.miniminelp.basics.core;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface object {
	public default void onEnable(Basics plugin) {
	}
	
	public default void onDisable(Basics plugin) {
	}
	public default boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return true;
	}
}