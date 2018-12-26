package me.xxfreakdevxx.de.gungame.lobby;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.xxfreakdevxx.de.gungame.GunGame;

public class LobbyManager {
	
	
//	private Location gameEntry1 = new Location(Bukkit.getWorld("world"), 150, 66, 247);
//	private Location gameEntry2 = new Location(Bukkit.getWorld("world"), 154, 66, 251);
	private Location entry1 = new Location(Bukkit.getWorld("world"), 232, 67, 412);
	private Location entry2 = new Location(Bukkit.getWorld("world"), 234, 70, 414);
//	private Location entry1 = new Location(Bukkit.getWorld("world"), 153, 66, 260);
//	private Location entry2 = new Location(Bukkit.getWorld("world"), 157, 63, 264);
	public Location entrySpawn = new Location(Bukkit.getWorld("world"), 233.5, 72, 262.5, 90, 0);
	@SuppressWarnings("unused")
	private GunGame pl;
	@SuppressWarnings("unused")
	private HashMap<String, Lobby> playersLobby = new HashMap<String, Lobby>();
	private HashMap<String, Lobby> lobbies = new HashMap<String, Lobby>();
	
	public LobbyManager(GunGame pl) {
		this.pl=pl;
	}
	public boolean createLobby(Lobby lobby) {
		if(lobbies.containsKey(lobby.getLobbyId())) return false;
		lobbies.put(lobby.getLobbyId(), lobby);
		System.out.println("Lobby "+lobby.getLobbyId()+" wurde erstellt");
		return true;
	}
	public Lobby checkForEntry(Player p) {
		double px, py, pz;
		double x1, x2;
		double y1, y2;
		double z1, z2;
		px = p.getLocation().getX();
		py = p.getLocation().getY();
		pz = p.getLocation().getZ();
		if(entry1.getX() > entry2.getX()){
			x1 = entry1.getX(); x2 = entry2.getX();
		}else { x1 = entry2.getX(); x2 = entry1.getX(); }
		
		if(entry1.getY() > entry2.getY()){
			y1 = entry1.getY(); y2 = entry2.getY();
		}else { y1 = entry2.getY(); y2 = entry1.getY(); }
		
		if(entry1.getZ() > entry2.getZ()){
			z1 = entry1.getZ(); z2 = entry2.getZ();
		}else { z1 = entry2.getZ(); z2 = entry1.getZ(); }
		
		if(px >= x2 && px <= x1) {
			if(py >= y2 && py <= y1) {
				if(pz >= z2 && pz <= z1) {
					System.out.println("Suche freie Lobby...");
					for(String s : lobbies.keySet()) {
						Lobby lobby = lobbies.get(s);
						if(lobby.getPlayersAmount() < lobby.getMaxPlayers()) {
							System.out.println("Lobby gefunden! Lobby-ID: "+lobby.getLobbyId());
							return lobby;
						}
					}
				}				
			}
		}
		return null;
	}
	public void remove(Lobby lobby) {
		lobbies.remove(lobby);
	}
	
}
