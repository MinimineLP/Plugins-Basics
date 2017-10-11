package com.github.miniminelp.basics.core;

import java.util.Date;

import org.bukkit.command.CommandSender;

@SuppressWarnings("deprecation")
public class Timemanager {
	
	public static int getYear(){
		return new Date().getYear()+1900;
	}
	public static int getMonth(){
		return new Date().getMonth();
	}
	public static int getDay(){
		return new Date().getDay()+1;
	}
	public static int getHour(){
		return new Date().getHours();
	}
	public static int getMinutes(){
		return new Date().getMinutes();
	}
	public static int getSeconds(){
		return new Date().getSeconds();
	}
	public static String getFormattedDate(int toAddSecounds){
		int toAddYears=(int)(toAddSecounds/(365*86400));
		toAddSecounds-=toAddYears*365*86400;
		
		int toAddMonths=(int)(toAddSecounds/(31*86400));
		toAddSecounds-=toAddMonths*31*86400;
		
		int toAddDays=(int)(toAddSecounds/(86400));
		toAddSecounds-=toAddDays*86400;
		
		int toAddHours=(int)(toAddSecounds/(3600));
		toAddSecounds-=toAddHours*3600;
		
		int toAddMinutes=(int)(toAddSecounds/(60));
		toAddSecounds-=toAddMinutes*60;
		
		int years=getYear()+toAddYears;
		int months=getMonth()+toAddMonths;
		int days=getDay()+toAddDays;
		int hours=getHour()+toAddHours;
		int minutes=getMinutes()+toAddMinutes;
		int secounds=getSeconds()+toAddSecounds;
		
		for(int i=0;i<2;i++){
			if(secounds>59){
				secounds-=60;
				minutes++;
			}
			if(minutes>59){
				minutes-=60;
				hours++;
			}
			if(hours>24){
				hours-=24;
				days++;
			}
			if(months>12){
				months-=12;
				years++;
			}
		}
		
		
		
		return days+"."+months+". "+years+" "+hours+":"+minutes+":"+secounds;
	}
	public static int dateFormat(String format, CommandSender sender){
		int time=0;
		
		//Dateformat
		try{
			//years
			if(format.endsWith("years"))time = Integer.parseInt(replaceLast(format, "years", ""))*86400*365;
			else if(format.endsWith("year"))time = Integer.parseInt(replaceLast(format, "year", ""))*86400*365;
			else if(format.endsWith("y"))time = Integer.parseInt(replaceLast(format, "y", ""))*86400*365;
			
			//months
			else if(format.endsWith("months"))time = Integer.parseInt(replaceLast(format, "months", ""))*86400*31;
			else if(format.endsWith("month"))time = Integer.parseInt(replaceLast(format, "month", ""))*86400*31;
			else if(format.endsWith("mo"))time = Integer.parseInt(replaceLast(format, "mo", ""))*86400*31;
			
			//weeks
			else if(format.endsWith("weeks"))time = Integer.parseInt(replaceLast(format, "weeks", ""))*86400*7;
			else if(format.endsWith("week"))time = Integer.parseInt(replaceLast(format, "week", ""))*86400*7;
			else if(format.endsWith("w"))time = Integer.parseInt(replaceLast(format, "w", ""))*86400*7;
			
			//days
			else if(format.endsWith("days"))time = Integer.parseInt(replaceLast(format, "days", ""))*86400;
			else if(format.endsWith("day"))time = Integer.parseInt(replaceLast(format, "day", ""))*86400;
			else if(format.endsWith("d"))time = Integer.parseInt(replaceLast(format, "d", ""))*86400;
			
			//hours
			else if(format.endsWith("hours"))time = Integer.parseInt(replaceLast(format, "hours", ""))*3600;
			else if(format.endsWith("hour"))time = Integer.parseInt(replaceLast(format, "hour", ""))*3600;
			else if(format.endsWith("h"))time = Integer.parseInt(replaceLast(format, "h", ""))*3600;
			
			//minutes
			else if(format.endsWith("minutes"))time = Integer.parseInt(replaceLast(format, "minutes", ""))*60;
			else if(format.endsWith("minute"))time = Integer.parseInt(replaceLast(format, "minute", ""))*60;
			else if(format.endsWith("min"))time = Integer.parseInt(replaceLast(format, "min", ""))*60;
			else if(format.endsWith("m"))time = Integer.parseInt(replaceLast(format, "m", ""))*60;
			
			//secounds
			else if(format.endsWith("secounds"))time = Integer.parseInt(replaceLast(format, "secounds", ""));
			else if(format.endsWith("secound"))time = Integer.parseInt(replaceLast(format, "secound", ""));
			else if(format.endsWith("sec"))time = Integer.parseInt(replaceLast(format, "sec", ""));
			else if(format.endsWith("s"))time = Integer.parseInt(replaceLast(format, "s", ""));
			
			else sender.sendMessage("Wrong Timeformat given");
			
		} catch (Exception e) {
			sender.sendMessage("Wrong Timeformat given");
		}
		return time*1000;
	}
	
	private static String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst("(?s)"+regex+"(?!.*?"+regex+")", replacement);
    }
	
	
	public static long getTime(){
		return new Date().getTime();
	}
}
