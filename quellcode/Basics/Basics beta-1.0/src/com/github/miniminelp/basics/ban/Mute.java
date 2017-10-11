package com.github.miniminelp.basics.ban;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.miniminelp.basics.core.Basics;
import com.github.miniminelp.basics.core.Formatting;
import com.github.miniminelp.basics.core.Timemanager;
import com.github.miniminelp.basics.core.object;
import com.github.miniminelp.basics.util.Mutetypes;

public class Mute implements object {
	
	private Basics plugin;
	
	@Override
	
	public void onEnable(Basics plugin) {
		
		
		Bukkit.getPluginManager().registerEvents(new ChatListener(plugin), plugin);
		this.plugin=plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(label.equalsIgnoreCase("mute")){
			if(sender.hasPermission("basics.ban.mute")){
				if(args.length>0){
					String reason="";
					for(int i=1;i<args.length;i++){
						reason+=args[i]+" ";
					}
					
					if(reason.equalsIgnoreCase(""))reason="none";
					
					String muter="";
					if(sender instanceof Player)muter=sender.getName();
					else muter="§4CONSOLE§r";
					
					Mutes.mutes.set("MUTES.DONT_DELEAT_ME_FROM_MUTELIST", "MUTED");
					
					Mutes.mutes.set("MUTES."+args[0].toLowerCase()+".reason", reason);
					Mutes.mutes.set("MUTES."+args[0].toLowerCase()+".muter", muter);
					Mutes.mutes.set("MUTES."+args[0].toLowerCase()+".time", "LIFETIME");
					
					Mutes.save();
					
					try{
						plugin.getServer().getPlayer(args[0]).sendMessage("You've been muted!");
					}catch (Exception e) {
					}
					sender.sendMessage("Player "+args[0]+" has been muted!");
				}else{
					return false;
				}
			}else{
				sender.sendMessage("You haven't got the Permission for this!");
			}
		}
		
		//Tempmute
		if(label.equalsIgnoreCase("tempmute")){
			if(sender.hasPermission("Basics.ban.tempban")){
				
				if(args.length>1){
					
					int time=Timemanager.dateFormat(args[1], sender);
					
					if(time!=0){
						String reason="";
						for(int i=2;i<args.length;i++){
							reason+=args[i]+" ";
						}
						
						if(reason.equalsIgnoreCase(""))reason="none";
						
						String muter="";
						if(sender instanceof Player)muter=sender.getName();
						else muter="§4CONSOLE§r";
						
						if(Bans.isPlayerBanned(args[0])&&Mutes.getMuteTime(args[0])==-1)
							sender.sendMessage("This player is already muted for LIFETIME");
						
						else if(Mutes.isPlayerMuted(args[0])&&Mutes.getMuteTime(args[0])>Timemanager.getTime()+time)
							sender.sendMessage("This player is already muted for a longer time");
						
						else{
							Mutes.mutes.set("MUTES."+args[0].toLowerCase()+".reason", reason);
							Mutes.mutes.set("MUTES."+args[0].toLowerCase()+".muter", muter);
							Mutes.mutes.set("MUTES."+args[0].toLowerCase()+".time", Timemanager.getTime()+time);
							
							Mutes.save();
							try{
								plugin.getServer().getPlayer(args[0]).sendMessage("You've been muted for "+Mutes.getFormattedMuteTime(args[0])+"!");
							}catch (Exception e) {
							}
							sender.sendMessage("Player "+args[0]+" has been muted for the Reason \""+reason.replace("&", "§")+"\"!");
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
		
		//Unmute
		if(label.equalsIgnoreCase("unmute")){
			if(sender.hasPermission("Basics.ban.unmute")){
				if(args.length>0){
					if(Mutes.mutes.contains("MUTES."+args[0].toLowerCase())){
						Mutes.mutes.set("MUTES."+args[0].toLowerCase(), null);
						Mutes.save();
						sender.sendMessage("You've unmuted Player "+args[0]);
						
						try{
							plugin.getServer().getPlayer(args[0]).sendMessage("You've been unmuted");;
						}catch (Exception e) {
						}
					}else{
						sender.sendMessage("This Player is not muted!");
					}
				}else{
					return false;
				}
			}
		}
		
		//Muteinfo
		if(label.equalsIgnoreCase("muteinfo")||label.equalsIgnoreCase("mute-info")){
				if(args.length==0){
				if(sender instanceof Player){
					Player p=(Player)sender;
					if(Mutes.isPlayerMuted(p)){
						sender.sendMessage(Formatting.COLOR.YELLOW+" \n----------------------------------------------------\n "+Formatting.RESET);
						sender.sendMessage(Formatting.COLOR.YELLOW+"  - Muter: "+Mutes.getMuter(p)+Formatting.RESET);
						sender.sendMessage(Formatting.COLOR.YELLOW+"  - Reason: "+Mutes.getReason(p)+Formatting.RESET);
						if(Mutes.getMuteType(p)==Mutetypes.TEMP_MUTED)sender.sendMessage(Formatting.COLOR.YELLOW+"  - Remaining time: "+Mutes.getFormattedMuteTime(p)+Formatting.RESET);
						else sender.sendMessage(Formatting.COLOR.YELLOW+"  - Remaining time: You are LIFETIME-MUTED!");
						sender.sendMessage(Formatting.COLOR.YELLOW+" \n----------------------------------------------------\n "+Formatting.RESET);
					}else{
						sender.sendMessage("You are not muted!");
					}
				}
			}else{
				sender.sendMessage("No permission");
			}
		}
		return true;
	}

}
