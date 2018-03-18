package com.github.miniminelp.basics.ban;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

import com.github.miniminelp.basics.core.Formatting;


class ChatListener implements Listener{
	
	@SuppressWarnings("unused")
	private Plugin plugin;
	
	public ChatListener(Plugin plugin){
		this.plugin=plugin;
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e){
		if(Mutes.isPlayerMuted(e.getPlayer())){
			e.setCancelled(true);
			e.getPlayer().sendMessage(Formatting.COLOR.RED+"You are muted! Type /muteinfo for more infos!"+Formatting.RESET);
		}
	}
}
