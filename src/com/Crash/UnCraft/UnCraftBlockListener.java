package com.Crash.UnCraft;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.server.CraftingManager;
import net.minecraft.server.EntityItem;
import net.minecraft.server.InventoryCrafting;
import net.minecraft.server.ShapedRecipes;
import net.minecraft.server.ShapelessRecipes;
import net.minecraft.server.TileEntityDispenser;
import net.minecraft.server.World;

import org.bukkit.block.Dispenser;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.inventory.CraftRecipe;
import org.bukkit.entity.Item;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.material.MaterialData;

public class UnCraftBlockListener extends BlockListener {

	private UnCraft plugin;
	
	public UnCraftBlockListener(UnCraft instance){
		
		plugin = instance;
		
	}
	
	private List<ItemStack> getItemList(ShapedRecipes sr){
		
		Field field = null;
		
		try {
			field = ShapedRecipes.class.getDeclaredField("d");
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		field.setAccessible(true);
		net.minecraft.server.ItemStack[] items = null;
		
		try {
			items = (net.minecraft.server.ItemStack[])field.get(sr);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<ItemStack> itemlist = new ArrayList<ItemStack>();
		
		for(int i = 0; i < items.length; i++){
			
			net.minecraft.server.ItemStack item = items[i];
			
			if(item == null)
				continue;
			
			boolean found = false;
			
			for(ItemStack listitem : itemlist){
				
				if(item.id == listitem.getTypeId()){
					
					listitem.setAmount(listitem.getAmount() + 1);
					found = true;
					break;
					
				}
				
			}
			
			if(!found)
				itemlist.add(new ItemStack(item.id, item.count, (short)item.damage));
			
		}
		
		field.setAccessible(false);
		
		return itemlist;
		
	}

	@SuppressWarnings("unchecked")
	private List<ItemStack> getItemList(ShapelessRecipes sr){
		
		Field field = null;
		
		try {
			field = ShapelessRecipes.class.getDeclaredField("b");
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		field.setAccessible(true);
		
		List<net.minecraft.server.ItemStack> list = null;
		
		try {
			list = (List<net.minecraft.server.ItemStack>)field.get(sr);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<ItemStack> retlist = new ArrayList<ItemStack>();
		
		for(net.minecraft.server.ItemStack item : list){
			
			boolean found = false;
			
			for(ItemStack listitem : retlist){
				
				if(item.id == listitem.getTypeId()){
					
					listitem.setAmount(listitem.getAmount() + 1);
					found = true;
					break;
					
				}
				
			}
			
			if(!found)
				retlist.add(new ItemStack(item.id, item.count, (short)item.damage));
			
		}
		
		field.setAccessible(false);
		
		return retlist;
		
	}
	
	public float getDurabilityRate(ItemStack item){
		
		switch(item.getTypeId()){
		
			case 268://Wood tools
			case 269:
			case 270:
			case 271:
			case 290:
				return (float)(60 - item.getDurability()) / 60;
				
			case 272://Stone tools
			case 273:
			case 274:
			case 275:
			case 291:
				return (float)(132 - item.getDurability()) / 132;
				
			case 256://Iron tools
			case 257:
			case 258:
			case 267:
			case 292:
				return (float)(251 - item.getDurability()) / 251;
				
			case 283://Gold tools
			case 284:
			case 285:
			case 286:
			case 294:
				return (float)(30 - item.getDurability()) / 30;
				
			case 276://Diamond tools
			case 277:
			case 278:
			case 279:
			case 293:
				return (float)(1562 - item.getDurability()) / 1562;
				
			case 298://Leather armor
				return (float)(34 - item.getDurability()) / 34;
			case 299:
				return (float)(49 - item.getDurability()) / 49;
			case 300:
				return (float)(46 - item.getDurability()) / 46;
			case 301:
				return (float)(40 - item.getDurability()) / 40;
				
			case 302://Chainmail armor
				return (float)(67 - item.getDurability()) / 67;
			case 303:
				return (float)(96 - item.getDurability()) / 96;
			case 304:
				return (float)(92 - item.getDurability()) / 92;
			case 305:
				return (float)(79 - item.getDurability()) / 79;
				
			case 306://Iron armor
				return (float)(136 - item.getDurability()) / 136;
			case 307:
				return (float)(192 - item.getDurability()) / 192;
			case 308:
				return (float)(184 - item.getDurability()) / 184;
			case 309:
				return (float)(160 - item.getDurability()) / 160;
				
			case 314://Gold armor
				return (float)(68 - item.getDurability()) / 68;
			case 315:
				return (float)(96 - item.getDurability()) / 96;
			case 316:
				return (float)(92 - item.getDurability()) / 92;
			case 317:
				return (float)(80 - item.getDurability()) / 80;
				
			case 310://Diamond armor
				return (float)(272 - item.getDurability()) / 272;
			case 311:
				return (float)(384 - item.getDurability()) / 384;
			case 312:
				return (float)(368 - item.getDurability()) / 368;
			case 313:
				return (float)(320 - item.getDurability()) / 320;
				
			case 259:
			case 346:
				return (float)(65 - item.getDurability()) / 65;
				
			case 359:
				return (float)(239 - item.getDurability()) / 239;
				
			default:
				return -1.0f;
		
		}
		
	}
	
	@SuppressWarnings("unused")
	private void splitList(float cutrate, List<ItemStack> list){
		
		List<ItemStack> valuables = new ArrayList<ItemStack>();
		
		for(ItemStack i : list)
			if(plugin.matchItem(i))
				valuables.add(i);
		
		int size = valuables.size();
		
		cutrate /= size;
		
		for(Iterator<ItemStack> iter = valuables.iterator(); iter.hasNext();){
			
			ItemStack i = iter.next();
			i.setAmount((int)(i.getAmount() * cutrate));
			if(i.getAmount() == 0)
				list.remove(i);
			
		}
			
	}
	
	@Override
	public void onBlockDispense(BlockDispenseEvent event){

		if(event.isCancelled() || event.getItem() == null)
			return;

		CraftingManager mgr = CraftingManager.getInstance();

		Inventory inventory = ((Dispenser)event.getBlock().getState()).getInventory();

		World world = ((CraftWorld)event.getBlock().getWorld()).getHandle();

		List<ItemStack> droplist = new ArrayList<ItemStack>();
		boolean recipe = false;

		for(int i = 0; i < mgr.b().size(); i++){

			Object o = mgr.b().get(i);
			int type; 
			if(o instanceof ShapedRecipes)
				type = ((ShapedRecipes)o).b().id;
			else
				type = ((ShapelessRecipes)o).b().id;

			if(type == event.getItem().getTypeId() && !plugin.blockedItem(type)){
				
				recipe = true;

				if(o instanceof ShapelessRecipes)
					droplist = getItemList((ShapelessRecipes)o); 
				else
					droplist = getItemList((ShapedRecipes)o);
				
				float cut = getDurabilityRate(event.getItem());
				
				splitList(cut, droplist);
				
				break;

			}

		}

		if(recipe){

			int x = event.getBlock().getX(), y = event.getBlock().getY(), z = event.getBlock().getZ();

			int l = world.getData(x, y, z);
			byte b0 = 0;
			byte b1 = 0;

			if (l == 3) {
				b1 = 1;
			} else if (l == 2) {
				b1 = -1;
			} else if (l == 5) {
				b0 = 1;
			} else {
				b0 = -1;
			}

			double d0 = (double) x + (double) b0 * 0.6D + 0.5D;
			double d1 = (double) y + 0.5D;
			double d2 = (double) z + (double) b1 * 0.6D + 0.5D;

			for(ItemStack i : droplist){

				EntityItem ei = new EntityItem(world, d0, d1 - 0.3D, d2, new net.minecraft.server.ItemStack(i.getTypeId(), i.getAmount(), i.getDurability()));
				ei.motX = event.getVelocity().getX();
				ei.motY = event.getVelocity().getY();
				ei.motY = event.getVelocity().getZ();
				world.addEntity(ei);

			}

			int slot = inventory.first(event.getItem().getTypeId());

			if(inventory.getContents()[slot].getAmount() == 1)
				inventory.setItem(slot, null);
			else
				inventory.getContents()[slot].setAmount(inventory.getContents()[slot].getAmount() - 1);

			event.setCancelled(true);

		}

	}
	

}
