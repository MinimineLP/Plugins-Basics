package com.github.miniminelp.basics.ban;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import com.github.miniminelp.basics.util.Bantypes;


class JoinListener implements Listener{
	
	@SuppressWarnings("unused")
	private Plugin plugin;
	
	public JoinListener(Plugin plugin){
		this.plugin=plugin;
	}
	
	@EventHandler
	private boolean onJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		if(Bans.isPlayerBanned(p)){
			e.setJoinMessage("");
			if(Bans.getBanType(p)==Bantypes.TEMP_BANNED)e.getPlayer().kickPlayer("§3You have been banned by §r"+Bans.getBanner(p)+"\n§3reason: §r"+Bans.getReason(p)+"\n§3You will be unbanned in "+Bans.getFormattedBannTime(p)+" seconds§r!");
			else e.getPlayer().kickPlayer("§3You have been banned by §r"+Bans.getBanner(p)+"\n§3reason: §r"+Bans.getReason(p).replace("&", "§"));
		}
		return false;
	}
	
	
}
