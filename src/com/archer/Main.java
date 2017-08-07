package com.archer;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import com.archer.Commands.Commands;

public class Main extends JavaPlugin implements Listener{
	
	public static String Name;
	public static boolean isStarted;
	public static int task;
	public static int cooldown = 10;
	
	public static int Episode;
	public static int Minutes;
	public static int Seconds;

	public static Scoreboard board;
     public static Objective ScMainObj;
     public static Objective ScLifeObj;
     
     public static ArrayList<Player> team1 = new ArrayList<Player>();
     public static ArrayList<Player> team2 = new ArrayList<Player>();
     public static ArrayList<Player> team3 = new ArrayList<Player>();
     public static ArrayList<Player> team4 = new ArrayList<Player>();
     public static ArrayList<Player> team5 = new ArrayList<Player>();
     public static ArrayList<Player> team6 = new ArrayList<Player>();
     public static ArrayList<Player> team7 = new ArrayList<Player>();
     public static ArrayList<Player> team8 = new ArrayList<Player>();
     public static ArrayList<Player> team9 = new ArrayList<Player>();
     public static ArrayList<Player> team10 = new ArrayList<Player>();
     
     public void onEnable() {
		event();
		commands();
		scoreboard();
		craft();
		DefaultSetUp();
		Name = "Archer S2";
		isStarted = false;
	}

	private void craft() {
		ItemStack melon = new ItemStack(Material.SPECKLED_MELON, 1);
		ShapedRecipe melon1 = new ShapedRecipe(melon);
		melon1.shape(
				"***", 
				"*%*", 
				"***");
		 
		melon1.setIngredient('*', Material.GOLD_INGOT);
		melon1.setIngredient('%', Material.MELON);
		getServer().addRecipe(melon1);
		
		//Disable Nugget Melon
		ItemStack disableMelon = new ItemStack(Material.AIR, 1);
		ShapedRecipe disableMelon1 = new ShapedRecipe(disableMelon);
		disableMelon1.shape(
				"***", 
				"*%*", 
				"***");
		 
		disableMelon1.setIngredient('*', Material.GOLD_NUGGET);
		disableMelon1.setIngredient('%', Material.MELON);
		getServer().addRecipe(disableMelon1);
		
	}

	private void event() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(this, this);
		//pm.registerEvents(new LeNomDeTaClasse(this), this);
	}

	private void commands() {
		getCommand("").setExecutor(new Commands(this));//TODO Commande Nom;
	}
	
	public static void generateWalls(){
		
	}
	
	private void scoreboard(){
		
		board = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
		
		ScMainObj = board.registerNewObjective("Main", "dummy");
		ScLifeObj = board.registerNewObjective("Life", "health");
		ScMainObj.setDisplayName(Name);
		ScMainObj.setDisplaySlot(DisplaySlot.SIDEBAR);
		ScLifeObj.setDisplaySlot(DisplaySlot.BELOW_NAME);
	}
	
	private void DefaultSetUp(){
		Bukkit.getWorld("world").setDifficulty(Difficulty.HARD);
		Bukkit.getServer().setDefaultGameMode(GameMode.SURVIVAL);
	}
	
	// EVENT & CRAFT //
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDeath(PlayerDeathEvent e){
		final Player p = (Player) e.getEntity();
		if(isStarted){
			Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
				public void run() {
					p.kickPlayer("§cVous avez été tuer par " + p.getKiller().getName() + " \n" + "§e§o§nJayJay!");
					p.setBanned(true);
				}
			}, 10 *20);
		}
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		if(e.getEntity() instanceof Ghast) {
			e.getDrops().clear();
			Bukkit.getWorld("world").dropItem(e.getEntity().getLocation(), new ItemStack(Material.GOLD_INGOT));
		}
	}
	  
	@EventHandler
	public void onEntityRegainHealth(EntityRegainHealthEvent e) {
		if (e.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED){
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		for(Player p : Bukkit.getOnlinePlayers()){
			p.setScoreboard(board);
		}
		if(!isStarted){
			e.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 0, 100, 0));
		 }
		}
	
	
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent e) {
		if(isStarted){
			e.setCancelled(false);
		} else {
			e.setCancelled(true);
		}
	}			
	
	
	public enum Tools{
		WOOD_SWORD, STONE_SWORD, IRON_SWORD, GOLD_SWORD, DIAMOND_SWORD,
		WOOD_AXE, STONE_AXE, IRON_AXE, GOLD_AXE, DIAMOND_AXE,
		WOOD_PICKAXE, STONE_PICKAXE, IRON_PICKAXE, GOLD_PICKAXE, DIAMOND_PICKAXE,
		WOOD_SPADE, STONE_SPADE, IRON_SPADE, GOLD_SPADE, DIAMOND_SPADE
	   }
	
	@EventHandler
	public void onTap(EntityDamageByEntityEvent e) {
		Player pK = (Player) e.getDamager();
		if (e.getEntity() instanceof Player) {
			if(e.getCause() == DamageCause.ENTITY_ATTACK && e.getEntityType() == EntityType.PLAYER){
			if (pK.getItemInHand().getType() == Material.WOOD_SWORD) {
				e.setCancelled(true);
			}else if (pK.getItemInHand().getType() == Material.STONE_SWORD) {
				e.setCancelled(true);
			}else if (pK.getItemInHand().getType() == Material.IRON_SWORD) {
				e.setCancelled(true);
			}else if (pK.getItemInHand().getType() == Material.GOLD_SWORD) {
				e.setCancelled(true);
			}else if (pK.getItemInHand().getType() == Material.DIAMOND_SWORD) {
				e.setCancelled(true);
			}
			
			// // // // // // // // // // // // // // // // // // // // // // //
			if (pK.getItemInHand().getType() == Material.WOOD_AXE) {
				e.setCancelled(true);
			}else if (pK.getItemInHand().getType() == Material.STONE_AXE) {
				e.setCancelled(true);
			}else if (pK.getItemInHand().getType() == Material.IRON_AXE) {
				e.setCancelled(true);
			}else if (pK.getItemInHand().getType() == Material.GOLD_AXE) {
				e.setCancelled(true);
			}else if (pK.getItemInHand().getType() == Material.DIAMOND_AXE) {
				e.setCancelled(true);
			}
			// // // // // // // // // // // // // // // // // // // // // // //
			if (pK.getItemInHand().getType() == Material.WOOD_PICKAXE) {
				e.setCancelled(true);
			}else if (pK.getItemInHand().getType() == Material.STONE_PICKAXE) {
				e.setCancelled(true);
			}else if (pK.getItemInHand().getType() == Material.IRON_PICKAXE) {
				e.setCancelled(true);
			}else if (pK.getItemInHand().getType() == Material.GOLD_PICKAXE) {
				e.setCancelled(true);
			}else if (pK.getItemInHand().getType() == Material.DIAMOND_PICKAXE) {
				e.setCancelled(true);
			}
			// // // // // // // // // // // // // // // // // // // // // // //
			if (pK.getItemInHand().getType() == Material.WOOD_SPADE) {
				e.setCancelled(true);
			}else if (pK.getItemInHand().getType() == Material.STONE_SPADE) {
				e.setCancelled(true);
			}else if (pK.getItemInHand().getType() == Material.IRON_SPADE) {
				e.setCancelled(true);
			}else if (pK.getItemInHand().getType() == Material.GOLD_SPADE) {
				e.setCancelled(true);
			}else if (pK.getItemInHand().getType() == Material.DIAMOND_SPADE) {
				e.setCancelled(true);
			}
			}
		} else {
			return;
		}
	}
	
}