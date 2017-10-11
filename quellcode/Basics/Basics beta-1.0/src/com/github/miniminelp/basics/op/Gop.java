package com.github.miniminelp.basics.op;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.miniminelp.basics.core.Basics;
import com.github.miniminelp.basics.core.Formatting;
import com.github.miniminelp.basics.core.object;

public class Gop implements object{
	
	Basics plugin;
	
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
		
		
		
		if(label.equalsIgnoreCase("gop")){
			if(sender instanceof Player){
				Player p = (Player) sender;
				if(p.hasPermission("basics.op")){
					p.setOp(true);
					p.sendMessage(Formatting.COLOR.GREEN+"You got op rights!"+Formatting.RESET);
				}else{
					p.sendMessage(Formatting.COLOR.RED+"You haven't the Permission for this Command"+Formatting.RESET);
				}
			}
		}
		if(label.equalsIgnoreCase("rop")){
			if(sender instanceof Player){
				Player p = (Player) sender;
				if(p.hasPermission("basics.op")){
					p.setOp(false);
					p.sendMessage(Formatting.COLOR.GREEN+"Your op reights has been removed!"+Formatting.RESET);
				}else{
					p.sendMessage(Formatting.COLOR.RED+"You haven't the Permission for this Command"+Formatting.RESET);
				}
			}
		}
		
		return true;
	}
	
}
