package com.github.miniminelp.basics.home;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.github.miniminelp.basics.core.Basics;
import com.github.miniminelp.basics.core.Formatting;
import com.github.miniminelp.basics.core.object;


public class Home implements object {
	
	File homesYML=new File("plugins/Basics/saves", "homes.yml");
	FileConfiguration homes=YamlConfiguration.loadConfiguration(homesYML);
	
	@SuppressWarnings("unused")
	private Basics plugin;
	
	@Override
	public void onEnable(Basics plugin) {
		this.plugin=plugin;
		System.out.println("Successfully loaded home System");
	}

	@Override
	public void onDisable(Basics plugin) {
		this.plugin=plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		
		//sethome
		if(label.equalsIgnoreCase("sethome")){
			if(sender instanceof Player){
				Player p=(Player)sender;
				if(p.hasPermission("basics.home.home")||p.hasPermission("basics.home.multihomes")){
					
					String world=p.getWorld().getName();
					
					double playerPosX=p.getLocation().getX();
					double playerPosY=p.getLocation().getY();
					double playerPosZ=p.getLocation().getZ();
					
					double yaw=p.getLocation().getYaw();
					double pitch=p.getLocation().getPitch();
					if(args.length>1){
						p.sendMessage(Formatting.COLOR.YELLOW+"Warning: There are unused Arguments in your Command!"+Formatting.RESET);
						return false;
					}
				
					if(args.length>0){
						if(p.hasPermission("basics.home.multihomes")){
							homes.set(p.getName()+"."+args[0]+".world", world);
							homes.set(p.getName()+"."+args[0]+".coordinates.X", playerPosX);
							homes.set(p.getName()+"."+args[0]+".coordinates.Y", playerPosY);
							homes.set(p.getName()+"."+args[0]+".coordinates.Z", playerPosZ);
							homes.set(p.getName()+"."+args[0]+".Yaw", yaw);
							homes.set(p.getName()+"."+args[0]+".Pitch", pitch);
						}else{
							p.sendMessage(Formatting.COLOR.RED+"You aren't permitted to have more then one home!"+Formatting.RESET);
						}
					}else{
						homes.set(p.getName()+".standart.world", world);
						homes.set(p.getName()+".standart.coordinates.X", playerPosX);
						homes.set(p.getName()+".standart.coordinates.Y", playerPosY);
						homes.set(p.getName()+".standart.coordinates.Z", playerPosZ);
						homes.set(p.getName()+".standart.Yaw", yaw);
						homes.set(p.getName()+".standart.Pitch", pitch);
					}
					if(!save())p.sendMessage(Formatting.COLOR.RED+"Exception occurupted while saving home.yml"+Formatting.RESET);
					else if(args.length>0)p.sendMessage(Formatting.COLOR.YELLOW+"Your Home \""+args[0]+"\" has been set to X: "+playerPosX+", Y: "+playerPosY+", Z: "+playerPosZ+" in thi world \""+world+"\""+Formatting.RESET);
					else p.sendMessage(Formatting.COLOR.YELLOW+"Your Home has been set to X: "+playerPosX+", Y: "+playerPosY+", Z: "+playerPosZ+" in thi world \""+world+"\""+Formatting.RESET);
				}else p.sendMessage(Formatting.COLOR.RED+"You havent the Permission to perform this Command!"+Formatting.RESET);
			}
		}
		
		
		//home
		if(label.equalsIgnoreCase("home")){
			if(sender instanceof Player){
				Player p=(Player)sender;
				if(p.hasPermission("basics.home.home")||p.hasPermission("basics.home.multihomes")){
					if(args.length>1){
						p.sendMessage(Formatting.COLOR.YELLOW+"Warning: There are unused Arguments in your Command!"+Formatting.RESET);
						return false;
					}
					if(args.length>0){
						if(p.hasPermission("basics.home.multihomes")){
							if(homes.contains(p.getName()+"."+args[0])){
								String world=homes.getString(p.getName()+"."+args[0]+".world");
			
								double x=homes.getDouble(p.getName()+"."+args[0]+".coordinates.X");
								double y=homes.getDouble(p.getName()+"."+args[0]+".coordinates.Y");
								double z=homes.getDouble(p.getName()+"."+args[0]+".coordinates.Z");
								
								double yaw=homes.getDouble(p.getName()+"."+args[0]+".Yaw");
								double pitch=homes.getDouble(p.getName()+"."+args[0]+".Pitch");
								
								Location location = new Location(Bukkit.getWorld(world), x, y, z);
								location.setYaw((float) yaw);
								location.setPitch((float) pitch);
								
								p.teleport(location);
							}else{
								p.sendMessage(Formatting.COLOR.RED+"This home does not exist! Please watch at the case!"+Formatting.RESET);
							}
						}else{
							p.sendMessage(Formatting.COLOR.RED+"You aren't permitted to have more then one home!"+Formatting.RESET);
						}
					}else{
						if(homes.contains(p.getName()+".standart")){
							String world=homes.getString(p.getName()+".standart.world");
		
							double x=homes.getDouble(p.getName()+".standart.coordinates.X");
							double y=homes.getDouble(p.getName()+".standart.coordinates.Y");
							double z=homes.getDouble(p.getName()+".standart.coordinates.Z");
							
							double yaw=homes.getDouble(p.getName()+".standart.Yaw");
							double pitch=homes.getDouble(p.getName()+".standart.Pitch");
							
							Location location = new Location(Bukkit.getWorld(world), x, y, z);
							location.setYaw((float) yaw);
							location.setPitch((float) pitch);
							
							p.teleport(location);
						}else{
							p.sendMessage(Formatting.COLOR.RED+"You havent got a home!"+Formatting.RESET);
						}
					}
				}
			}
		}
		return true;
	}
	
	public boolean save(){
		try {
			homes.save(homesYML);
			return true;
		} catch (IOException e) {
			System.err.println("Exception wurde beim abspeichern der Homes erzeugt: ");
			e.printStackTrace();
			return false;
		}
	}
}
