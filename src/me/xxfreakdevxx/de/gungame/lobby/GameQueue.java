package me.xxfreakdevxx.de.gungame.lobby;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.xxfreakdevxx.de.gungame.GunGame;

public class GameQueue implements Listener {
	
	private List<GGPlayer> players;
	private String lobbyid;
	private GGMap map;
	
	public GameQueue(LinkedList<GGPlayer> players, Lobby lobby) {
		GunGame.getInstance().getServer().getPluginManager().registerEvents(this, GunGame.getInstance());
		this.players = players;
		this.lobbyid = lobby.getLobbyId();
		this.map = lobby.getMap();
		for(GGPlayer p : players) TierSettings.setInventory(p);
	}
	
	public GGPlayer getGGPlayer(Player p) {
		for(GGPlayer gp : players) if(gp.player.getName().equals(p.getName())) return gp;
		return null;
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		if(e.getEntity().getKiller() != null) {
			Player p = e.getEntity().getKiller();
			GGPlayer gp = getGGPlayer(p);
			gp.nextLevel();
		}
	}
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		if(e.getEntity().getKiller() != null) {
			Player p = e.getEntity().getKiller();
			GGPlayer gp = getGGPlayer(p);
			gp.nextLevel();
		}
	}
	
}
