package com.craftinggamertom.main;

import com.craftinggamertom.block.MBlocks;
import com.craftinggamertom.block.PlasticBarrel;
import com.craftinggamertom.creativeTabs.MCreativeTabs;
import com.craftinggamertom.item.MItems;
import com.craftinggamertom.lib.RefStrings;
import com.craftinggamertom.tileEntities.ADTTileEntity;
import com.craftinggamertom.tileEntities.DTTileEntity;
import com.craftinggamertom.tileEntities.PBTileEntity;
import com.craftinggamertom.tileEntities.VTileEntity;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.Mod.Instance;

@Mod(modid = RefStrings.MODID , name = RefStrings.NAME , version = RefStrings.VERSION)

public class MainRegistry {
	
	@SidedProxy(clientSide = RefStrings.CLIENTSIDE, serverSide = RefStrings.SERVERSIDE)
	public static CommonProxy proxy;
	@EventHandler
	public static void PreLoad(FMLPreInitializationEvent PreEvent)
	{
		MCreativeTabs.initializeTabs();
		MBlocks.mainRegistry();
		MItems.mainRegistry();
		CraftingManager.mainRegistry();
	
		proxy.init(null);
		proxy.registerRenderInfo();
		
	}
	@EventHandler
	public static void load(FMLInitializationEvent event)
	{
		GameRegistry.registerTileEntity(PBTileEntity.class, "PlasticBarrel");
		GameRegistry.registerTileEntity(VTileEntity.class, "Vacuum");
		GameRegistry.registerTileEntity(DTTileEntity.class, "DryTable");
		GameRegistry.registerTileEntity(ADTTileEntity.class, "AdvDryTable");

	}
	@EventHandler
	public static void PostLoad(FMLPostInitializationEvent PostEvent)
	{
		
		System.out.println("****************************************");
		System.out.println("****************************************");
		System.out.println("*             GREEN GROW+              *");
		System.out.println("****************************************");
		System.out.println("****************************************");
		System.out.println("*   THIS MOD IS NOT INTENDED FOR USE   *");
		System.out.println("*   OUTSIDE OF BIG GAMING'S MODPACKS   *");
		System.out.println("*  YOU MAY NOT DISTRIBUTE OR USE THIS  *");
		System.out.println("* OR ANY OTHER BIG GAMING MOD WITHOUT  *");
		System.out.println("*  BIG GAMING and CRAFTINGGAMERTOM'S   *");
		System.out.println("*        PERMISSION. THANK YOU.        *");
		System.out.println("****************************************");
		System.out.println("****************************************");
		System.out.println("*  IF YOU'RE READING THIS AND THIS IS  *");
		System.out.println("*   NOT A BIG GAMING MODPACK, PLEASE   *");
		System.out.println("*         CONTACT THE OWNER ON         *");
		System.out.println("*        BigGamingOfficial.com         *");
		System.out.println("****************************************");
		System.out.println("****************************************");
		System.out.println("For Author: Version: " + RefStrings.VERSION);
		System.out.println("****************************************");
		System.out.println("****************************************");
		
		
	}


}
