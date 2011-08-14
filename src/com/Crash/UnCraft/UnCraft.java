package com.Crash.UnCraft;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class UnCraft extends JavaPlugin {

	private static Logger logger = Logger.getLogger("Minecraft");
	private UnCraftBlockListener BlockListener = new UnCraftBlockListener();
	
	public static void log(Level l, String s){
		
		logger.log(l, "[UnCraft] " + s);
		
	}
	
	public static void log(String s){
		
		log(Level.INFO, s);
		
	}
	
	@Override
	public void onDisable() {

		
		
	}

	@Override
	public void onEnable() {
		
		PluginDescriptionFile pdf = getDescription();
		
		getServer().getPluginManager().registerEvent(Event.Type.BLOCK_DISPENSE, BlockListener, Event.Priority.Normal, this);
		
		log("UnCraft v" + pdf.getVersion() + " enabled, by Crash");
		
	}

}