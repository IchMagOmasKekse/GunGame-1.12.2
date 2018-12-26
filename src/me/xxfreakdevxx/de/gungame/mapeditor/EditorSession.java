package me.xxfreakdevxx.de.gungame.mapeditor;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.xxfreakdevxx.de.gungame.lobby.GGMap;

public class EditorSession {
	
	private Player p;
	private Location prevLoc;
	private GGMap map;
	
	private final String spawnSetterPrefix = "§5Spawn-Setter - spawn #";
	
	public EditorSession(Player player, GGMap map) {
		this.p = player;
		this.map = map;
		this.prevLoc = p.getLocation();
		ItemStack item = new ItemStack(Material.MOB_SPAWNER);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(spawnSetterPrefix+"1");
		item.setItemMeta(meta);
		p.getInventory().setItem(0, item);
		p.teleport(map.getMapSpawn());
	}
	public Location getPrevLocation() {
		return prevLoc;
	}
	public void onInteract(PlayerInteractEvent e) {
		if(e.getPlayer() == this.p){
			if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if(p.getInventory().getItemInMainHand() != null) {
					ItemStack item = p.getInventory().getItemInMainHand();
					if(item.getType() == Material.MOB_SPAWNER) {
						if(item.hasItemMeta()) {
							ItemMeta meta = item.getItemMeta();
							if(meta.getDisplayName().contains(spawnSetterPrefix)) {
								String name = meta.getDisplayName();
								String[] a = name.split("#");
								int spawnNumber = Integer.parseInt(a[1]);
								Location loc = null;
								if(e.getClickedBlock() == null) {
									loc = e.getClickedBlock().getLocation().clone();
									loc.add(0,1,0);
									loc.setYaw(p.getLocation().getYaw());
									loc.setPitch(p.getLocation().getPitch());
								}else loc = p.getLocation();
								
								if(map.setSpawn(spawnNumber, loc)){
									e.setCancelled(true);
									p.sendMessage("§7Spawn für Platz §f"+spawnNumber+" §7wurde gesetzt");
								}
							}
						}
					}
				}
			}else if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
				if(p.isSneaking()) {
					e.setCancelled(true);
					ItemStack item = p.getInventory().getItemInMainHand();
					ItemMeta meta = item.getItemMeta();
					if(meta.getDisplayName().contains(spawnSetterPrefix)) {
						String name = meta.getDisplayName();
						String[] a = name.split("#");
						int spawnNumber = Integer.parseInt(a[1]);
						if(spawnNumber == map.getMaxPlayers()) spawnNumber=1;
						else spawnNumber++;
						meta.setDisplayName(spawnSetterPrefix+spawnNumber);
						item.setItemMeta(meta);
						p.getInventory().setItemInMainHand(item);
						p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG,1,1);
					}
				}
			}
		}
	}
}
