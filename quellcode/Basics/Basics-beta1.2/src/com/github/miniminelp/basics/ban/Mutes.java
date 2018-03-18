package com.github.miniminelp.basics.ban;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.github.miniminelp.basics.core.Timemanager;
import com.github.miniminelp.basics.util.Mutetypes;


public class Mutes {

	static File mutesYML=new File("plugins/Basics/saves", "mutes.yml");
	static FileConfiguration mutes=YamlConfiguration.loadConfiguration(mutesYML);
	
	public static boolean save(){
		try {
			mutes.save(mutesYML);
			return true;
		} catch (IOException e) {
			System.err.println("Exception wurde beim abspeichern der Mutes erzeugt: ");
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean isPlayerMuted(String player){
		
		if(mutes.contains("MUTES."+player.toLowerCase())){
			return checkMute(player);
		}
		return false;
	}
	public static boolean isPlayerMuted(Player player){
		checkMute(player);
		return mutes.contains("MUTES."+player.getUniqueId().toString().toLowerCase());
	}
	public static String getReason(String player){
		if(isPlayerMuted(player)){
			return mutes.getString("MUTES."+player.toLowerCase()+".reason").replace("&", "§");
		}
		return null;
	}
	public static String getReason(Player player){
		if(isPlayerMuted(player)){
			return mutes.getString("MUTES."+player.getUniqueId().toString().toLowerCase()+".reason").replace("&", "§");
		}
		return null;
	}
	public static String getMuter(String player){
		if(isPlayerMuted(player)){
			return mutes.getString("MUTES."+player.toLowerCase()+".muter").replace("&", "§");
		}
		return null;
	}
	public static String getMuter(Player player){
		if(isPlayerMuted(player)){
			return mutes.getString("MUTES."+player.getUniqueId().toString().toLowerCase()+".muter").replace("&", "§");
		}
		return null;
	}
	
	public static long getMuteTime(String player){
		if(mutes.contains("MUTES."+player.toLowerCase())){
			String s = mutes.getString("MUTES."+player.toLowerCase()+".time");
			if(!s.equalsIgnoreCase("LIFETIME"))return Long.parseLong(s);
			else return -1;
		}
		return 0;
	}
	public static long getMuteTime(Player player){
		if(mutes.contains("MUTES."+player.getUniqueId().toString().toLowerCase())){
			String s = mutes.getString("MUTES."+player.getUniqueId().toString().toLowerCase()+".time");
			if(!s.equalsIgnoreCase("LIFETIME"))return Long.parseLong(s);
			else return -1;
		}
		return 0;
	}
	
	public static long getUnmuteTime(String player){
		return getMuteTime(player)-Timemanager.getTime();
	}
	
	public static long getUnmuteTime(Player player){
		return getMuteTime(player)-Timemanager.getTime();
	}
	
	public static boolean checkMute(String player){
		if(getUnmuteTime(player)<1&&getMuteType(player)==1){
			mutes.set("MUTES.DONT_DELEAT_ME_FROM_MUTELIST", "MUTED");
			mutes.set("MUTES."+player.toLowerCase(), null);
			save();
			return false;
		}
		return true;
	}
	public static boolean checkMute(Player player){
		if(getUnmuteTime(player)<1&&getMuteType(player)==1){
			mutes.set("MUTES.DONT_DELEAT_ME_FROM_MUTELIST", "MUTED");
			mutes.set("MUTES."+player.getUniqueId().toString().toLowerCase(), null);
			save();
			return false;
		}
		return true;
	}
	
	public static String getFormattedMuteTime(String player){
		long mutetime=((getMuteTime(player)-Timemanager.getTime())/1000)+1;
		
		
		return mutetime+" secounds";
	}
	
	public static String getFormattedMuteTime(Player player){
		return getFormattedMuteTime(player.getUniqueId().toString());
	}
	
	public static int getMuteType(String player){
		if(getMuteTime(player)==0)return 0;
		else if(getMuteTime(player)==-1)return 2;
		else return 1;
	}
	
	public static int getMuteType(Player player){
		if(getMuteTime(player)==0)return Mutetypes.NOT_MUTED;
		else if(getMuteTime(player)==-1)return Mutetypes.LIFETIME_MUTED;
		else return 1;
	}
}