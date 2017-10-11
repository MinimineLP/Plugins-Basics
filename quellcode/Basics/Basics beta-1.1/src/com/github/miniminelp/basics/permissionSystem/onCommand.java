package com.github.miniminelp.basics.permissionSystem;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;

class onCommand implements Listener{
	
	private Plugin plugin;
	
	public onCommand(Plugin plugin){
		this.plugin=plugin;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
		if(event.getMessage().startsWith("/deop ")){
			if(plugin.getServer().getPlayer(event.getMessage().replaceFirst("/deop ", "").split(" ")[0]).hasPermission("*")){
				event.setCancelled(true);
				if(!PermissionSystem.toDeop.contains(event.getMessage().replaceFirst("/deop ", "").split(" ")[0].toLowerCase())){
					try {
						PermissionSystem.toDeop.add(Bukkit.getOfflinePlayer(event.getMessage().replaceFirst("/deop ", "").split(" ")[0]).getUniqueId());
					} catch (Exception e) {
					}
					PermissionSystem.saveToDeop();
				}
			}
		}
		
		if(event.getMessage().startsWith("/op ")){
			System.out.println(event.getMessage().replaceFirst("/op ", "").split(" ")[0].toLowerCase());
			if(PermissionSystem.toDeop.contains(Bukkit.getOfflinePlayer(event.getMessage().replaceFirst("/op ", "").split(" ")[0]).getUniqueId())){
				PermissionSystem.toDeop.remove(Bukkit.getOfflinePlayer(event.getMessage().replaceFirst("/op ", "").split(" ")[0]).getUniqueId());
			}
			PermissionSystem.saveToDeop();
		}
		/*for (String command : cmds) {
			if (event.getMessage().equalsIgnoreCase("/" + command)) {
				event.setCancelled(true);
				p.sendMessage("§cYou do not have permission to do that!");
			}
		}*/
	}
}
