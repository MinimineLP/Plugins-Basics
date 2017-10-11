package com.github.miniminelp.basics.heal;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.github.miniminelp.basics.core.Basics;
import com.github.miniminelp.basics.core.Formatting;
import com.github.miniminelp.basics.core.object;


public class Heal implements object {
	private Basics plugin;
	@Override
	public void onEnable(Basics plugin) {
		this.plugin=plugin;
	}
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("Heal")){
			if(sender instanceof Player){
				Player p=(Player)sender;
				if(args.length>0){
					if(p.hasPermission("Basics.heal.other")){
						Player p2=plugin.getServer().getPlayer(args[0]);
						if(p2.isOnline()){

							p2.setHealth(p.getMaxHealth());
							p2.setRemainingAir(p.getMaximumAir());
							p2.setFoodLevel(20);
							p2.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 60, 255, false, true));
							p2.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 255, false, true));
							p2.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 60, 255, false, true));
							
							p2.sendMessage("You have been healed by "+p.getName());
							p.sendMessage("You healed "+args[0]);
						}else{
							p.sendMessage(Formatting.COLOR.RED+"The Player \""+args[0]+"\" is not online!"+Formatting.RESET);
						}
					}
				}else{
					if(p.hasPermission("Basics.heal.self")){
						p.setHealth(p.getMaxHealth());
						p.setRemainingAir(p.getMaximumAir());
						p.setFoodLevel(20);
						p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 60, 255, false, true));
						p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 255, false, true));
						p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 60, 255, false, true));
						
						p.sendMessage("You healed yourself!");
					}else{
						p.sendMessage(Formatting.COLOR.RED+"You don't have the Permission to heal yourself!"+Formatting.RESET);
					}
				}
			}else{
				sender.sendMessage(Formatting.COLOR.RED+"This command is yust for Player"+Formatting.RESET);
			}
		}
		return true;
	}

}
