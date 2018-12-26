package me.xxfreakdevxx.de.gungame.lobby;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public enum GGMap {
	
	RANDOM("Zufällige Map", 2, 16, new Location(Bukkit.getWorld("world"), 136, 89, 224), new Location(Bukkit.getWorld("world"), 136, 68, 224)),
	MAP_FORREST("FORREST", 2, 16, new Location(Bukkit.getWorld("world"), 136, 89, 224), new Location(Bukkit.getWorld("world"), 136, 68, 224));
	
	private String name="";
	private int minPlayers=0;
	private int maxPlayers=0;
	private Location lobbyLoc;
	private Location mapSpawn;
	
	GGMap(String name, int minPlayers, int maxPlayers, Location lobbyLoc, Location mapSpawn) {
		this.name = name;
		this.minPlayers = minPlayers;
		this.maxPlayers = maxPlayers;
		this.lobbyLoc = lobbyLoc;
		this.mapSpawn = mapSpawn;
	}
	public String getName() {return name;}
	public int getMinPlayers() {return minPlayers;}
	public int getMaxPlayers() {return maxPlayers;}
	public Location getLobbySpawn() {return lobbyLoc;}
	public Location getMapSpawn() {return mapSpawn;}
	public boolean setSpawn(int spawn, Location loc) {
		File file = new File("plugins/GunGame/maps/"+getName()+"/spawns.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		cfg.set("Map.Spawns."+spawn+".World", loc.getWorld().getName());
		cfg.set("Map.Spawns."+spawn+".X", loc.getX()); 
		cfg.set("Map.Spawns."+spawn+".Y", loc.getY()); 
		cfg.set("Map.Spawns."+spawn+".Z", loc.getZ()); 
		cfg.set("Map.Spawns."+spawn+".Yaw", loc.getYaw()); 
		cfg.set("Map.Spawns."+spawn+".Pitch", loc.getPitch());
		try { cfg.save(file); } catch (IOException e) { e.printStackTrace(); return false; }
		return true;
	}
	public Location getPlayerSpawn(int i) {
		File file = new File("plugins/GunGame/maps/"+getName()+"/spawns.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		String worldname;
		double x, y, z;
		float yaw, pitch;
		worldname = cfg.getString("Map.Spawns."+i+".World");
		x = cfg.getDouble("Map.Spawns."+i+".X");
		y = cfg.getDouble("Map.Spawns."+i+".Y");
		z = cfg.getDouble("Map.Spawns."+i+".Z");
		yaw = (float)cfg.getDouble("Map.Spawns."+i+".Yaw");
		pitch = (float)cfg.getDouble("Map.Spawns."+i+".Pitch");
		return new Location(Bukkit.getWorld(worldname), x, y, z, yaw, pitch);
	}
}
