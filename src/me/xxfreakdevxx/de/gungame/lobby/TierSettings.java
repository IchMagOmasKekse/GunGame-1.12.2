package me.xxfreakdevxx.de.gungame.lobby;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TierSettings {
	
	public static void setInventory(GGPlayer player) {
		Player p = player.player;
		Inventory inv = p.getInventory();
		switch(player.tier) {
		case 0:
			inv.clear();
			p.getInventory().setHeldItemSlot(4);
			inv.setItem(4, new ItemStack(Material.WOOD_SWORD));
			break;
		case 1:
			inv.clear();
			inv.setItem(4, new ItemStack(Material.STONE_SWORD));
			break;
		case 2:
			inv.clear();
			inv.setItem(4, new ItemStack(Material.GOLD_SWORD));
			break;
		case 3:
			inv.clear();
			inv.setItem(4, new ItemStack(Material.IRON_SWORD));
			break;
		case 4:
			inv.clear();
			inv.setItem(4, new ItemStack(Material.DIAMOND_SWORD));
			break;
			default:
				inv.clear();
				inv.setItem(4, new ItemStack(Material.DIAMOND_SWORD));
				break;
		}
		p.getInventory().setContents(inv.getContents());
	}
	
}
