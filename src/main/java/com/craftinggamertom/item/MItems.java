package com.craftinggamertom.item;

import com.craftinggamertom.block.MBlocks;
import com.craftinggamertom.creativeTabs.MCreativeTabs;
import com.craftinggamertom.lib.RefStrings;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemSeeds;
import net.minecraftforge.common.util.EnumHelper;

public class MItems {
	public static void mainRegistry()
	{
		initializeItem();
		registerItem();
	}
	
	public static ToolMaterial metal = EnumHelper.addToolMaterial("Metal", 2, 320, 5.0f, 1.0f, 0);
	//public static ToolMaterial metal1 = EnumHelper.addToolMaterial(name, harvestLevel, maxUses, efficiency, damage, enchantability);
	
	//public static Item //ENTER NAME HERE;
	
	public static Item CannaSeedGreen;
	public static Item CannaSeedPurple;
	public static Item CannaSeedOrange;
	public static Item CocaSeed;
	public static Item Spors;
	public static Item TobaccoSeed;
	
	public static Item GreenCannabis;
	public static Item PurpleCannabis;
	public static Item OrangeCannabis;
	public static Item Coca;
	public static Item Shrooms;
	public static Item Tobacco;
	
	public static Item GreenDriedCannabis;
	public static Item PurpleDriedCannabis;
	public static Item OrangeDriedCannabis;
	public static Item MagicMushrooms;
	
	public static Item CannabisLeaves;
	public static Item CannabisStems;
	public static Item Resin;
	public static Item Butane;
	
	public static Item Grinder;
	public static Item SilkScreen;
	public static Item MetalPipe;
	public static Item MetalCap;
	public static Item BrownieDough;
	public static Item CookieDough;
	public static Item Filter;
	public static Item Extractor;
	public static Item PackedExtractor;
	
	public static Item FineTrimmings;
	public static Item Hash;
	public static Item FineBud;
	public static Item CocainePaste;
	public static Item Cocaine;
	public static Item OilBase;

	public static Item Brownie;
	public static Item Cookie;
	public static Item Shatter;
	
	// ********* 38 Items on last update
	
