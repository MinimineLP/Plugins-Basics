package com.github.miniminelp.basics.feed;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.miniminelp.basics.core.object;


public class Feed implements object {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("feed")){
			if(sender instanceof Player){
				//Player p=
			}else{
				sender.sendMessage("Just Player can feed theirselves!");
			}
		}
		return false;
	}

}
