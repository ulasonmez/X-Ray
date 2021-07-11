package me.blume.xrayplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.blume.xrayplugin.Main;
public class ActivateXray implements CommandExecutor{

	private Main plugin;
	public ActivateXray(Main plugin) {
		this.plugin=plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String label, String[] args) {
		if(args.length==0) {
			if(label.equals("activatexray")) {
				if(sender instanceof Player) {
					if(plugin.xrayPlayer == null) {
						plugin.xrayPlayer = (Player) sender;
					}
				}
			}
		}
		
		
		return false;
	}
}