	public static void initializeItem()
	{
		//ENTER NAME HERE  = new Item().setUnlocalizedName("//name").setTextureName(RefStrings.MODID + ":NAME_HERE").setCreativeTab(CreativeTabs.tab);
		
		//Seeds
		CannaSeedGreen = new ItemSeeds(MBlocks.GreenCannabisPlant, Blocks.farmland).setUnlocalizedName("CannaSeedGreen").setTextureName(RefStrings.MODID + ":seeds_cannabis_green").setCreativeTab(MCreativeTabs.greengrow);
		CannaSeedPurple = new ItemSeeds(MBlocks.PurpleCannabisPlant, Blocks.farmland).setUnlocalizedName("CannaSeedPurple").setTextureName(RefStrings.MODID + ":seeds_cannabis_purple").setCreativeTab(MCreativeTabs.greengrow);
		CannaSeedOrange = new ItemSeeds(MBlocks.OrangeCannabisPlant, Blocks.farmland).setUnlocalizedName("CannaSeedOrange").setTextureName(RefStrings.MODID + ":seeds_cannabis_orange").setCreativeTab(MCreativeTabs.greengrow);
		CocaSeed = new ItemSeeds(MBlocks.CocaPlant, Blocks.farmland).setUnlocalizedName("CocaSeed").setTextureName(RefStrings.MODID + ":seeds_coca").setCreativeTab(MCreativeTabs.greengrow);
		Spors = new ItemSeeds(MBlocks.ShroomPlant, Blocks.farmland).setUnlocalizedName("Spors").setTextureName(RefStrings.MODID + ":seeds_spors").setCreativeTab(MCreativeTabs.greengrow);
		TobaccoSeed = new ItemSeeds(MBlocks.TobaccoPlant, Blocks.farmland).setUnlocalizedName("TobaccoSeed").setTextureName(RefStrings.MODID + ":seeds_tobacco").setCreativeTab(MCreativeTabs.greengrow);
		
		GreenCannabis = new Item().setUnlocalizedName("GreenCannabis").setTextureName(RefStrings.MODID + ":cannabis_green").setCreativeTab(MCreativeTabs.greengrow);
		PurpleCannabis = new Item().setUnlocalizedName("PurpleCannabis").setTextureName(RefStrings.MODID + ":cannabis_purple").setCreativeTab(MCreativeTabs.greengrow);
		OrangeCannabis = new Item().setUnlocalizedName("OrangeCannabis").setTextureName(RefStrings.MODID + ":cannabis_orange").setCreativeTab(MCreativeTabs.greengrow);
		Coca = new Item().setUnlocalizedName("Coca").setTextureName(RefStrings.MODID + ":coca").setCreativeTab(MCreativeTabs.greengrow);
		Shrooms = new Item().setUnlocalizedName("Shrooms").setTextureName(RefStrings.MODID + ":shrooms").setCreativeTab(MCreativeTabs.greengrow);
		Tobacco = new Item().setUnlocalizedName("Tobacco").setTextureName(RefStrings.MODID + ":tobacco").setCreativeTab(MCreativeTabs.greengrow);
		
		GreenDriedCannabis = new Item().setUnlocalizedName("GreenDriedCannabis").setTextureName(RefStrings.MODID + ":cannabis_green_dry").setCreativeTab(MCreativeTabs.greengrow);
		PurpleDriedCannabis = new Item().setUnlocalizedName("PurpleDriedCannabis").setTextureName(RefStrings.MODID + ":cannabis_purple_dry").setCreativeTab(MCreativeTabs.greengrow);
		OrangeDriedCannabis = new Item().setUnlocalizedName("OrangeDriedCannabis").setTextureName(RefStrings.MODID + ":cannabis_orange_dry").setCreativeTab(MCreativeTabs.greengrow);
		MagicMushrooms = new Item().setUnlocalizedName("MagicMushrooms").setTextureName(RefStrings.MODID + ":magic_mushrooms").setCreativeTab(MCreativeTabs.greengrow);
		
		CannabisLeaves = new Item().setUnlocalizedName("CannabisLeaves").setTextureName(RefStrings.MODID + ":cannabis_leaves").setCreativeTab(MCreativeTabs.greengrow);
		CannabisStems = new Item().setUnlocalizedName("CannabisStems").setTextureName(RefStrings.MODID + ":cannabis_stem").setCreativeTab(MCreativeTabs.greengrow);
		Resin = new Item().setUnlocalizedName("Resin").setTextureName(RefStrings.MODID + ":resin").setCreativeTab(MCreativeTabs.greengrow);
		Butane = new Item().setUnlocalizedName("Butane").setTextureName(RefStrings.MODID + ":butane").setCreativeTab(MCreativeTabs.greengrow);
		
		Grinder = new Item().setUnlocalizedName("Grinder").setTextureName(RefStrings.MODID + ":grinder").setCreativeTab(MCreativeTabs.greengrow);
		SilkScreen = new Item().setUnlocalizedName("SilkScreen").setTextureName(RefStrings.MODID + ":silk_screen").setCreativeTab(MCreativeTabs.greengrow);
		MetalPipe = new Item().setUnlocalizedName("MetalPipe").setTextureName(RefStrings.MODID + ":metal_pipe").setCreativeTab(MCreativeTabs.greengrow);
		MetalCap = new Item().setUnlocalizedName("MetalCap").setTextureName(RefStrings.MODID + ":metal_cap").setCreativeTab(MCreativeTabs.greengrow);
		BrownieDough = new Item().setUnlocalizedName("BrownieDough").setTextureName(RefStrings.MODID + ":brownie_dough").setCreativeTab(MCreativeTabs.greengrow);
		CookieDough = new Item().setUnlocalizedName("CookieDough").setTextureName(RefStrings.MODID + ":cookie_dough").setCreativeTab(MCreativeTabs.greengrow);
		Filter = new Item().setUnlocalizedName("Filter").setTextureName(RefStrings.MODID + ":filter").setCreativeTab(MCreativeTabs.greengrow);
		Extractor = new Item().setUnlocalizedName("Extractor").setTextureName(RefStrings.MODID + ":extractor").setCreativeTab(MCreativeTabs.greengrow);
		PackedExtractor = new Item().setUnlocalizedName("PackedExtractor").setTextureName(RefStrings.MODID + ":extractor_packed").setCreativeTab(MCreativeTabs.greengrow);
		
		FineTrimmings = new Item().setUnlocalizedName("FineTrimmings").setTextureName(RefStrings.MODID + ":fine_trimmings").setCreativeTab(MCreativeTabs.greengrow);
		Hash = new Item().setUnlocalizedName("Hash").setTextureName(RefStrings.MODID + ":hash").setCreativeTab(MCreativeTabs.greengrow);
		FineBud = new Item().setUnlocalizedName("FineBud").setTextureName(RefStrings.MODID + ":fine_bud").setCreativeTab(MCreativeTabs.greengrow);
		CocainePaste = new Item().setUnlocalizedName("CocainePaste").setTextureName(RefStrings.MODID + ":cocaine_paste").setCreativeTab(MCreativeTabs.greengrow);
		Cocaine = new Item().setUnlocalizedName("Cocaine").setTextureName(RefStrings.MODID + ":cocaine").setCreativeTab(MCreativeTabs.greengrow);
		OilBase = new Item().setUnlocalizedName("OilBase").setTextureName(RefStrings.MODID + ":oil_base").setCreativeTab(MCreativeTabs.greengrow);

		Brownie = new Item().setUnlocalizedName("Brownie").setTextureName(RefStrings.MODID + ":brownie").setCreativeTab(MCreativeTabs.greengrow);
		Cookie = new Item().setUnlocalizedName("Cookie").setTextureName(RefStrings.MODID + ":cookie").setCreativeTab(MCreativeTabs.greengrow);
		Shatter = new Item().setUnlocalizedName("Shatter").setTextureName(RefStrings.MODID + ":shatter").setCreativeTab(MCreativeTabs.greengrow);

	}
	
