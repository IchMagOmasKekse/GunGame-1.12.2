package me.xxfreakdevxx.de.gungame;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.xxfreakdevxx.de.gungame.lobby.Lobby;

public class JoinHandler implements Listener {
	
	private GunGame pl;
		
	@SuppressWarnings("unused")
	private Lobby lobby1 = null;
	@SuppressWarnings("unused")
	private Lobby lobby2 = null;
	private boolean lockLocEveryBlock = false;
	
	private HashMap<Player, Location> locStorage = new HashMap<Player, Location>();
	
	public JoinHandler(GunGame pl) {
		this.pl=pl;
		this.pl.getServer().getPluginManager().registerEvents(this, pl);
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if(lockLocEveryBlock) {			
			Player p = e.getPlayer();
			Location loc = p.getLocation();
			Location l = null;
			if(locStorage.containsKey(p)) l=locStorage.get(p);
			if(l != null) {
				if(loc.getBlockX() != l.getBlockX() ||
						loc.getBlockY() != l.getBlockY() ||
						loc.getBlockZ() != l.getBlockZ()) {
					locStorage.remove(p);
					locStorage.put(p, loc);
					checkForEntries(e);
				}
			}else{
				locStorage.put(p, loc);
				checkForEntries(e);
			}
		}else checkForEntries(e);
	}
	
	public void checkForEntries(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		Lobby lobby = pl.getLobbyManager().checkForEntry(p);
		if(lobby != null) {
			if(lobby.join(p)) p.sendMessage("§aDu hast Lobby §f"+lobby.getLobbyId()+" §abetreten! "+lobby.getPlayersString()+" §aMap: §f"+lobby.getMapName());
			else p.teleport(pl.getLobbyManager().entrySpawn);
		}
	}
	
}
