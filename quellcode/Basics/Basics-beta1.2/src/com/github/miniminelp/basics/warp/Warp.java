package com.github.miniminelp.basics.warp;

import java.util.LinkedList;
import java.util.List;
import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.miniminelp.basics.core.Basics;
import com.github.miniminelp.basics.core.Formatting;
import com.github.miniminelp.basics.core.object;


public class Warp implements object{
	
	JavaPlugin plugin;
	
	File warpsYML=new File("plugins/Basics/saves", "warps.yml");
	FileConfiguration warps=YamlConfiguration.loadConfiguration(warpsYML);
	
	
	@Override
	public void onEnable(Basics plugin) {
		if(!Basics.config.contains("Basics.Warp.Foodusage"))Basics.config.set("Basics.Warp.Foodusage", 6);
		Basics.save();
		save();
		this.plugin=plugin;
	}
	
	@Override
	public void onDisable(Basics plugin) {
		this.plugin=plugin;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		
			
		if(label.equalsIgnoreCase("warp")||label.equalsIgnoreCase("warps")){
			
			if(sender instanceof Player){
				Player p = (Player) sender;
				if(args.length>0){
					if(warps.contains("WARPS."+args[0])){
						if(p.getFoodLevel()>=Basics.config.getInt("Basics.Warp.Foodusage")||p.getGameMode().equals(GameMode.CREATIVE)||p.getGameMode().equals(GameMode.SPECTATOR)){
							String world = warps.getString("WARPS."+args[0]+".world");
							
							double x = warps.getDouble("WARPS."+args[0]+".posX");
							double y = warps.getDouble("WARPS."+args[0]+".posY");
							double z = warps.getDouble("WARPS."+args[0]+".posZ");
							
							double yaw = warps.getDouble("WARPS."+args[0]+".yaw");
							double pitch = warps.getDouble("WARPS."+args[0]+".pitch");
							
							Location location = new Location(Bukkit.getWorld(world), x, y, z);
							location.setYaw((float) yaw);
							location.setPitch((float) pitch);
							
							if(!p.getGameMode().equals(GameMode.CREATIVE)&&!p.getGameMode().equals(GameMode.SPECTATOR))p.setFoodLevel((int) (p.getFoodLevel()-Basics.config.getInt("Basics.Warp.Foodusage")));
							
							p.teleport(location);
							
							
							p.sendMessage(Formatting.COLOR.GREEN+"Warped successfully to the Warp \""+args[0]+"\"!"+Formatting.RESET);
						}else{
							p.sendMessage(Formatting.COLOR.RED+"You are too hungry to Warp!"+Formatting.RESET);
						}
					}else{
						p.sendMessage(Formatting.COLOR.RED+"This Warp does not exist!"+Formatting.RESET);
					}
				}else{
					List<String> warplist = (List<String>) warps.getList("WARPLIST");
					String message="The following warps are available:";
					for(int i=0;i<warplist.size();i++){
						message+="\n - "+warplist.get(i);
					}
					p.sendMessage(message);
				}
			}
			else{
				sender.sendMessage("This command is just for Players");
			}
			
		}
		
		
		if(label.equalsIgnoreCase("setwarp")){
			
			if(sender instanceof Player){
				Player p = (Player) sender;
				
				if(p.hasPermission("Basics.warp.admin")){
					if(args.length>0){
						if(!warps.contains("WARPS."+args[0])){
							
							String world = p.getLocation().getWorld().getName();
							
							double posX=p.getLocation().getX();
							double posY=p.getLocation().getY();
							double posZ=p.getLocation().getZ();
							
							double pitch=p.getLocation().getPitch();
							double yaw=p.getLocation().getYaw();
							
							warps.set("WARPS."+args[0]+".world", world);
							warps.set("WARPS."+args[0]+".posX", posX);
							warps.set("WARPS."+args[0]+".posY", posY);
							warps.set("WARPS."+args[0]+".posZ", posZ);
							warps.set("WARPS."+args[0]+".pitch", pitch);
							warps.set("WARPS."+args[0]+".yaw", yaw);
							
							if(warps.contains("WARPLIST")){
								List l = (List) warps.get("WARPLIST");
								l.add(args[0]);
								warps.set("WARPLIST", l);
							}else{
								List l = new LinkedList();
								l.add(args[0]);
								warps.set("WARPLIST", l);
							}
							
							p.sendMessage(Formatting.COLOR.GREEN+"The Warp \""+args[0]+"\" has been successfully set to X: "+(int)posX+", Y: "+(int)posY+", Z: "+(int)posZ+"!"+Formatting.RESET);
							save();
						}else{
							p.sendMessage(Formatting.COLOR.RED+"This warp already exists!"+Formatting.RESET);
						}
					}
				}else{
					p.sendMessage(Formatting.COLOR.RED+"Bitte define a Name for Your Warp!"+Formatting.RESET);
					return false;
				}
			}
			else{
				sender.sendMessage("This command is just for Players");
			}
			
		}
		
		if(label.equalsIgnoreCase("delwarp")){
			
			if(sender instanceof Player){
				
				Player p=(Player)sender;
				
				if(p.hasPermission("basics.warp.admin")){
					
					if(args.length>0){
						
						if(warps.contains("WARPLIST")){
							List<String> existingWarps = (List<String>) warps.getList("WARPLIST");
							boolean b=false;
							for(int i=0;i<existingWarps.size();i++){
								if(existingWarps.get(i).equals(args[0])){
									existingWarps.remove(i);
									b=true;
								}
							}
							if(b){
								warps.set("WARPS."+args[0], null);
								save();
								p.sendMessage(Formatting.COLOR.GREEN+"Warp \""+args[0]+"\" successfully deleted"+Formatting.RESET);
							}else{
								p.sendMessage(Formatting.COLOR.RED+"This warp does not exist!"+Formatting.RESET);
							}
							
						}else{
							p.sendMessage(Formatting.COLOR.RED+"No warps are Existing!"+Formatting.RESET);
						}
						
					}else{
						p.sendMessage(Formatting.COLOR.RED+"Please enter at least one argument!"+Formatting.RESET);
					}
					
				}else{
					p.sendMessage(Formatting.COLOR.RED+"You haven't got the Permission for this Command!"+Formatting.RESET);
				}
				
			}else{
				sender.sendMessage("You must be a Player to execute this Command!");
			}
			
		}
		return true;
	}
	
	public boolean save(){
		try {
			warps.save(warpsYML);
			return true;
		} catch (IOException e) {
			System.err.println("Exception wurde beim abspeichern der Warps erzeugt: ");
			e.printStackTrace();
			return false;
		}
	}
}