	public static void registerItem()
	{
		//GameRegistry.registerItem(, .getUnlocalizedName());
		
		GameRegistry.registerItem(CannaSeedGreen, CannaSeedGreen.getUnlocalizedName());
		GameRegistry.registerItem(CannaSeedPurple, CannaSeedPurple.getUnlocalizedName());
		GameRegistry.registerItem(CannaSeedOrange, CannaSeedOrange.getUnlocalizedName());
		GameRegistry.registerItem(CocaSeed, CocaSeed.getUnlocalizedName());
		GameRegistry.registerItem(Spors, Spors.getUnlocalizedName());
		GameRegistry.registerItem(TobaccoSeed, TobaccoSeed.getUnlocalizedName());
		
		GameRegistry.registerItem(GreenCannabis, GreenCannabis.getUnlocalizedName());
		GameRegistry.registerItem(PurpleCannabis, PurpleCannabis.getUnlocalizedName());
		GameRegistry.registerItem(OrangeCannabis, OrangeCannabis.getUnlocalizedName());
		GameRegistry.registerItem(Coca, Coca.getUnlocalizedName());
		GameRegistry.registerItem(Shrooms, Shrooms.getUnlocalizedName());
		GameRegistry.registerItem(Tobacco, Tobacco.getUnlocalizedName());
		
		GameRegistry.registerItem(GreenDriedCannabis, GreenDriedCannabis.getUnlocalizedName());
		GameRegistry.registerItem(PurpleDriedCannabis, PurpleDriedCannabis.getUnlocalizedName());
		GameRegistry.registerItem(OrangeDriedCannabis, OrangeDriedCannabis.getUnlocalizedName());
		GameRegistry.registerItem(MagicMushrooms, MagicMushrooms.getUnlocalizedName());
		
		GameRegistry.registerItem(CannabisLeaves, CannabisLeaves.getUnlocalizedName());
		GameRegistry.registerItem(CannabisStems, CannabisStems.getUnlocalizedName());
		GameRegistry.registerItem(Resin, Resin.getUnlocalizedName());
		GameRegistry.registerItem(Butane, Butane.getUnlocalizedName());
		
		GameRegistry.registerItem(Grinder, Grinder.getUnlocalizedName());
		GameRegistry.registerItem(SilkScreen, SilkScreen.getUnlocalizedName());
		GameRegistry.registerItem(MetalPipe, MetalPipe.getUnlocalizedName());
		GameRegistry.registerItem(MetalCap, MetalCap.getUnlocalizedName());
		GameRegistry.registerItem(BrownieDough, BrownieDough.getUnlocalizedName());
		GameRegistry.registerItem(CookieDough, CookieDough.getUnlocalizedName());
		GameRegistry.registerItem(Filter, Filter.getUnlocalizedName());
		GameRegistry.registerItem(Extractor, Extractor.getUnlocalizedName());
		GameRegistry.registerItem(PackedExtractor, PackedExtractor.getUnlocalizedName());
		
		GameRegistry.registerItem(FineTrimmings, FineTrimmings.getUnlocalizedName());
		GameRegistry.registerItem(Hash, Hash.getUnlocalizedName());
		GameRegistry.registerItem(FineBud, FineBud.getUnlocalizedName());
		GameRegistry.registerItem(CocainePaste, CocainePaste.getUnlocalizedName());
		GameRegistry.registerItem(Cocaine, Cocaine.getUnlocalizedName());
		GameRegistry.registerItem(OilBase, OilBase.getUnlocalizedName());
		
		
		GameRegistry.registerItem(Brownie, Brownie.getUnlocalizedName());
		GameRegistry.registerItem(Cookie, Cookie.getUnlocalizedName());
		GameRegistry.registerItem(Shatter, Shatter.getUnlocalizedName());
		
		

		

		
		
		
	}

}
