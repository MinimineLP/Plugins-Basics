package com.github.miniminelp.basics.fly;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.miniminelp.basics.core.Basics;
import com.github.miniminelp.basics.core.Formatting;
import com.github.miniminelp.basics.core.object;

public class Fly implements object {
	
	
	
	private Basics plugin;
	private List<String> flyingPlayers = new LinkedList<String>();
	
	
	@Override
	public void onEnable(Basics plugin) {
		this.plugin=plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("fly")){
			if(args.length==0){
				if(sender.hasPermission("basics.fly.self")){
					if(sender instanceof Player){
						Player p=(Player)sender;
						if(!p.getGameMode().equals(GameMode.CREATIVE)&&!p.getGameMode().equals(GameMode.SPECTATOR)){
							if(!flyingPlayers.contains(p.getName().toLowerCase())){
								p.setAllowFlight(true);
								flyingPlayers.add(p.getName().toLowerCase());
								p.sendMessage(Formatting.COLOR.GREEN+"Flyingmode enabled!");
							}else{
								p.setAllowFlight(false);
								flyingPlayers.remove(p.getName().toLowerCase());
								p.sendMessage(Formatting.COLOR.GREEN+"Flyingmode disabled!");
							}
						}else{
							sender.sendMessage(Formatting.COLOR.RED+"You are in creative or spectator mode!");
						}
					}else{
						sender.sendMessage(Formatting.COLOR.RED+"Consoles cannot fly");
					}
				}else{
					sender.sendMessage(Formatting.COLOR.RED+"No Permission");
				}
			}else{
				if(sender.hasPermission("basics.fly.other")){
					Player p=plugin.getServer().getPlayer(args[0]);
					try {
						if(!p.getGameMode().equals(GameMode.CREATIVE)&&!p.getGameMode().equals(GameMode.SPECTATOR)){
							if(!flyingPlayers.contains(p.getName().toLowerCase())){
								p.setAllowFlight(true);
								flyingPlayers.add(p.getName().toLowerCase());
								sender.sendMessage(p.getName()+" can now fly!");
								p.sendMessage(Formatting.COLOR.GREEN+"Flyingmode enabled!");
							}else{
								p.setAllowFlight(false);
								flyingPlayers.remove(p.getName().toLowerCase());
								sender.sendMessage(p.getName()+" can not longer fly!");
								p.sendMessage(Formatting.COLOR.GREEN+"Flyingmode disabled!");
							}
						}else{
							sender.sendMessage(Formatting.COLOR.RED+"This player is in creative or spectator mode!");
						}
					} catch (Exception e) {
						sender.sendMessage(Formatting.COLOR.RED+"Player not online!");
					}
				}else{
					sender.sendMessage(Formatting.COLOR.RED+"No Permission");
				}
			}
		}
		return true;
	}
}
