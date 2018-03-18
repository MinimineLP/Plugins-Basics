package com.github.miniminelp.basics.gamemode;


import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.miniminelp.basics.core.Basics;
import com.github.miniminelp.basics.core.Formatting;
import com.github.miniminelp.basics.core.object;

public class Gamemode implements object {
	private Basics plugin;
	
	@Override
	public void onEnable(Basics plugin) {
		this.plugin=plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("gm")){
			if(sender.hasPermission("basics.gamemode")){
				if(args.length==1){
					if(sender instanceof Player){
						
						Player p =(Player)sender;
						
						switch (args[0]) {
						case "0":
							p.setGameMode(GameMode.SURVIVAL);
							p.sendMessage(Formatting.COLOR.BLUE+"Your gamemode has been updatet to Survival"+Formatting.RESET);
							break;
						case "1":
							p.setGameMode(GameMode.CREATIVE);
							p.sendMessage(Formatting.COLOR.BLUE+"Your gamemode has been updatet to Creative"+Formatting.RESET);
							break;
						case "2":
							p.setGameMode(GameMode.ADVENTURE);
							p.sendMessage(Formatting.COLOR.BLUE+"Your gamemode has been updatet to Adventure"+Formatting.RESET);
							break;
						case "3":
							p.setGameMode(GameMode.SPECTATOR);
							p.sendMessage(Formatting.COLOR.BLUE+"Your gamemode has been updatet to Spectator"+Formatting.RESET);
							break;
						default:
							sender.sendMessage("This gamemode does not exists");
							break;
						}
					}
				}else if(args.length>1){
					try {
						
						Player p =plugin.getServer().getPlayer(args[1]);
						
						switch (args[0]) {
						case "0":
							p.setGameMode(GameMode.SURVIVAL);
							p.sendMessage(Formatting.COLOR.BLUE+"Your gamemode has been updatet to Survival"+Formatting.RESET);
							sender.sendMessage(Formatting.COLOR.BLUE+p.getName()+"'s gamemode has been updated to Survival"+Formatting.RESET);
							break;
						case "1":
							p.setGameMode(GameMode.CREATIVE);
							p.sendMessage(Formatting.COLOR.BLUE+"Your gamemode has been updatet to Creative"+Formatting.RESET);
							sender.sendMessage(Formatting.COLOR.BLUE+p.getName()+"'s gamemode has been updated to Creative"+Formatting.RESET);
							break;
						case "2":
							p.setGameMode(GameMode.ADVENTURE);
							p.sendMessage(Formatting.COLOR.BLUE+"Your gamemode has been updatet to Adventure"+Formatting.RESET);
							sender.sendMessage(Formatting.COLOR.BLUE+p.getName()+"'s gamemode has been updated to Adventure"+Formatting.RESET);
							break;
							case "3":
								p.setGameMode(GameMode.SPECTATOR);
								p.sendMessage(Formatting.COLOR.BLUE+"Your gamemode has been updatet to Spectator"+Formatting.RESET);
							sender.sendMessage(Formatting.COLOR.BLUE+p.getName()+"'s gamemode has been updated to Spectator"+Formatting.RESET);
							break;
						default:
							sender.sendMessage("This gamemode does not exists");
							break;
						}
					} catch (Exception e) {
						sender.sendMessage(Formatting.COLOR.RED+"This Player is not online!"+Formatting.RESET);
					}
				}else{
					return false;
				}
			}else{
				sender.sendMessage("No Permission");
			}
		}
		
		return true;
	}
}
