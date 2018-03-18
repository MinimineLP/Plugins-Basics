package com.github.miniminelp.basics.vanish;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class QuitListener implements Listener {

	private Plugin plugin;
	
	public QuitListener(Plugin plugin){
		this.plugin=plugin;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	private boolean onQuit(PlayerQuitEvent e) {
		if(Vanish.vanish.contains(e.getPlayer().getName().toLowerCase())){
			Vanish.vanish.remove(e.getPlayer().getName().toLowerCase());
		}
		for(Player p : plugin.getServer().getOnlinePlayers()){
			p.showPlayer(e.getPlayer());
		}
		return true;
	}
}
