package com.github.miniminelp.basics.vanish;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;




@SuppressWarnings("deprecation")
public class JoinListener implements Listener{
	
	private Plugin plugin;
	
	public JoinListener(Plugin plugin){
		this.plugin=plugin;
	}
	
	@EventHandler
	private boolean onJoin(PlayerJoinEvent e) {
		for(String s : Vanish.vanish){
			e.getPlayer().hidePlayer(plugin.getServer().getPlayer(s));
		}
		return true;
	}
}

