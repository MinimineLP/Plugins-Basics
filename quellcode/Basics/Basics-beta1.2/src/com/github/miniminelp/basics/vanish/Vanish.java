package com.github.miniminelp.basics.vanish;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.miniminelp.basics.core.Basics;
import com.github.miniminelp.basics.core.Formatting;
import com.github.miniminelp.basics.core.object;

@SuppressWarnings("deprecation")
public class Vanish implements object {
	
	private Basics plugin;
	public static List<String> vanish=new LinkedList<String>();
	
	@Override
	public void onEnable(Basics plugin) {
		this.plugin=plugin;
		if(!Basics.config.contains("Basics.vanish.see_permission"))Basics.config.set("Basics.vanish.see_permission", "basics.vanish.seeVanishedPlayers");
		if(!Basics.config.contains("Basics.vanish.show_leave_message"))Basics.config.set("Basics.vanish.show_leave_message", true);
		if(!Basics.config.contains("Basics.vanish.leave_message"))Basics.config.set("Basics.vanish.leave_message", "&e%player% left the game&r");
		if(!Basics.config.contains("Basics.vanish.show_join_message"))Basics.config.set("Basics.vanish.show_join_message", true);
		if(!Basics.config.contains("Basics.vanish.join_message"))Basics.config.set("Basics.vanish.join_message", "&e%player% joined the game&r");
		
		Bukkit.getPluginManager().registerEvents(new JoinListener(plugin), plugin);
		Bukkit.getPluginManager().registerEvents(new QuitListener(plugin), plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("v")||label.equalsIgnoreCase("vanish")){
			if(args.length==0){
				if(sender.hasPermission("Basics.vanish.self")){
					if(sender instanceof Player){
						Player p=(Player)sender;
						if(!vanish.contains(p.getName().toLowerCase())){
							vanish.add(p.getName().toLowerCase());
							for(Player p2 : plugin.getServer().getOnlinePlayers()){
								if(!p2.hasPermission("basics.vanish.seeVanishedPlayers")){
									p2.hidePlayer(p);
									if(Basics.config.getBoolean("Basics.vanish.show_leave_message"))p2.sendMessage(Basics.config.getString("Basics.vanish.leave_message").replaceAll("&", "§").replaceAll("%player%", p.getName()));
								}
							}
							sender.sendMessage(Formatting.COLOR.GREEN+"You are now invisible to other Players");
						}else{
							vanish.remove(p.getName().toLowerCase());
							for(Player p2 : plugin.getServer().getOnlinePlayers()){
								p2.showPlayer(p);

								if(!p2.hasPermission("basics.vanish.seeVanishedPlayers")){
									if(Basics.config.getBoolean("Basics.vanish.show_join_message"))p2.sendMessage(Basics.config.getString("Basics.vanish.join_message").replaceAll("&", "§").replaceAll("%player%", p.getName()));
								}
							}
							sender.sendMessage(Formatting.COLOR.GREEN+"You are now visible to other Players");
						}
					}else{
						sender.sendMessage(Formatting.COLOR.RED+"Yust Players can vanish theirselves!");
					}
				}else{
					sender.sendMessage(Formatting.COLOR.RED+"No Permission");
				}
			}else{
				if(sender.hasPermission("Basics.vanish.other")){
					try{
						Player p=plugin.getServer().getPlayer(args[0]);
						if(!vanish.contains(p.getName().toLowerCase())){
							vanish.add(p.getName().toLowerCase());
							for(Player p2 : plugin.getServer().getOnlinePlayers()){
								if(!p2.hasPermission("basics.vanish.seeVanishedPlayers")){
									p2.hidePlayer(p);
									if(Basics.config.getBoolean("Basics.vanish.show_leave_message"))p2.sendMessage(Basics.config.getString("Basics.vanish.leave_message").replaceAll("&", "§").replaceAll("%player%", p.getName()));
								}
							}
							sender.sendMessage(p.getName()+" is now invisible to other players!");
							p.sendMessage(Formatting.COLOR.GREEN+"You are now invisible to other Players");
						}else{
							vanish.remove(p.getName().toLowerCase());
							for(Player p2 : plugin.getServer().getOnlinePlayers()){
								p2.showPlayer(p);
								if(!p2.hasPermission("basics.vanish.seeVanishedPlayers")){
									if(Basics.config.getBoolean("Basics.vanish.show_join_message"))p2.sendMessage(Basics.config.getString("Basics.vanish.join_message").replaceAll("&", "§").replaceAll("%player%", p.getName()));
								}
							}
							sender.sendMessage(p.getName()+" is now visible to other players!");
							p.sendMessage(Formatting.COLOR.GREEN+"You are now visible to other Players");
						}
					}catch (Exception e) {
						sender.sendMessage(Formatting.COLOR.RED+"This Player is not online!");
					}
				}else{
					sender.sendMessage(Formatting.COLOR.RED+"No Permission");
				}
			}
		}
		return true;
	}
}
