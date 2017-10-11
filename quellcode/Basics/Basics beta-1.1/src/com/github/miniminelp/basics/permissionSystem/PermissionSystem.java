package com.github.miniminelp.basics.permissionSystem;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import com.github.miniminelp.basics.core.Basics;
import com.github.miniminelp.basics.core.Formatting;
import com.github.miniminelp.basics.core.object;

public class PermissionSystem implements object{
	
	static Basics plugin;
	
	static File permissionsYML=new File("plugins/Basics/saves", "permissions.yml");
	static FileConfiguration permissions=YamlConfiguration.loadConfiguration(permissionsYML);
	
	static List<UUID> toDeop=new LinkedList<UUID>();
	static File toDeopYML=new File("plugins/Basics/saves", "toDeop.yml");
	static FileConfiguration tdo=YamlConfiguration.loadConfiguration(toDeopYML);
	
	public static String defaultgroup = null;
	
	boolean error=false;
	
	@SuppressWarnings("static-access")
	@Override
	public void onEnable(Basics plugin) {
		this.plugin=plugin;
		Bukkit.getPluginManager().registerEvents(new JoinListener(plugin), plugin);
		Bukkit.getPluginManager().registerEvents(new onCommand(plugin), plugin);
		
		if(!permissions.contains("groups")){
			
			List<String> defaultpermissions = new LinkedList<String>();
			
			defaultpermissions.add("modifyworld.*");
			
			permissions.set("groups.default.options.default", true);
			permissions.set("groups.default.permissions", defaultpermissions);
			
			save();
			System.out.println("Successfully loaded Permissins!");
		}
		
		Set<String> groups = permissions.getConfigurationSection("groups").getKeys(true);
		
		for(String s : groups){
			if(permissions.getBoolean("groups."+s+".options.default")){
				defaultgroup=s;
			}
		}
		
		if(defaultgroup==null){
			error=true;
			System.err.println("The Permission System can't run because an Error in the Config: No Default Group");
		}

		enable();
	}
	@Override
	public void onDisable(Basics plugin) {
		disable();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("permission")){
			if(args.length>0){
				if(!(sender instanceof BlockCommandSender)&&sender.hasPermission("basics.permissions.admin")){
					switch (args[0]) {
					//Reload
					case "reload":
						reload();
						break;
					
					case "rl":
						reload();
						break;
					
						
					//Group
					case "group":
						if(!(args.length>3))sender.sendMessage(Formatting.COLOR.RED+"Wrong Synthax"+Formatting.RESET);
						else{
							switch (args[2]) {
							case "add":
								addGroupPermission(args[1], args[3]);
								sender.sendMessage("Please type /permission reload to load the edits!");
								break;
							case "remove":
								removeGroupPermission(args[1], args[3]);
								sender.sendMessage("Please type /permission reload to load the edits!");
								break;

							default:
								sender.sendMessage(Formatting.COLOR.RED+"Wrong Synthax"+Formatting.RESET);
								break;
							}
						}
						break;
					
					
					//user
					case "user":
						if(!(args.length>2))sender.sendMessage(Formatting.COLOR.RED+"Wrong Synthax"+Formatting.RESET);
						
						else{
							Player p = plugin.getServer().getPlayer(args[1]);
							//String player=args[1];
							switch (args[2]) {
							/*case "set":
								if(!(args.length>3))sender.sendMessage(Formatting.COLOR.RED+"Wrong Synthax"+Formatting.RESET);
								else{
									String val = "";
									for(int i=4;i<args.length;i++){
										val+=args[i]+" ";
									}
									//val=val.substring(0, val.length()-1);
									setPlayerOption(p.getUniqueId().toString().toLowerCase(), args[3], val);
								}
								break;*/
							case "add":
								
								if(!(args.length>3))sender.sendMessage(Formatting.COLOR.RED+"Wrong Synthax"+Formatting.RESET);
								else 
								try{
									setPermission(p.getUniqueId(), args[3], true);
									sender.sendMessage("Permission "+args[3]+" added");
								}catch (Exception e) {
									System.out.println("Player not online!");
								}
								break;
								
							case "remove":
								
								if(!(args.length>3))sender.sendMessage(Formatting.COLOR.RED+"Wrong Synthax"+Formatting.RESET);
								else 
								try{
									setPermission(p.getUniqueId(), args[3], false);
									sender.sendMessage("Permission "+args[3]+" removed");
								}catch (Exception e) {
									System.out.println("Player not online!");
								}
								break;
							case "group":
								
								if(!(args.length>4))sender.sendMessage(Formatting.COLOR.RED+"Wrong Synthax"+Formatting.RESET);
								else{
									switch (args[3]) {
									case "set":
										try {
											setPlayerGroup(args[4], p.getUniqueId().toString().toLowerCase());
											p.kickPlayer(Formatting.COLOR.GREEN+"Your Permissions has been updated"+Formatting.RESET);
											sender.sendMessage(p.getName()+"'s group has been setted to "+args[4]);
										} catch (Exception e) {
											sender.sendMessage(Formatting.COLOR.RED+"Player not online"+Formatting.RESET);
										}
										break;
									case "add":
										try {
											addPlayerToGroup(args[4], p.getUniqueId().toString().toLowerCase());
											sender.sendMessage("Player "+p.getName()+" has been added to the group "+args[4]);
										} catch (Exception e) {
											sender.sendMessage(Formatting.COLOR.RED+"Player not online"+Formatting.RESET);
										}
										break;
									case "remove":
										addPlayerToGroup(args[4], p.getUniqueId().toString().toLowerCase());
										break;
									default:
										sender.sendMessage(Formatting.COLOR.RED+"Wrong Synthax"+Formatting.RESET);
										break;
									}
								}
								
								break;
							default:
								sender.sendMessage(Formatting.COLOR.RED+"Wrong Synthax"+Formatting.RESET);
								break;
							}
						}
						break;
					default:
						sender.sendMessage(Formatting.COLOR.RED+"Wrong Synthax"+Formatting.RESET);
						break;
					}
				}else{
					sender.sendMessage(Formatting.COLOR.RED+"No Permission"+Formatting.RESET);
				}
			}else{
				sender.sendMessage("Permissionsystem added by Basics");
			}
		}
		return true;
	}
	
	public static boolean save(){
		try {
			permissions.save(permissionsYML);
			return true;
		} catch (IOException e) {
			System.err.println("Exception occured while saving permissions: ");
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean saveToDeop(){
		try {
			tdo.set("todeop", toDeop);
			tdo.save(toDeopYML);
			return true;
		} catch (IOException e) {
			System.err.println("Exception occured while saving permissions: ");
			e.printStackTrace();
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> getPlayerPermissions(String uuid){
		if(permissions.contains("users."+uuid.toLowerCase()+".permissions")){
			return (List<String>) permissions.getList("users."+uuid.toLowerCase()+".permissions");
		}
		return new LinkedList<String>();
	}
	
	public static boolean hasPlayerPermission(String uuid){
		if(permissions.contains("users."+uuid.toLowerCase()+".permissions")){
			List<String> perm = getPlayerPermissions(uuid);
			if(perm.size()>0)return true;
		}
		return false;
	}
	
	public static boolean hasPlayerPermission(String uuid, String permission){
		if(hasPlayerPermission(uuid)){
			if(getPlayerPermissions(uuid).contains(permission.toLowerCase()))return true;
		}
		return false;
	}
	
	public static boolean setPermission(UUID uuid, String permission, boolean b){
		boolean ret=false;
		List<String> perms = getPlayerPermissions(uuid.toString());
		if(!hasPlayerPermission(uuid.toString(), permission)&&b){
			System.out.println("hi");
			perms.add(permission);
			ret=true;
		}else if(hasPlayerPermission(uuid.toString(), permission)&&!b){
			perms.remove(permission);
			ret=true;
		}
		permissions.set("users."+uuid.toString().toLowerCase()+".permissions", perms);
		save();
		try{
			setPlayerPermission(plugin.getServer().getPlayer(uuid).getName(), permission, b);
		}catch (Exception e) {
		}
		return ret;
	}
	
	public static boolean givePlayerHisPermissions(Player player){
		boolean b=true;
		List<String> permissions=getPlayerPermissions(player.getUniqueId().toString().toLowerCase());
		if(permissions!=null){
			for(int i=0;i<permissions.size();i++){
				if(!addPlayerPermission(player.getName().toLowerCase(), permissions.get(i)))b=false;;
			}
		}else{
			b=false;
		}
		return b;
	}
	public static boolean existsGroup(String group){
		if(permissions.contains("groups."+group))return true;
		return false;
	}
	@SuppressWarnings("unchecked")
	public static List<String> getGroupPermissions(String group){
		if(existsGroup(group)){
			if(permissions.contains("groups."+group+".permissions"))return (List<String>) permissions.getList("groups."+group+".permissions");
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean hasPlayerGroup(String uuid){
		if(permissions.contains("users."+uuid.toLowerCase()+".group")){
			List<String> groups = (List<String>) permissions.getList("users."+uuid.toLowerCase()+".group");
			if(groups.size()>0)return true;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean isPlayerInGroup(String group, String uuid){
		if(hasPlayerGroup(uuid)){
			List<String> groups = (List<String>) permissions.getList("users."+uuid.toLowerCase()+".group");
			if(groups.contains(group))return true;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> getPlayerGroups(String uuid){
		if(hasPlayerGroup(uuid)){
			return (List<String>) permissions.getList("users."+uuid.toLowerCase()+".group");
		}
		return null;
	}
	
	public static boolean setPlayerGroup(String group, String uuid){
		uuid=uuid.toLowerCase();
		
		List<String> groups = new LinkedList<String>();
		groups.add(group);
		permissions.set("users."+uuid+".group", groups);
		return save();
	}
	
	@SuppressWarnings("unchecked")
	public static boolean addPlayerToGroup(String group, String uuid){
		uuid=uuid.toLowerCase();
		
		if(!isPlayerInGroup(group, uuid)){
			
			if(hasPlayerGroup(uuid)){
				
				List<String> groups = (List<String>) permissions.getList("users."+uuid.toLowerCase()+".group");
				groups.add(group);
				permissions.set("users."+uuid+".group", groups);
				
			}else{
				
				List<String> groups = new LinkedList<String>();
				groups.add(group);
				permissions.set("users."+uuid+".group", groups);
				
			}
			
			return save();
			
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean removePlayerFromGroup(String group, String uuid){
		uuid=uuid.toLowerCase();
		
		if(isPlayerInGroup(group, uuid)){
			List<String> groups = (List<String>) permissions.getList("users."+uuid.toLowerCase()+".group");
			groups.remove(group);
			permissions.set("users."+uuid+".group", groups);
			return save();
		}
		return false;
	}
	
	public static boolean addPlayerToDefaultGroup(String uuid){
		return addPlayerToGroup(defaultgroup, uuid.toLowerCase());
	}
	
	public static boolean setPlayerOption(String uuid, String key, String value){
		permissions.set("users."+uuid.toLowerCase()+".options."+key.toLowerCase(), value);
		return save();
	}
	public static boolean setPlayerName(String uuid, String name){
		return setPlayerOption(uuid, "name", name);
	}
	public static boolean setPlayerPermission(String player, String permission, boolean b){
		try {
			Player p=plugin.getServer().getPlayer(player);
			PermissionAttachment attachment = p.addAttachment(plugin);
			attachment.setPermission(permission, b);
			plugin.getServer().getPlayer(player);
			if(permission.equalsIgnoreCase("*")){
				if(b){
					if(!p.isOp()){
						p.setOp(true);
						toDeop.add(p.getUniqueId());
						saveToDeop();
					}
				}else{
					if(toDeop.contains(p.getUniqueId())){
						p.setOp(false);
						toDeop.remove(p.getUniqueId());
						saveToDeop();
					}
				}
			}
			//System.out.println("Setted the Permission "+permission+" for the Player "+player +" to "+b);
		} catch (Exception e) {
			System.err.println("error");
			return false;
		}
		return true;
	}
	public static boolean addPlayerPermission(String player, String permission){
		return setPlayerPermission(player, permission, true);
	}
	public static boolean removePlayerPermission(String player, String permission){
		return setPlayerPermission(player, permission, false);
	}
	public static boolean givePlayerGroupPermission(String player, String group){
		boolean b=true;
		
		List<String> permissions=getGroupPermissions(group);
		if(permissions!=null){
			for(int i=0;i<permissions.size();i++){
				if(!addPlayerPermission(player, permissions.get(i)))b=false;;
			}
		}else{
			b=false;
		}
		return b;
	}
	public static boolean givePlayerHisGroupPermissions(String player){
		player=player.toLowerCase();
		try{
			List<String> groups = getPlayerGroups(plugin.getServer().getPlayer(player).getUniqueId().toString());
			for(int i=0;i<groups.size();i++){
				givePlayerGroupPermission(player, groups.get(i));
			}
		}catch (Exception e) {
			return false;
		}
		return true;
	}
	public static void reload(){
		disable();
		enable();
	}
	public static void enable(){
		permissions=YamlConfiguration.loadConfiguration(permissionsYML);
	}
	public static boolean disable(){
		for(Player p : plugin.getServer().getOnlinePlayers()){
			p.kickPlayer(Formatting.COLOR.RED+"Permissions reload, please rejoin"+Formatting.RESET);
		}
		boolean ret=true;
		for(int i=0;i<toDeop.size();i++){
			try {
				plugin.getServer().getPlayer(toDeop.get(i)).setOp(false);
			} catch (Exception e) {
				System.out.println("Player "+toDeop.get(i).toString()+" is not longer online, but he has to be deoped!");
				ret=false;
			}
		}
		toDeop=new LinkedList<UUID>();
		saveToDeop();
		return ret;
	}
	
	public static boolean addGroupPermission(String group, String permission){
		List<String> permissionList=new LinkedList<String>();
		if(existsGroup(group))permissionList=getGroupPermissions(group);
		if(!permissionList.contains(permission))permissionList.add(permission);
		permissions.set("groups."+group+".permissions", permissionList);
		return true;
	}
	public static boolean removeGroupPermission(String group, String permission){
		List<String> permissionList=new LinkedList<String>();
		if(existsGroup(group))permissionList=getGroupPermissions(group);
		if(permissionList.contains(permission))permissionList.remove(permission);
		permissions.set("groups."+group+".permissions", permissionList);
		return true;
	}
}
