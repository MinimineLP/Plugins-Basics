package com.github.miniminelp.basics.feed;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.miniminelp.basics.core.Basics;
import com.github.miniminelp.basics.core.Formatting;
import com.github.miniminelp.basics.core.object;


public class Feed implements object {
	
	private Basics plugin;
	@Override
	public void onEnable(Basics plugin) {
		this.plugin=plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("feed")){
			if(args.length>0){
				if(sender.hasPermission("basics.feed.other")){
					Player p=plugin.getServer().getPlayer(args[0]);
					try {
						p.setFoodLevel(20);
						p.setSaturation(8);
					} catch (Exception e) {
						sender.sendMessage("This Player is not online!");
					}
				}else{
					sender.sendMessage(Formatting.COLOR.RED+"No Permission"+Formatting.RESET);
				}
			}else{
				if(sender.hasPermission("basics.feed.self")){
					if(sender instanceof Player){
						Player p=(Player)sender;
						p.setFoodLevel(20);
						p.setSaturation(8);
					}else{
						sender.sendMessage("Just Player can feed theirselves!");
					}
				}else{
					sender.sendMessage(Formatting.COLOR.RED+"No Permission"+Formatting.RESET);
				}
			}
			
		}
		return true;
	}

}
