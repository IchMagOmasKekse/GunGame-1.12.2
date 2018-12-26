package me.xxfreakdevxx.de.gungame.lobby;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.xxfreakdevxx.de.gungame.GunGame;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;

public class Lobby {
	
	private Lobby lobby;
	private String lobbyId = "";
	private int playersAmount = 0;
	private List<Player> players = new LinkedList<Player>();
	private Location spawnLocation = null;
	private Location entryLocation = null;
	private GGMap gameMap;
	private HashMap<String, BukkitRunnable> timer = new HashMap<String, BukkitRunnable>();
	private boolean startCountdown = false;
	private int countdown = -1;
	
	public Lobby(GGMap map, String lobbyId, Location entryLocation) {
		this.lobbyId = lobbyId;
		this.entryLocation = entryLocation;
		this.spawnLocation = entryLocation.add(0,10,0);
		this.gameMap = map;
		int c = 10;
		this.timer.put("playerChecker", new BukkitRunnable() {
			
			@Override
			public void run() {
				if(startCountdown == false) {					
					if(getPlayersAmount() >= gameMap.getMinPlayers()){
						startCountdown = true;
						countdown = c;
					}else{
						countdown = c;
					}
				}else{
					countdown--;
					if(countdown == 0) {
						countdown = 1000;
						startCountdown = false;
						GunGame.getInstance().getQueueManager().createNewQueue(lobby);
						clearLobby();
					}else{
						playLobbySound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
						sendLobbyMessages("Spiel beginnt int "+countdown);
					}
				}
			}

		});
		this.lobby = this;
		this.timer.get("playerChecker").runTaskTimer(GunGame.getInstance(), 20, 20);
	}
	private void clearLobby() {
		this.timer.get("playerChecker").cancel();
		players.clear();
		GunGame.getInstance().getLobbyManager().remove(this);
		GunGame.getInstance().getLobbyManager().createLobby(new Lobby(gameMap, lobbyId, entryLocation));
	}

	public String getLobbyId() {
		return lobbyId;
	}
	public int getMaxPlayers() {
		return gameMap.getMaxPlayers();
	}
	public int getPlayersAmount() {
		return playersAmount;
	}
	public Location getSpawnLocation() {
		return spawnLocation;
	}

	public boolean isInLobby(Player p) {
		return players.contains(p);
	}
	public boolean join(Player player) {
		if(isInLobby(player)) return false;
		players.add(player);
		this.playersAmount++;
		Location loc = player.getLocation();
		GunGame.playParticle(new PacketPlayOutWorldParticles(EnumParticle.EXPLOSION_NORMAL,true,
				(float)loc.getX(),
				(float)loc.getY(),
				(float)loc.getZ(),0,0,0,1,0));
		player.teleport(gameMap.getLobbySpawn());
		loc = player.getLocation();
		GunGame.playParticle(new PacketPlayOutWorldParticles(EnumParticle.EXPLOSION_NORMAL,true,
				(float)loc.getX(),
				(float)loc.getY(),
				(float)loc.getZ(),0,0,0,1,0));
		return true;
	}
	public boolean quit(Player player) {
		if(isInLobby(player)){
			players.remove(player);
			this.playersAmount--;
			return true;
		}
		return false;
	}
	public Location getEntryLocation() {
		return entryLocation;
	}
	public String getPlayersString() {
		return "§a(§2"+playersAmount+"§f/§2"+ gameMap.getMaxPlayers()+"§a)";
	}
	public String getMapName() {
		return gameMap.getName();
	}
	public GGMap getMap() {
		return gameMap;
	}
	public LinkedList<Player> getPlayers() {
		return (LinkedList<Player>) players;
	}
	public void sendLobbyMessages(String...strings) {
		for(String s : strings) for(Player p : players) p.sendMessage("§a"+s);
	}
	public void playLobbySound(Sound sound) {
		for(Player p : players) p.playSound(p.getLocation(), sound, 1f, 1f);
	}
	
}
