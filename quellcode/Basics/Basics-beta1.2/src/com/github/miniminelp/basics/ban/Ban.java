package com.github.miniminelp.basics.ban;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.miniminelp.basics.core.Basics;
import com.github.miniminelp.basics.core.Formatting;
import com.github.miniminelp.basics.core.Timemanager;
import com.github.miniminelp.basics.core.object;

public class Ban implements object {
	
	private Basics plugin;
	
	@Override
	public void onEnable(Basics plugin) {
		Bukkit.getPluginManager().registerEvents(new JoinListener(plugin), plugin);
		this.plugin=plugin;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		//Ban
		if(label.equalsIgnoreCase("ban")){
			if(sender.hasPermission("basics.ban.ban")){
				
				if(args.length>0){
					UUID uuid;
					OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
					if (op.hasPlayedBefore()) {
					    args[0] = op.getUniqueId().toString();
					    uuid=op.getUniqueId();
					} else {
						sender.sendMessage("Player hasn't ever played on this Server, you can't ban him!");
					    return true;
					}
					
					String reason="";
					for(int i=1;i<args.length;i++){
						reason+=args[i]+" ";
					}
					if(reason.equalsIgnoreCase(""))reason="none";
					else reason = reason.substring(0, reason.length() - 1);
					String banner="";
					if(sender instanceof Player)banner=sender.getName();
					else banner="§4CONSOLE§r";
					
					
					
					Bans.bans.set("BANS."+args[0].toLowerCase()+".reason", reason);
					Bans.bans.set("BANS."+args[0].toLowerCase()+".banner", banner);
					Bans.bans.set("BANS."+args[0].toLowerCase()+".time", "LIFETIME");
					Bans.save();
					
					try{
						Player banned=plugin.getServer().getPlayer(uuid);
						banned.kickPlayer("§3You have been banned by §r"+banner+"\n§3reason: §r"+reason.replace("&", "§"));
					} catch (Exception e) {
						sender.sendMessage("Player \""+args[0]+" is not online, he will also be banned!");
					}
					
					
					sender.sendMessage("Player "+args[0]+" has been banned from the Server for the Reason \""+reason.replace("&", "§")+"\"!");
				}else{
					return false;
				}
			}else{
				if(sender instanceof Player)sender.sendMessage(Formatting.COLOR.RED+"You havent the Permission to do that!"+Formatting.RESET);
				else sender.sendMessage("You havent the Permission to do that!");
			}
		}
		
		//Tempban
		if(label.equalsIgnoreCase("tempban")){
			if(sender.hasPermission("Basics.ban.tempban")){
				
				if(args.length>1){
					UUID uuid;
					OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
					if (op.hasPlayedBefore()) {
					    args[0] = op.getUniqueId().toString();
					    uuid=op.getUniqueId();
					} else {
						sender.sendMessage("Player hasn't ever played on this Server, you can't ban him!");
					    return true;
					}
					
					int time=Timemanager.dateFormat(args[1], sender);
					
					if(time!=0){
						String reason="";
						for(int i=2;i<args.length;i++){
							reason+=args[i]+" ";
						}
						
						
						
						if(reason.equalsIgnoreCase(""))reason="none";
						
						String banner="";
						if(sender instanceof Player)banner=sender.getName();
						else banner="§4CONSOLE§r";
						if(Bans.isPlayerBanned(args[0])&&Bans.getBannTime(args[0])==-1)
							sender.sendMessage("This player is already banned for LIFETIME");
						else if(Bans.isPlayerBanned(args[0])&&Bans.getBannTime(args[0])>Timemanager.getTime()+time)
							sender.sendMessage("This player is already banned for a longer time");
						else{
							Bans.bans.set("BANS."+args[0].toLowerCase()+".reason", reason);
							Bans.bans.set("BANS."+args[0].toLowerCase()+".banner", banner);
							Bans.bans.set("BANS."+args[0].toLowerCase()+".time", Timemanager.getTime()+time);
							
							Bans.save();
							
							
							try {
								Player banned=plugin.getServer().getPlayer(uuid);
								banned.kickPlayer("§3You have been banned by §r"+Bans.getBanner(args[0])+"\n§3reason: §r"+Bans.getReason(args[0])+"\n§3You will be unbanned in "+Bans.getFormattedBannTime(args[0])+" secounds§r!");
							} catch (Exception e) {
								sender.sendMessage("Player \""+args[0]+" is not online, he will also be banned!");
							}
							
							sender.sendMessage("Player "+args[0]+" has been banned from the Server for the Reason \""+reason.replace("&", "§")+"\"!");
						}
					}
				}else{
					return false;
				}
			}else{
				if(sender instanceof Player)sender.sendMessage(Formatting.COLOR.RED+"You havent the Permission to do that!"+Formatting.RESET);
				else sender.sendMessage("You havent the Permission to do that!");
			}
		}
		
		//Unban
		if(label.equalsIgnoreCase("unban")||label.equalsIgnoreCase("pardon")){
			if(sender.hasPermission("Basics.ban.unban")){
				if(args.length>0){
					OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
					if (op.hasPlayedBefore()) {
					    args[0] = op.getUniqueId().toString();
					} else {
						sender.sendMessage("Player hasn't ever played on this Server, you can't unban him!");
					    return true;
					}
					if(Bans.bans.contains("BANS."+args[0].toLowerCase())){
						Bans.bans.set("BANS."+args[0].toLowerCase()+".time", Timemanager.getTime());
						Bans.save();
						sender.sendMessage("You've unbanned Player "+args[0]);
					}else{
						sender.sendMessage("This Player is not banned!");
					}
				}else{
					return false;
				}
			}
		}
		
		//GET IP
		if(label.equalsIgnoreCase("ip")){
			if(sender.hasPermission("Basics.ip")){
				if(args.length>0){
					sender.sendMessage(plugin.getServer().getPlayer(args[0]).getAddress().getHostName());
				}else{
					return false;
				}
			}else{
				sender.sendMessage("No Permission");
			}
		}
		//IP-Ban
		if(label.equalsIgnoreCase("ip-ban")||label.equalsIgnoreCase("ipban")){
			if(sender.hasPermission("Basics.ban.ipban")){
				
				if(args.length>0){
					
					String reason="";
					for(int i=1;i<args.length;i++){
						reason+=args[i]+" ";
					}
					
					if(reason.equalsIgnoreCase(""))reason="none";
					
					String banner="";
					if(sender instanceof Player)banner=sender.getName();
					else banner="§4CONSOLE§r";
					
					Bans.bans.set("IPBANS."+args[0].toLowerCase()+".reason", reason);
					Bans.bans.set("IPBANS."+args[0].toLowerCase()+".banner", banner);
					Bans.bans.set("IPBANS."+args[0].toLowerCase()+".time", "LIFETIME");
					Bans.save();
					
					
					
					try{
						Player[] players = (Player[]) Bukkit.getServer().getOnlinePlayers().toArray();
						for(int i=0;i<players.length;i++){
							if(players[i].getAddress().getHostName().equalsIgnoreCase(args[0])){
								players[i].kickPlayer("§3You have been IP-Banned by §r"+banner+"\n§3reason: §r"+reason.replace("&", "§"));
							}
						}
						//banned.kickPlayer("§3You have been banned by §r"+banner+"\n§3reason: §r"+reason.replace("&", "§"));
					} catch (Exception e) {
						sender.sendMessage("Error occurred!");
					}
					
					
					sender.sendMessage("Player "+args[0]+" has been banned from the Server for the Reason \""+reason.replace("&", "§")+"\"!");
				}else{
					return false;
				}
			}else{
				if(sender instanceof Player)sender.sendMessage(Formatting.COLOR.RED+"You havent the Permission to do that!"+Formatting.RESET);
				else sender.sendMessage("You havent the Permission to do that!");
			}
		}
		return true;
	}
	
	
	
}
