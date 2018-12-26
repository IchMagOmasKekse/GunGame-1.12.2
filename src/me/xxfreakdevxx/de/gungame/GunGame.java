package me.xxfreakdevxx.de.gungame;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.xxfreakdevxx.de.gungame.commands.CommandEditMap;
import me.xxfreakdevxx.de.gungame.lobby.EntryParticles;
import me.xxfreakdevxx.de.gungame.lobby.GGMap;
import me.xxfreakdevxx.de.gungame.lobby.Lobby;
import me.xxfreakdevxx.de.gungame.lobby.LobbyManager;
import me.xxfreakdevxx.de.gungame.mapeditor.EditorManager;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;

public class GunGame extends JavaPlugin {
	
	private static GunGame pl;
	private LobbyManager lobbyManager;
	private QueueManager queueManager;
	private EditorManager editorManager;
	private int join1x = 173;
	private int join1y = 67;
	private int join1z = 266;
	private int join2x = 166;
	private int join2y = 67;
	private int join2z = 266;
	private static boolean inMaintance = false;
	private HashMap<String, BukkitRunnable> timer = new HashMap<String, BukkitRunnable>();
	public String noPerm = "§cDu hast keine Berechtigung dazu!";
	
	/* MiniGame - Core-Klasse */
	@Override
	public void onEnable() {
		pl=this;
		this.lobbyManager = new LobbyManager(this);
		this.queueManager = new QueueManager();
		this.editorManager = new EditorManager(this);
		new JoinHandler(this);
		new CommandEditMap(this);
		dsfsd
		sdfsa
		sfa
		getLobbyManager().createLobby(new Lobby(GGMap.MAP_FORREST, "L#1", new Location(Bukkit.getWorld("world"), join1x, join1y, join1z)));
		getLobbyManager().createLobby(new Lobby(GGMap.MAP_FORREST, "L#2", new Location(Bukkit.getWorld("world"), join2x, join2y, join2z)));
		timer.put("Particler", new BukkitRunnable() { @Override public void run() {EntryParticles.nextParticle();}});
		timer.get("Particler").runTaskTimer(pl, 0, 1);
	}
	@Override
	public void onDisable() {
		
	}
	
	public QueueManager getQueueManager() { return this.queueManager; }
	public LobbyManager getLobbyManager() { return this.lobbyManager; }
	public EditorManager getEditorManager() { return this.editorManager; }
	public static boolean isInMaintance() {
		return inMaintance;
	}
	public static GunGame getInstance() {
		return pl;
	}
	
	public static void playParticle(PacketPlayOutWorldParticles packet) {
		for(Player p : Bukkit.getOnlinePlayers()) {
			((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
		}
	}
	
}
