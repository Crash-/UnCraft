package com.Crash.UnCraft;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

public class UnCraft extends JavaPlugin {

	private static Logger logger = Logger.getLogger("Minecraft");
	private PluginDescriptionFile pdf = getDescription();
	private File file;
	private Configuration config;
	private UnCraftBlockListener BlockListener = new UnCraftBlockListener(this);
	private List<Integer> blockedItems = new ArrayList<Integer>();
	private List<Integer> valuableItems = new ArrayList<Integer>();
	
	public static void log(Level l, String s){
		
		logger.log(l, "[UnCraft] " + s);
		
	}
	
	public static void log(String s){
		
		log(Level.INFO, s);
		
	}
	
	@Override
	public void onDisable() {

		log("UnCraft v" + pdf.getVersion() + " disabled.");
		
	}
	
	public void loadValuables(){
		
		config.load();
		
		List<Object> blocked = config.getList("blocked-items");
		
		if(blocked != null)
			for(Object o : blocked)
				if(o instanceof Integer)
					blockedItems.add((Integer)o);
		
		List<Object> valuables = config.getList("valuable-items");
		
		if(valuables != null)
			for(Object o : valuables)
				if(o instanceof Integer)
					valuableItems.add((Integer)o);
		
	}
	
	public boolean blockedItem(int item){
		
		for(Integer i : blockedItems)
			if(item == i)
				return true;
		
		return false;
		
	}
	
	public boolean matchItem(ItemStack item){
		
		for(Integer i : valuableItems)
			if(i == item.getTypeId())
				return true;
		
		return false;
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void onEnable() {
		
		file = new File(getDataFolder() + "/config.yml");
		
		getDataFolder().mkdir();
		boolean setValues = !file.exists();
		
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		config = new Configuration(file);
		if(setValues){
			
			config.setProperty("blocked-items", new ArrayList());
			List list = new ArrayList();
			list.add(263);
			list.add(264);
			list.add(265);
			list.add(266);
			config.setProperty("valuable-items", list);
			config.save();
			
		}
		pdf = getDescription();
		
		getServer().getPluginManager().registerEvent(Event.Type.BLOCK_DISPENSE, BlockListener, Event.Priority.Normal, this);
		getServer().getPluginManager().registerEvent(Event.Type.BLOCK_BREAK, BlockListener, Event.Priority.Normal, this);
		
		loadValuables();
		
		log("UnCraft v" + pdf.getVersion() + " enabled, by Crash");
		
	}

}