package com.craftinggamertom.main;

import org.apache.logging.log4j.Level;


import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.*;

public class CommonProxy {

	public void registerRenderInfo()
	{
		
	}
    public void preInit(FMLPreInitializationEvent e) 
    {

    }

    public void init(FMLInitializationEvent e) 
    {
    	NetworkRegistry.INSTANCE.registerGuiHandler("greengrow", new GUIHandler());
    }

    public void postInit(FMLPostInitializationEvent e) 
    {

    }
}