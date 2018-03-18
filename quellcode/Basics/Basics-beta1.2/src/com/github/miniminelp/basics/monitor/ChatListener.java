package com.github.miniminelp.basics.monitor;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;

import com.github.miniminelp.basics.core.Formatting;


class ChatListener implements Listener {
	private Plugin plugin;
	
	public ChatListener(Plugin plugin){
		this.plugin=plugin;
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e){
		for(String s : Monitor.monitor){
			try{
				plugin.getServer().getPlayer(s).sendMessage(Formatting.COLOR.BLUE+"[Basics-Monitor]"+Formatting.COLOR.YELLOW+" Received Message! Sender: "+e.getPlayer().getName()+", Message: "+e.getMessage());
			}catch (Exception ex){
				
			}
		}
	}
	@EventHandler
	public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent e) {
		for(String s : Monitor.monitor){
			try{
				plugin.getServer().getPlayer(s).sendMessage(Formatting.COLOR.BLUE+"[Basics-Monitor] "+Formatting.COLOR.RED+" Received Command! Sender: "+e.getPlayer().getName()+", Command: "+e.getMessage());
			}catch (Exception ex){
				
			}
		}
	}
}
