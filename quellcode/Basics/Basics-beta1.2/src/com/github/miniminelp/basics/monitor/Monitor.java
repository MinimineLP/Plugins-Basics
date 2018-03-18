package com.github.miniminelp.basics.monitor;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.miniminelp.basics.core.Basics;
import com.github.miniminelp.basics.core.Formatting;
import com.github.miniminelp.basics.core.object;

@SuppressWarnings({"unused", "static-access"})
public class Monitor implements object {
	
	static List<String> monitor = new LinkedList<String>();
	private static Basics plugin;
	
	@Override
	public void onEnable(Basics plugin) {
		this.plugin=plugin;
		Bukkit.getPluginManager().registerEvents(new ChatListener(plugin), plugin);
	}
	
	@Override
	public void onDisable(Basics plugin) {
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("monitor")){
			if(sender.hasPermission("basics.monitor")){
				if(sender instanceof Player){
					if(!monitor.contains(sender.getName().toLowerCase())){
						monitor.add(sender.getName().toLowerCase());
						sender.sendMessage(Formatting.COLOR.GREEN+"Monitor Mode enabled");
					}else{
						monitor.remove(sender.getName().toLowerCase());
						sender.sendMessage(Formatting.COLOR.RED+"Monitor Mode disabled");
					}
				}else{
					sender.sendMessage(Formatting.COLOR.RED+"You must be a Player to run this command!");
				}
			}else{
				sender.sendMessage(Formatting.COLOR.RED+"No Permission");
			}
		}
		return true;
	}
}
