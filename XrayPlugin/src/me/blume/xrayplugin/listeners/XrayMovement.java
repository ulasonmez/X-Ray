package me.blume.xrayplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.blume.xrayplugin.Main;

public class XrayMovement implements Listener{

	@SuppressWarnings("unused")
	private Main plugin;
	public XrayMovement(Main plugin) {
		this.plugin=plugin;
	}
	int xWide = 16,yWide=60,zWide=16;
	boolean hasCrouched = false,hasWorked = false;
	@EventHandler
	public void onMovement(PlayerMoveEvent event) {
		if(plugin.xrayPlayer == event.getPlayer()) {
			Player player = event.getPlayer();
			//if sneaking show the blocks glass
			if(event.getPlayer().isSneaking()) {
				if(!hasWorked) {
					plugin.task = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
						@Override
						public void run() {
							player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,20*1,2,false,false));
						}
					}, 0, 20L*1);
				}
				//has never been crouched
				if(!hasCrouched) {
					for(double x = - xWide ; x <= xWide ; x++) {
						for(double z = - zWide ; z <= zWide ; z++) {
							for(double y = -yWide ; y <= yWide ; y++) {
								Location loc = new Location(player.getWorld(),player.getLocation().getX()+x,player.getLocation().getY()+y,player.getLocation().getZ()+z);
								plugin.location.add(loc);
								if(loc.getBlock().getType().equals(Material.AIR)) {
									continue;
								}
								if(plugin.notmaterial.contains(loc.getBlock().getType())) {
									player.sendBlockChange(loc, Material.AIR.createBlockData());
									continue;
								}
								if(!plugin.material.contains(loc.getBlock().getType()))
									player.sendBlockChange(loc, Material.GLASS.createBlockData());
							}
						}
					}
					plugin.coordinates[0] = player.getLocation().getX();
					plugin.coordinates[1] = player.getLocation().getY();
					plugin.coordinates[2] = player.getLocation().getZ();
					hasCrouched = true;
				}
				//has crouched once
				else {
					//if he is in the same area return
					Location playersLoc = new Location(player.getWorld(),player.getLocation().getX(),player.getLocation().getY(),player.getLocation().getZ());
					if(plugin.coordinates[0] - xWide <= playersLoc.getX() && plugin.coordinates[0] + xWide >= playersLoc.getX()
							&& plugin.coordinates[1] - yWide <= playersLoc.getY() && plugin.coordinates[1] + yWide >= playersLoc.getY()
							&& plugin.coordinates[2] - zWide <= playersLoc.getZ() && plugin.coordinates[2] + zWide >= playersLoc.getZ()) {
						return;
					}
					//if he is not in the same are change all the blocks before
					for(double x = - xWide ; x <= xWide ; x++) {
						for(double z = - zWide ; z <= zWide ; z++) {
							for(double y = -yWide ; y <= yWide ; y++) {
								Location loc = new Location(player.getWorld(),plugin.coordinates[0]+x,plugin.coordinates[1]+y,plugin.coordinates[2]+z);
								plugin.location.add(loc);
								player.sendBlockChange(loc, loc.getBlock().getType().createBlockData());
							}
						}
					}
					plugin.location.clear();
					//now change the new block to glass and change plugin.coordinates
					for(double x = - xWide; x <= xWide; x++) {
						for(double z = - zWide ; z <= zWide ; z++) {
							for(double y = -yWide ; y <= yWide ; y++) {
								Location loc = new Location(player.getWorld(),player.getLocation().getX()+x,player.getLocation().getY()+y,player.getLocation().getZ()+z);
								plugin.location.add(loc);
								if(loc.getBlock().getType().equals(Material.AIR)) {
									continue;
								}
								if(plugin.notmaterial.contains(loc.getBlock().getType())) {
									player.sendBlockChange(loc, Material.AIR.createBlockData());
									continue;
								}
								if(!plugin.material.contains(loc.getBlock().getType()))
									player.sendBlockChange(loc, Material.GLASS.createBlockData());
							}
						}
					}
					plugin.coordinates[0] = player.getLocation().getX();
					plugin.coordinates[1] = player.getLocation().getY();
					plugin.coordinates[2] = player.getLocation().getZ();
				}

				hasWorked = true;
			}
			//if not sneaking put the blocks back
			else {
				if(hasWorked == true) {
					for(double x = - xWide ; x <= xWide ; x++) {
						for(double z = - zWide ; z <= zWide ; z++) {
							for(double y = -yWide ; y <= yWide ; y++) {
								Location loc = new Location(player.getWorld(),plugin.coordinates[0]+x,plugin.coordinates[1]+y,plugin.coordinates[2]+z);
								plugin.location.add(loc);
								player.sendBlockChange(loc, loc.getBlock().getType().createBlockData());
							}
						}
					}
					plugin.location.clear();
					hasWorked = false;
					hasCrouched = false;
					plugin.task.cancel();
				}
			}
		}
	}

}
