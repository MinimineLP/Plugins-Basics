package com.github.miniminelp.basics.permissionSystem;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;


class JoinListener implements Listener{
	
	@SuppressWarnings("unused")
	private Plugin plugin;
	
	public JoinListener(Plugin plugin){
		this.plugin=plugin;
	}
	
	@EventHandler
	private boolean onJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		if(!PermissionSystem.hasPlayerGroup(p.getUniqueId().toString().toLowerCase()))System.out.println(PermissionSystem.addPlayerToDefaultGroup(p.getUniqueId().toString().toLowerCase()));
		PermissionSystem.setPlayerName(p.getUniqueId().toString(), p.getName());
		PermissionSystem.givePlayerHisGroupPermissions(p.getName());
		PermissionSystem.givePlayerHisPermissions(p);
		return true;
	}
}

