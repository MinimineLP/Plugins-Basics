package com.github.miniminelp.basics.changelog;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import com.github.miniminelp.basics.core.Basics;



public class JoinListener implements Listener{
	
	@SuppressWarnings("unused")
	private Plugin plugin;
	
	public JoinListener(Plugin plugin){
		this.plugin=plugin;
	}
	
	@EventHandler
	private boolean onJoin(PlayerJoinEvent event) {
		Player p = (Player)event.getPlayer();
		if(!Basics.config.getList("Basics.Changelog.Player").contains(p.getName())){
			@SuppressWarnings("unchecked")
			List<String> l = (List<String>) Basics.config.getList("Basics.Changelog.Player");
			l.add(p.getName());
			Basics.config.set("Basics.Changelog.Player", l);
			Basics.save();
			p.sendMessage(Basics.config.getString("Basics.Changelog.Message"));
		}
		return true;
	}
}
