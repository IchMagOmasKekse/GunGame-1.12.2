package me.xxfreakdevxx.de.gungame.mapeditor;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import me.xxfreakdevxx.de.gungame.GunGame;
import me.xxfreakdevxx.de.gungame.lobby.GGMap;

public class EditorManager implements Listener {
	public GunGame pl;
	public HashMap<Player, EditorSession> sessions = new HashMap<Player, EditorSession>();
	public EditorManager(GunGame pl) {
		this.pl = pl;
		this.pl.getServer().getPluginManager().registerEvents(this, pl);
	}
	public boolean addEditorSession(Player p, GGMap map) {
		if(!sessions.containsKey(p)) sessions.put(p, new EditorSession(p, map));
		else return false;
		return true;
	}
	public boolean closeEditorSession(Player p) {
		if(sessions.containsKey(p)){
			p.teleport(sessions.get(p).getPrevLocation());
			sessions.remove(p);
		}
		else return false;
		return true;
	}
	
	@EventHandler public void onInteract(PlayerInteractEvent e) { for(Player p : sessions.keySet()) sessions.get(p).onInteract(e); }
	
}
