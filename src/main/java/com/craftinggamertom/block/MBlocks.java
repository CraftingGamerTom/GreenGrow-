package com.craftinggamertom.block;

import com.craftinggamertom.creativeTabs.MCreativeTabs;
import com.craftinggamertom.lib.RefStrings;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.util.EnumHelper;

public class MBlocks {


	public static void mainRegistry()
	{
		initializeBlock();
		registerBlock();
	}
	//public static Item //ENTER NAME HERE;
	
	public static CannabisPlants GreenCannabisPlant;
	public static CannabisPlants GreenCannabisPlantTwo;
	public static CannabisPlants GreenCannabisPlantThree;
	public static CannabisPlants PurpleCannabisPlant;
	public static CannabisPlants PurpleCannabisPlantTwo;
	public static CannabisPlants PurpleCannabisPlantThree;
	public static CannabisPlants OrangeCannabisPlant;
	public static CannabisPlants OrangeCannabisPlantTwo;
	public static CannabisPlants OrangeCannabisPlantThree;
	public static Block CocaPlant;
	public static Block ShroomPlant;
	public static Block TobaccoPlant;
	
	public static Block DryBench;
	public static Block DryBenchOn;
	public static Block AdvancedDryBench;
	public static Block Vacuum;
	public static Block VacuumOn;
	public static Block PBInUse;
	public static Block PBStandby;
	
	//ToDo Add
	//public static GrowLamp;
	//public static Foil;
	//public static Hydroponix;
	
	
	public static void initializeBlock()
	{
		//toAdd --- NAME_HERE  = new NAME_HERE().setBlockName("NAME_HERE").setCreativeTab(MCreativeTabs.greengrow);
		
		GreenCannabisPlant = new GreenCannabisPlant();
		GreenCannabisPlantTwo = new GreenCannabisPlantTwo();
		GreenCannabisPlantThree = new GreenCannabisPlantThree();
		PurpleCannabisPlant = new PurpleCannabisPlant();
		PurpleCannabisPlantTwo = new PurpleCannabisPlantTwo();
		PurpleCannabisPlantThree = new PurpleCannabisPlantThree();
		OrangeCannabisPlant = new OrangeCannabisPlant();
		OrangeCannabisPlantTwo = new OrangeCannabisPlantTwo();
		OrangeCannabisPlantThree = new OrangeCannabisPlantThree();
		CocaPlant = new CocaPlant();
		ShroomPlant = new ShroomPlant();
		TobaccoPlant = new TobaccoPlant();
		PBInUse = new PBInUse(Material.gourd);
		PBStandby = new PBStandby(Material.gourd);
		Vacuum  = new Vacuum(false).setBlockName("Vacuum").setCreativeTab(MCreativeTabs.greengrow);
		VacuumOn = new Vacuum(true).setBlockName("VacuumOn");
		DryBench  = new DryBench(false).setBlockName("DryBench");
		DryBenchOn  = new DryBench(true).setBlockName("DryBenchOn");
		AdvancedDryBench  = new AdvancedDryBench().setBlockName("AdvancedDryBench");
		/*
		PlasticBarrel  = new PlasticBarrel().setBlockName("PlasticBarrel").setCreativeTab(MCreativeTabs.greengrow);
		*/

		
	}
	
	public static void registerBlock()
	{
		//toAdd --- GameRegistry.registerBlock(NAME_HERE, NAME_HERE.getUnlocalizedName());
		
		GameRegistry.registerBlock(GreenCannabisPlant, GreenCannabisPlant.getUnlocalizedName());
		GameRegistry.registerBlock(GreenCannabisPlantTwo, GreenCannabisPlantTwo.getUnlocalizedName());
		GameRegistry.registerBlock(GreenCannabisPlantThree, GreenCannabisPlantThree.getUnlocalizedName());
		GameRegistry.registerBlock(PurpleCannabisPlant, PurpleCannabisPlant.getUnlocalizedName());
		GameRegistry.registerBlock(PurpleCannabisPlantTwo, PurpleCannabisPlantTwo.getUnlocalizedName());
		GameRegistry.registerBlock(PurpleCannabisPlantThree, PurpleCannabisPlantThree.getUnlocalizedName());
		GameRegistry.registerBlock(OrangeCannabisPlant, OrangeCannabisPlant.getUnlocalizedName());
		GameRegistry.registerBlock(OrangeCannabisPlantTwo, OrangeCannabisPlantTwo.getUnlocalizedName());
		GameRegistry.registerBlock(OrangeCannabisPlantThree, OrangeCannabisPlantThree.getUnlocalizedName());
		GameRegistry.registerBlock(CocaPlant, CocaPlant.getUnlocalizedName());
		GameRegistry.registerBlock(ShroomPlant, ShroomPlant.getUnlocalizedName());
		GameRegistry.registerBlock(TobaccoPlant, TobaccoPlant.getUnlocalizedName());
		
		GameRegistry.registerBlock(PBInUse, PBInUse.getUnlocalizedName());
		GameRegistry.registerBlock(PBStandby, PBStandby.getUnlocalizedName());
		GameRegistry.registerBlock(Vacuum, Vacuum.getUnlocalizedName());
		GameRegistry.registerBlock(VacuumOn, VacuumOn.getUnlocalizedName());
		
		GameRegistry.registerBlock(DryBench, DryBench.getUnlocalizedName());
		GameRegistry.registerBlock(AdvancedDryBench, AdvancedDryBench.getUnlocalizedName());
		/* 
		 GameRegistry.registerBlock(PlasticBarrel, PlasticBarrel.getUnlocalizedName());
		*/
		
	}

}