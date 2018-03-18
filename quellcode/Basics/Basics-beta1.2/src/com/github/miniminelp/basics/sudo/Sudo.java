package com.github.miniminelp.basics.sudo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.github.miniminelp.basics.core.Basics;
import com.github.miniminelp.basics.core.Formatting;
import com.github.miniminelp.basics.core.object;

public class Sudo implements object{
	
	private Basics plugin;
	
	@Override
	public void onEnable(Basics plugin) {
		this.plugin=plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("sudo")){
			if(sender.hasPermission("basics.sudo")){
				if(args.length>1){
					String toChat="";
					for(int i=1;i<args.length;i++){
						toChat+=args[i]+" ";
					}
					sender.sendMessage(Formatting.COLOR.GREEN+"Successfully run \""+toChat+"\" from Player "+plugin.getServer().getPlayer(args[0]).getName());
					plugin.getServer().getPlayer(args[0]).chat(toChat);
					
				}else{
					return false;
				}
			}else{
				sender.sendMessage(Formatting.COLOR.RED+"No Permission");
			}
		}
		return true;
	}
}
