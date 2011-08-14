package com.Crash.UnCraft;

import java.lang.reflect.Field;
import java.util.ArrayList;
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
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.material.MaterialData;

public class UnCraftBlockListener extends BlockListener {

	public List<ItemStack> getItemList(ShapedRecipes sr){
		
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
	public List<ItemStack> getItemList(ShapelessRecipes sr){
		
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

			if(type == event.getItem().getTypeId()){
				
				recipe = true;

				if(o instanceof ShapelessRecipes)
					droplist = getItemList((ShapelessRecipes)o); 
				else
					droplist = getItemList((ShapedRecipes)o);
				
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
