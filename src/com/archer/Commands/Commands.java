package com.archer.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.archer.Main;
import com.archer.Game.getGame;

public class Commands implements CommandExecutor{
	
	Main plugin;
	public Commands(Main main){
		this.plugin = main;
	}
	
	public boolean onCommand(CommandSender sender, Command commandLabel, String label, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("§cERREUR: §4Les commandes sont utilisables seulement par des joueurs.");
	return false;
				}
				Player p = (Player) sender;
		if(p.isOp()){
			if(label.equalsIgnoreCase("game")){
				
					
					if(args.length == 0){
						p.sendMessage("§cUsage: /game <start/stop/menu>");
					} else if(args.length == 1){
						if(args[0].equalsIgnoreCase("start")){
							
	getGame.start();
			} else if(args[0].equalsIgnoreCase("stop")){
							//TODO
						} else if(args[0].equalsIgnoreCase("menu")){
							//TODO
						} else {
							p.sendMessage("§cUsage: /game <start/stop/menu>");
						}
					}
					
					
				}
			} else {
				p.sendMessage("§cTu n'a pas la permision");
		}
				
				

				
		return false;
	}

}
