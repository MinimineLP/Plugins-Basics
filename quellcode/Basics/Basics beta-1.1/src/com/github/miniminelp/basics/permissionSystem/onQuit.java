package com.github.miniminelp.basics.permissionSystem;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

class onQuit implements Listener{
	
	@SuppressWarnings("unused")
	private Plugin plugin;
	
	public onQuit(Plugin plugin){
		this.plugin=plugin;
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		Player p = e.getPlayer();
		if(PermissionSystem.toDeop.contains(p.getName().toLowerCase())){
			p.setOp(false);
			PermissionSystem.toDeop.remove(p.getName().toLowerCase());
			PermissionSystem.saveToDeop();
		}
	}
}
