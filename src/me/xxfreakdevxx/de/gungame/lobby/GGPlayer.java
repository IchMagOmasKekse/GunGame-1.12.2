package me.xxfreakdevxx.de.gungame.lobby;

import org.bukkit.entity.Player;

public class GGPlayer {
	
	public Player player = null;
	public int tier = 0;
	
	public GGPlayer(Player player) {
		this.player = player;
	}
	
	public void nextLevel() {
		tier++;
		TierSettings.setInventory(this);
		player.sendMessage("Tier up! "+tier);
	}
	
}
