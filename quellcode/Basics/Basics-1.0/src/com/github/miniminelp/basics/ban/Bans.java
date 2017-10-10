package com.github.miniminelp.basics.ban;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.github.miniminelp.basics.core.Timemanager;
import com.github.miniminelp.basics.util.Bantypes;


public class Bans {

	static File bansYML=new File("plugins/Basics/saves", "bans.yml");
	static FileConfiguration bans=YamlConfiguration.loadConfiguration(bansYML);
	
	public static boolean save(){
		try {
			bans.save(bansYML);
			return true;
		} catch (IOException e) {
			System.err.println("Exception wurde beim abspeichern der bans erzeugt: ");
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean isPlayerBanned(String player){
		
		if(bans.contains("BANS."+player.toLowerCase())){
			return checkBan(player);
		}
		return false;
	}
	public static boolean isPlayerBanned(Player player){
		checkBan(player);
		return bans.contains("BANS."+player.getName().toLowerCase());
	}
	public static String getReason(String player){
		if(isPlayerBanned(player)){
			return bans.getString("BANS."+player.toLowerCase()+".reason").replace("&", "§");
		}
		return null;
	}
	public static String getReason(Player player){
		if(isPlayerBanned(player)){
			return bans.getString("BANS."+player.getName().toLowerCase()+".reason").replace("&", "§");
		}
		return null;
	}
	public static String getBanner(String player){
		if(isPlayerBanned(player)){
			return bans.getString("BANS."+player.toLowerCase()+".banner").replace("&", "§");
		}
		return null;
	}
	public static String getBanner(Player player){
		if(isPlayerBanned(player)){
			return bans.getString("BANS."+player.getName().toLowerCase()+".banner").replace("&", "§");
		}
		return null;
	}
	
	public static long getBannTime(String player){
		if(bans.contains("BANS."+player.toLowerCase())){
			String s = bans.getString("BANS."+player.toLowerCase()+".time");
			if(!s.equalsIgnoreCase("LIFETIME"))return Long.parseLong(s);
			else return -1;
		}
		return 0;
	}
	public static long getBannTime(Player player){
		if(bans.contains("BANS."+player.getName().toLowerCase())){
			String s = bans.getString("BANS."+player.getName().toLowerCase()+".time");
			if(!s.equalsIgnoreCase("LIFETIME"))return Long.parseLong(s);
			else return -1;
		}
		return 0;
	}
	
	public static long getUnbanTime(String player){
		return getBannTime(player)-Timemanager.getTime();
	}
	
	public static long getUnbanTime(Player player){
		return getBannTime(player)-Timemanager.getTime();
	}
	
	public static boolean checkBan(String player){
		if(getUnbanTime(player)<1&&getBanType(player)==1){
			bans.set("BANS.DONT_DELEAT_ME_FROM_BANLIST", "BANNED");
			bans.set("BANS."+player.toLowerCase(), null);
			save();
			return false;
		}
		return true;
	}
	public static boolean checkBan(Player player){
		if(getUnbanTime(player)<1&&getBanType(player)==1){
			bans.set("BANS.DONT_DELEAT_ME_FROM_BANLIST", "BANNED");
			bans.set("BANS."+player.getName().toLowerCase(), null);
			save();
			return false;
		}
		return true;
	}
	
	public static String getFormattedBannTime(String player){
		long banntime=(getBannTime(player)-Timemanager.getTime())/1000;
		
		/*//Years
		int y=(int)banntime/(365*86400);
		banntime-=y*365*86400;
		
		//Months
		int mo=(int)banntime/(31*86400);
		banntime-=mo*31*86400;
				
		//Days
		int d=(int)banntime/(86400);
		banntime-=d*86400;
		
		//Hours
		int h=(int)banntime/(3600);
		banntime-=h*3600;
		
		//Minutes
		int m=(int)banntime/(60);
		banntime-=m*60;
		
		//Secounds
		int s=(int)banntime;
		banntime-=s;
		
		return d+"."+mo+". "+y+" "+h+":"+m+":"+s;*/
		return banntime+"";
	}
	
	public static String getFormattedBannTime(Player player){
		return getFormattedBannTime(player.getName());
	}
	
	public static int getBanType(String player){
		if(getBannTime(player)==0)return 0;
		else if(getBannTime(player)==-1)return 2;
		else return 1;
	}
	
	public static int getBanType(Player player){
		if(getBannTime(player)==0)return Bantypes.NOT_BANNED;
		else if(getBannTime(player)==-1)return Bantypes.LIFETIME_BANNED;
		else return 1;
	}
}
