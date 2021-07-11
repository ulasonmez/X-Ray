package me.blume.xrayplugin;

import java.util.ArrayList;
import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import me.blume.xrayplugin.commands.ActivateXray;
import me.blume.xrayplugin.commands.DisableXray;
import me.blume.xrayplugin.listeners.ClickListener;
import me.blume.xrayplugin.listeners.XrayMovement;

public class Main extends JavaPlugin{
	Material[] seeThem = {Material.COAL_ORE,Material.IRON_ORE,Material.GOLD_ORE,Material.LAPIS_ORE,
			Material.DIAMOND_ORE,Material.REDSTONE_ORE,Material.WATER,Material.LAVA,Material.AIR,
			Material.ANCIENT_DEBRIS,Material.NETHER_GOLD_ORE,Material.END_PORTAL_FRAME,Material.CHEST,
			Material.BEDROCK,Material.GOLD_BLOCK,Material.OBSIDIAN,Material.NETHER_PORTAL};
	Material[] notseeThem = {Material.GRASS,Material.WHEAT_SEEDS,Material.TALL_GRASS,
							Material.POPPY,Material.DANDELION,Material.DEAD_BUSH,Material.CORNFLOWER
							,Material.OXEYE_DAISY,Material.AZURE_BLUET,Material.SUNFLOWER,
							Material.BLUE_ORCHID,Material.ALLIUM,Material.RED_TULIP,Material.ORANGE_TULIP,
							Material.WHITE_TULIP,Material.PINK_TULIP,Material.LILY_OF_THE_VALLEY,
							Material.BROWN_MUSHROOM,Material.RED_MUSHROOM,Material.CRIMSON_FUNGUS,
							Material.WARPED_FUNGUS,Material.CRIMSON_ROOTS,Material.WARPED_ROOTS,
							Material.NETHER_SPROUTS,Material.KELP};
	public HashSet<Material> material = new HashSet<Material>();
	public HashSet<Material> notmaterial = new HashSet<Material>();
	public ArrayList<Location> location = new ArrayList<Location>();
	public double[] coordinates = new double[3];
	public Player xrayPlayer = null;
	public BukkitTask task;
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new XrayMovement(this), this);
		getServer().getPluginManager().registerEvents(new ClickListener(this), this);
		getCommand("activatexray").setExecutor(new ActivateXray(this));
		getCommand("disablexray").setExecutor(new DisableXray(this));
		for(Material m : seeThem) {
			material.add(m);
		}
		for(Material m : notseeThem) {
			notmaterial.add(m);
		}
	}
}
