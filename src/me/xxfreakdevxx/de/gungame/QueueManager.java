package me.xxfreakdevxx.de.gungame;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.entity.Player;

import me.xxfreakdevxx.de.gungame.lobby.GGPlayer;
import me.xxfreakdevxx.de.gungame.lobby.GameQueue;
import me.xxfreakdevxx.de.gungame.lobby.Lobby;

public class QueueManager {
	
	private HashMap<Integer, GameQueue> queues = new HashMap<Integer, GameQueue>();
	
	public QueueManager() {
		
	}
	
	public boolean createNewQueue(Lobby lobby) {
		if(GunGame.isInMaintance()) return false;
		else {
			List<Player> p = lobby.getPlayers();
			LinkedList<GGPlayer> players = new LinkedList<GGPlayer>();
			for(Player ps : p) {
				players.add(new GGPlayer(ps));
			}
			GameQueue gq = new GameQueue(players, lobby);
			queues.put(queues.size(), gq);
			int i = 0;
			for(Player ps : p){
				ps.teleport(lobby.getMap().getPlayerSpawn(i+1));
				i++;
			}
		}
		
		return false;
	}
	
}
