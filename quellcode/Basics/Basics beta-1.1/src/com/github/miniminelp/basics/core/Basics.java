package com.github.miniminelp.basics.core;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.miniminelp.basics.ban.Ban;
import com.github.miniminelp.basics.ban.Mute;
import com.github.miniminelp.basics.changelog.JoinListener;
import com.github.miniminelp.basics.feed.Feed;
import com.github.miniminelp.basics.gamemode.Gamemode;
import com.github.miniminelp.basics.heal.Heal;
import com.github.miniminelp.basics.home.Home;
import com.github.miniminelp.basics.monitor.Monitor;
import com.github.miniminelp.basics.op.Gop;
import com.github.miniminelp.basics.permissionSystem.PermissionSystem;
import com.github.miniminelp.basics.sudo.Sudo;
import com.github.miniminelp.basics.tpa.Tpa;
import com.github.miniminelp.basics.warp.Warp;

public class Basics extends JavaPlugin{
	
	List<object> objects=new LinkedList<object>();
	
	public static File configYML=new File("plugins/Basics/", "config.yml");
	public static FileConfiguration config=YamlConfiguration.loadConfiguration(configYML);
	
	@Override
	public void onEnable() {
		System.out.println("Starting Plugin Basics...");
		if(!config.contains("Basics.Warp.Foodusage"))config.set("Basics.Warp.Foodusage", 6);
		if(!config.contains("Basics.Changelog.Message"))config.set("Basics.Changelog.Message", "Neue updates des Plugin Basics installiert");
		if(!config.contains("Basics.Changelog.Player"))config.set("Basics.Changelog.Player", new LinkedList<String>());
		File mainfolder=new File("plugins/Basics");
		if(!mainfolder.exists())mainfolder.mkdirs();
		File savefolder=new File("plugins/Basics/saves");
		if(!savefolder.exists())savefolder.mkdirs();
		registerSubs();
		enableSubs();
		if(config.getBoolean("Basics.Modulmanager.Changelog"))Bukkit.getPluginManager().registerEvents(new JoinListener(this), this);
		save();
		System.out.println("Successfully started Plugin basics!");
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		System.out.println("Stopping Plugin Basics...");
		disableSubs();
		System.out.println("Successfully stopped Plugin basics!");
		super.onDisable();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("basics")){
			if(args.length>0){
				if(args[0].equalsIgnoreCase("reload")||args[0].equalsIgnoreCase("rl")){
					if(sender.hasPermission("basics.admin"))reload();
					else sender.sendMessage(Formatting.COLOR.RED+"No Permission");
				}
			}else sender.sendMessage("This Server uses "+getName());
		}
		return subCommands(sender, command, label, args);
	}
	
	private void registerSubs(){
		
		if(!config.contains("Basics.Modulmanager.Home"))config.set("Basics.Modulmanager.Home", true);
		if(!config.contains("Basics.Modulmanager.Gop"))config.set("Basics.Modulmanager.Gop", true);
		if(!config.contains("Basics.Modulmanager.Tpa"))config.set("Basics.Modulmanager.Tpa", true);
		if(!config.contains("Basics.Modulmanager.Warp"))config.set("Basics.Modulmanager.Warp", true);
		if(!config.contains("Basics.Modulmanager.Heal"))config.set("Basics.Modulmanager.Heal", true);
		if(!config.contains("Basics.Modulmanager.Feed"))config.set("Basics.Modulmanager.Feed", true);
		if(!config.contains("Basics.Modulmanager.Ban"))config.set("Basics.Modulmanager.Ban", true);
		if(!config.contains("Basics.Modulmanager.Mute"))config.set("Basics.Modulmanager.Mute", true);
		if(!config.contains("Basics.Modulmanager.PermissionSystem"))config.set("Basics.Modulmanager.PermissionSystem", true);
		if(!config.contains("Basics.Modulmanager.gm"))config.set("Basics.Modulmanager.gm", true);
		if(!config.contains("Basics.Modulmanager.monitor"))config.set("Basics.Modulmanager.monitor", true);
		if(!config.contains("Basics.Modulmanager.sudo"))config.set("Basics.Modulmanager.sudo", true);
		
		if(!config.contains("Basics.Modulmanager.Changelog"))config.set("Basics.Modulmanager.Changelog", true);
		
		if(config.getBoolean("Basics.Modulmanager.Home"))objects.add(new Home());
		if(config.getBoolean("Basics.Modulmanager.Gop"))objects.add(new Gop());
		if(config.getBoolean("Basics.Modulmanager.Tpa"))objects.add(new Tpa());
		if(config.getBoolean("Basics.Modulmanager.Warp"))objects.add(new Warp());
		if(config.getBoolean("Basics.Modulmanager.Heal"))objects.add(new Heal());
		if(config.getBoolean("Basics.Modulmanager.Feed"))objects.add(new Feed());
		if(config.getBoolean("Basics.Modulmanager.Ban"))objects.add(new Ban());
		if(config.getBoolean("Basics.Modulmanager.Mute"))objects.add(new Mute());
		if(config.getBoolean("Basics.Modulmanager.PermissionSystem"))objects.add(new PermissionSystem());
		if(config.getBoolean("Basics.Modulmanager.gm"))objects.add(new Gamemode());
		if(config.getBoolean("Basics.Modulmanager.monitor"))objects.add(new Monitor());
		if(config.getBoolean("Basics.Modulmanager.sudo"))objects.add(new Sudo());
		
		
	}
	
	private boolean enableSubs(){
		for(int i=0;i<objects.size();i++){
			objects.get(i).onEnable(this);
		}
		return true;
	}
	
	private boolean disableSubs(){
		for(int i=0;i<objects.size();i++){
			objects.get(i).onDisable(this);
		}
		return true;
	}
	
	private boolean subCommands(CommandSender sender, Command command, String label, String[] args){
		for(int i=0;i<objects.size();i++){
			if(!objects.get(i).onCommand(sender, command, label, args))return false;
		}
		return true;
	}
	public static boolean save(){
		try {
			config.save(configYML);
			return true;
		} catch (IOException e) {
			System.err.println("Exception wurde beim abspeichern der config erzeugt: ");
			e.printStackTrace();
			return false;
		}
	}
	public void reload(){
		System.out.println("Reloading Plugin Basics!");
		disableSubs();
		config=YamlConfiguration.loadConfiguration(configYML);
		objects=new LinkedList<object>();
		registerSubs();
		enableSubs();
	}
}
