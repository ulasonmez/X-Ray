package me.blume.xrayplugin.listeners;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.blume.xrayplugin.Main;


public class ClickListener implements Listener{

	@SuppressWarnings("unused")
	private Main plugin;
	public ClickListener(Main plugin) {
		this.plugin=plugin;
	}
	@EventHandler
	public void onClickFakeBlock(PlayerInteractEvent event) {
		Action action = event.getAction();
		if(plugin.xrayPlayer == event.getPlayer()) {
			if(action.equals(Action.LEFT_CLICK_BLOCK)) {
				Block block = event.getClickedBlock();
				if(plugin.location.contains(block.getLocation())) {
					event.getPlayer().sendBlockChange(block.getLocation(), block.getType().createBlockData());
				}
			}
		}
	}
	@EventHandler
	public void onDisconnect(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if(player == plugin.xrayPlayer) {
			plugin.xrayPlayer = null;
		}
	}
}
