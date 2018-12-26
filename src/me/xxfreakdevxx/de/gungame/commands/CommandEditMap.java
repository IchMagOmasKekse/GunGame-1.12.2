package me.xxfreakdevxx.de.gungame.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.xxfreakdevxx.de.gungame.GunGame;
import me.xxfreakdevxx.de.gungame.lobby.GGMap;

public class CommandEditMap implements CommandExecutor {
	
	private GunGame pl;
	
	public CommandEditMap(GunGame pl) {
		pl.getCommand("gg").setExecutor(this);
		this.pl = pl;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("server.gungame.use.commands.gg")) {
				switch(args.length) {
				case 0:
					sendCommandInfos(p);
					break;
				case 1:
					if(args[0].equalsIgnoreCase("exit")) {
						pl.getEditorManager().closeEditorSession(p);
					}else if(args[0].equalsIgnoreCase("edit")) {
						sendCommandInfos(p);
					}
					break;
				case 2:
					if(args[0].equalsIgnoreCase("edit")) {
						String mapname = args[1];
						try{
							GGMap m = GGMap.valueOf(mapname);
							pl.getEditorManager().addEditorSession(p, m);
							p.sendMessage("§7Editormodus aktiviert");
						}catch(Exception ex) {
							p.sendMessage("§cUnbekannter Map-Name");
							sendMapNames(p);
						}
					}
					break;
				}
			}else p.sendMessage(pl.noPerm);
		}
		return false;
	}
	public void sendCommandInfos(Player p) {
		p.sendMessage("§8[§7====-----§8[ §cGunGame Commands §8]§f-----====§8]");
		p.sendMessage("§7/gg edit <MapName> §8Editiere eine Map");
		p.sendMessage("§7/gg exit §8Verlasse den Editor-Modus");
	}
	public void sendMapNames(Player p) {
		p.sendMessage("§8[§7====-----§8[ §cGunGame Maps §8]§f-----====§8]");
		p.sendMessage("§7MAP_FORREST");
	}
}
