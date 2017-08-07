package com.archer.Game;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;

import com.archer.Main;


public class getGame implements Listener{
	
	static Main plugin;
	@SuppressWarnings("static-access")
	public getGame(Main main){
		this.plugin = main;
	}
	
	public static void startTimer(){
		Main.Minutes = 20;
		Main.Seconds = 60;
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run() {
				if(Main.Seconds != 0){
					Main.Seconds--;
				}else{
					Main.Seconds = 60;
					Main.Minutes--;
					if(Main.Minutes == 0){
						Main.Episode++;
						Bukkit.broadcastMessage("*-*-*-*-*  Episode " + Main.Episode + " *-*-*-*-*");
						Main.Seconds = 60;
						Main.Minutes = 20;
					}
				}
			}
		}, 20,20);
	}
	
	@SuppressWarnings("deprecation")
	public static void start(){
		Main.isStarted = true;
		
		Main.task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run() {
				if(Main.cooldown != -1){
					if(Main.cooldown != 0){
						Bukkit.broadcastMessage("§8§l>> §r§7La partie commence dans §f§o" + Main.cooldown + "§r§7!");
						Main.cooldown--;
					} else {
						Main.cooldown--;
						Bukkit.getScheduler().cancelTask(Main.task);
						Bukkit.broadcastMessage("§8§l>> §r§a§oBonne Chance §8§l<<");
						startTimer();
						for(Player pp : Bukkit.getOnlinePlayers()){
							Bukkit.getWorld("world").setDifficulty(Difficulty.EASY);
							pp.setGameMode(GameMode.SURVIVAL);
							pp.setFoodLevel(20);
							pp.setSaturation(40F);
							pp.setHealth(20);
							pp.setLevel(0);
							pp.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 10*20, 255));
							Bukkit.getWorld("world").setTime(0L);
							Bukkit.getWorld("world").setDifficulty(Difficulty.HARD);
						}
						
						for(Player T1 : Main.team1){
							T1.teleport(new Location(Bukkit.getWorld("world"), 900, 150, 900));
						}
						for(Player T2 : Main.team2){
							T2.teleport(new Location(Bukkit.getWorld("world"), -900, 150, 900));
						}
						for(Player T3 : Main.team3){
							T3.teleport(new Location(Bukkit.getWorld("world"), 900, 150, -900));
						}
						for(Player T4 : Main.team4){
							T4.teleport(new Location(Bukkit.getWorld("world"), -900, 150, -900));
						}
						/////////////////////////////////////////////////////////////////////						
						for(Player T5 : Main.team5){
							T5.teleport(new Location(Bukkit.getWorld("world"), 300, 150, 300));
						}
						for(Player T6 : Main.team6){
							T6.teleport(new Location(Bukkit.getWorld("world"), -300, 150, 300));
						}
						for(Player T7 : Main.team7){
							T7.teleport(new Location(Bukkit.getWorld("world"), 300, 150, -300));
						}
						for(Player T8 : Main.team8){
							T8.teleport(new Location(Bukkit.getWorld("world"), -300, 150, -300));
						}
						/////////////////////////////////////////////////////////////////////
						for(Player T9 : Main.team9){
							T9.teleport(new Location(Bukkit.getWorld("world"), 900, 150, 0));
						}
						for(Player T10 : Main.team10){
							T10.teleport(new Location(Bukkit.getWorld("world"), -900, 150, 0));
						}
						
					}
				}
			}
		},20, 20);
		
		
		Main.ScMainObj.getScore(Bukkit.getOfflinePlayer("Episode " + ChatColor.WHITE + Main.Episode)).setScore(5);
		Main.ScMainObj.getScore(Bukkit.getOfflinePlayer("" + Bukkit.getOnlinePlayers().size() + ChatColor.GRAY + " joueurs")).setScore(4);
		Main.ScMainObj.getScore(Bukkit.getOfflinePlayer("")).setScore(3);
		Main.ScMainObj.getScore(Bukkit.getOfflinePlayer(Main.Minutes  + ":" + Main.Seconds)).setScore(2);
		Main.ScMainObj.getScore(Bukkit.getOfflinePlayer("")).setScore(1);
	}
	
	public static void stop(){
		Main.isStarted = false;
		Bukkit.broadcastMessage("§4§l*-*-*-*-*  Arrêt de la partie ! *-*-*-*-*");
		Main.board.clearSlot(DisplaySlot.SIDEBAR);
		Bukkit.reload();
	}
	
	
	
	
}
