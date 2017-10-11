package com.github.miniminelp.basics.tpa;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.miniminelp.basics.core.Basics;
import com.github.miniminelp.basics.core.object;

public class Tpa implements object{
	
	private Basics plugin;
	HashMap<String, String> teleports = new HashMap<String, String>();
	
	@Override
	public void onEnable(Basics plugin) {
		this.plugin=plugin;
	}

	@Override
	public void onDisable(Basics plugin) {
		this.plugin=plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		
		
		
		//TPA
		if(label.equalsIgnoreCase("tpa")){
			if(sender instanceof Player){
				Player p = (Player)sender;
				
				if(p.hasPermission("basics.tpa.tpa")){
					if(args.length>0){
						args[0]=args[0].toLowerCase();
						Player toTp=plugin.getServer().getPlayer(args[0]);
						try {
							toTp.sendMessage("You've got a tp request from "+p.getName());
							
							if(teleports.containsKey(p.getName())){
								p.sendMessage("You allready sent a tp request to a Player, it will be resettet");
								try {
									plugin.getServer().getPlayer(teleports.get(p.getName().toLowerCase())).sendMessage("The tp request from "+p.getName()+" has been resettet");
								} catch (Exception e) {
								}
								teleports.remove(p.getName().toLowerCase());
							}
							
							teleports.put(p.getName().toLowerCase(), args[0].toLowerCase());
							p.sendMessage("Your tp request has been sent to "+args[0]);
							
						} catch (Exception e) {
							p.sendMessage("Player "+args[0]+" is not online!");
						}	
					}else{
						return false;
					}
				}
			}
		}
		
		if(label.equalsIgnoreCase("tpaaccept")){
			if(sender instanceof Player){
				Player p = (Player)sender;
				if(p.hasPermission("basics.tpa.tpaaccept")){
					if(args.length>0){
						if(teleports.containsKey(args[0].toLowerCase())){
							if(teleports.get(args[0].toLowerCase()).toLowerCase().equalsIgnoreCase(p.getName().toLowerCase())){
								Player p2 = plugin.getServer().getPlayer(args[0].toLowerCase());
								try {
									p2.sendMessage("You will be teleported to "+p.getName());
									p2.teleport(p.getLocation());
									teleports.remove(args[0].toLowerCase());
									p.sendMessage(p2.getName()+" has been teleported to you!");
								} catch (Exception e) {
								}
							}else{
								p.sendMessage("This player has not requested you, or the request is not (longer) valid!");
							}
						}else{
							p.sendMessage("This player has not requested you, or the request is not (longer) valid!");
						}
					}else{
						return false;
					}
				}
			}
		}
		
		/*if(cmd.equalsIgnoreCase("tpforbit")){
			if(p.hasPermission("basics.tpa.tpaforbit")){
				if(args.length>0){
				
				}else{
					
				}
			}
		}*/
		return true;
	}

}
