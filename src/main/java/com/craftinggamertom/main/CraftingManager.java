package com.craftinggamertom.main;

import com.craftinggamertom.block.MBlocks;
import com.craftinggamertom.item.MItems;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import net.minecraft.item.ItemAxe;

public class CraftingManager {
	public static void mainRegistry()
	{
		addCraftingRec();
		addSmeltingRec();
	}
	public static void addCraftingRec()
	{
		//GameRegistry.addRecipe(new ItemStack(MItems.x), new Object[]{"", "", "", '', });
		
		//Shaped
		GameRegistry.addRecipe(new ItemStack(MItems.Grinder, 1), new Object[]{"XX ","   ","XX ", 'X', Items.iron_ingot});
		GameRegistry.addRecipe(new ItemStack(MItems.SilkScreen, 1), new Object[]{"YXY", "XXX", "YXY", 'X', Items.string, 'Y', Items.stick});
		GameRegistry.addRecipe(new ItemStack(MItems.MetalPipe, 1), new Object[]{"X X","X X","X X", 'X', Items.iron_ingot});
		GameRegistry.addRecipe(new ItemStack(MItems.MetalCap, 1), new Object[]{"XHX","X X","   ", 'X', Items.iron_ingot, 'H', Blocks.hopper});
		GameRegistry.addRecipe(new ItemStack(MItems.Filter, 1), new Object[]{"XXX", "XXX", "XXX", 'X', Items.string});
		GameRegistry.addRecipe(new ItemStack(MItems.Extractor, 1), new Object[]{" X ", " Y ", " Z ", 'X', MItems.MetalCap, 'Y', MItems.MetalPipe, 'Z', MItems.Filter});
		GameRegistry.addRecipe(new ItemStack(MItems.PackedExtractor, 1), new Object[]{"XXX", " Y ", "   ", 'X', MItems.FineBud, 'Y', MItems.Extractor});
		GameRegistry.addRecipe(new ItemStack(MItems.PackedExtractor, 1), new Object[]{"TTT", "TYT", "TTT", 'T', MItems.FineTrimmings, 'Y', MItems.Extractor});
		//make cocoa
		GameRegistry.addRecipe(new ItemStack(MItems.BrownieDough, 1), new Object[]{"SME", "BBB", "WCW", 'S', Items.sugar, 'M', Items.milk_bucket.setContainerItem(Items.bucket), 'E', Items.egg, 'B', MItems.FineBud, 'W', Items.wheat, 'C', Items.dye});
		GameRegistry.addRecipe(new ItemStack(MItems.CookieDough, 1), new Object[]{"WME", "BBB", "SSS", 'S', Items.sugar, 'M', Items.milk_bucket.setContainerItem(Items.bucket), 'E', Items.egg, 'B', MItems.FineBud, 'W', Items.wheat});
		GameRegistry.addRecipe(new ItemStack(MItems.OilBase, 1), new Object[]{" B ", " E ", "   ", 'B', MItems.Butane, 'E', MItems.PackedExtractor.setContainerItem(MItems.Extractor)});
		
		GameRegistry.addRecipe(new ItemStack(MBlocks.Vacuum, 1), new Object[]{"GGG", "GSG", "IRI", 'S', MItems.SilkScreen, 'I', Blocks.iron_block, 'R', Items.redstone, 'G', Blocks.glass});
		GameRegistry.addRecipe(new ItemStack(MBlocks.PBStandby, 1), new Object[]{"W W", "W W", "WWW", 'W', Blocks.wool});
		GameRegistry.addRecipe(new ItemStack(MBlocks.DryBench, 1), new Object[]{"WWW", "SSS", "WWW", 'W', Blocks.planks, 'S', Blocks.wooden_slab});
		GameRegistry.addRecipe(new ItemStack(MBlocks.AdvancedDryBench, 1), new Object[]{"DDD", "SSS", "DDD", 'D', MBlocks.DryBench, 'S', Blocks.wooden_slab});
		
		//Shapeless
		//Need to add damage to Grinder
		//test 8
		GameRegistry.addShapelessRecipe(new ItemStack(MItems.Spors, 1), new Object[]{new ItemStack(MItems.Shrooms)});
		
		GameRegistry.addShapelessRecipe(new ItemStack(MItems.FineTrimmings, 1), new Object[]{new ItemStack(MItems.Grinder.setContainerItem(MItems.Grinder)), MItems.CannabisLeaves, MItems.CannabisLeaves, MItems.CannabisLeaves, MItems.CannabisLeaves, MItems.CannabisLeaves, MItems.CannabisLeaves, MItems.CannabisLeaves, MItems.CannabisLeaves,});
		
		GameRegistry.addShapelessRecipe(new ItemStack(MItems.FineBud, 8), new Object[]{new ItemStack(MItems.Grinder.setContainerItem(MItems.Grinder)), MItems.GreenDriedCannabis, MItems.GreenDriedCannabis, MItems.GreenDriedCannabis, MItems.GreenDriedCannabis, MItems.GreenDriedCannabis, MItems.GreenDriedCannabis, MItems.GreenDriedCannabis, MItems.GreenDriedCannabis});
		GameRegistry.addShapelessRecipe(new ItemStack(MItems.FineBud, 8), new Object[]{new ItemStack(MItems.Grinder.setContainerItem(MItems.Grinder)), MItems.OrangeDriedCannabis, MItems.OrangeDriedCannabis, MItems.OrangeDriedCannabis, MItems.OrangeDriedCannabis, MItems.OrangeDriedCannabis, MItems.OrangeDriedCannabis, MItems.OrangeDriedCannabis, MItems.OrangeDriedCannabis});
		GameRegistry.addShapelessRecipe(new ItemStack(MItems.FineBud, 24), new Object[]{new ItemStack(MItems.Grinder.setContainerItem(MItems.Grinder)), MItems.PurpleDriedCannabis, MItems.PurpleDriedCannabis, MItems.PurpleDriedCannabis, MItems.PurpleDriedCannabis, MItems.PurpleDriedCannabis, MItems.PurpleDriedCannabis, MItems.PurpleDriedCannabis, MItems.PurpleDriedCannabis});
		
		GameRegistry.addShapelessRecipe(new ItemStack(MItems.FineBud, 1), new Object[]{new ItemStack(MItems.Grinder.setContainerItem(MItems.Grinder)), MItems.GreenDriedCannabis});
		GameRegistry.addShapelessRecipe(new ItemStack(MItems.FineBud, 1), new Object[]{new ItemStack(MItems.Grinder.setContainerItem(MItems.Grinder)), MItems.OrangeDriedCannabis});
		GameRegistry.addShapelessRecipe(new ItemStack(MItems.FineBud, 3), new Object[]{new ItemStack(MItems.Grinder.setContainerItem(MItems.Grinder)), MItems.PurpleDriedCannabis});
	}
	public static void addSmeltingRec()
	{
		GameRegistry.addSmelting(MItems.BrownieDough, new ItemStack(MItems.Brownie, 1), 5.0f);
		GameRegistry.addSmelting(MItems.CookieDough, new ItemStack(MItems.Cookie, 1), 5.0f);
		GameRegistry.addSmelting(new ItemStack(MItems.CocainePaste, 1), new ItemStack(MItems.Cocaine, 1), 5.0f);
	}
}
