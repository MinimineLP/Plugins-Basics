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
import com.github.miniminelp.basics.heal.Heal;
import com.github.miniminelp.basics.home.Home;
import com.github.miniminelp.basics.op.Gop;
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
		Bukkit.getPluginManager().registerEvents(new JoinListener(this), this);
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
		
		return subCommands(sender, command, label, args);
	}
	
	private void registerSubs(){
		objects.add(new Home());
		objects.add(new Gop());
		objects.add(new Tpa());
		objects.add(new Warp());
		objects.add(new Heal());
		objects.add(new Ban());
		objects.add(new Mute());
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
	
}
