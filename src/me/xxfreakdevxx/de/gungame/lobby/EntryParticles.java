package me.xxfreakdevxx.de.gungame.lobby;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import me.xxfreakdevxx.de.gungame.GunGame;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;

public class EntryParticles {
	
	private static double angle = 1;
	private static int parDir = 1;
	private static int radDir = 1;
	public static Location mid = new Location(Bukkit.getWorld("world"), 233.5, 76, 413.5);
	public static Location origin = new Location(Bukkit.getWorld("world"), 233, 76, 413);	
	public static void nextParticle() {
		if(Bukkit.getServer().getOnlinePlayers().size() != 0) {
			drawCircle();
		}
	}
	
	private static float radius = 1f;
	private static float parY = 0.0f;
	private static boolean applyY = false;
	private static boolean applyRadius = false;
	
	public static void drawCircle() {
		Location loc = mid.clone();
		float x = radius*(float)Math.sin(angle);
		float z = radius*(float)Math.cos(angle);
		GunGame.playParticle(new PacketPlayOutWorldParticles(EnumParticle.FIREWORKS_SPARK,true,
				(float)loc.getX()+x,
				(float)loc.getY()+parY,
				(float)loc.getZ()+z,0,0,0,1,0));
		

		angle+=0.25;
		if(angle==360)angle=0;
		
		if(applyY){
			if(parY >= 3.0f) parDir=-1;
			else if(parY <= 0.0f) parDir=1;
			if(parDir == 1) parY+=0.1f;
			else if(parDir == -1) parY-=0.1f;
		}
		
		if(applyRadius) {			
			if(radius >= 1.0f) radDir = -1;
			else if(radius <= 0.2f) radDir = 1;
			
			if(radDir == 1) radius += 0.1f;
			else if(radDir == -1) radius -= 0.1f;
		}
	}
	
}
