package com.craftinggamertom.main;

import com.craftinggamertom.containers.ContainerAdvDryTable;
import com.craftinggamertom.containers.ContainerDryTable;
import com.craftinggamertom.containers.ContainerPlasticBarrel;
import com.craftinggamertom.containers.ContainerVacuum;
import com.craftinggamertom.gui.AdvDryTableGUI;
import com.craftinggamertom.gui.DryTableGUI;
import com.craftinggamertom.gui.PlasticBarrelGUI;
import com.craftinggamertom.gui.VacuumGUI;
import com.craftinggamertom.tileEntities.ADTTileEntity;
import com.craftinggamertom.tileEntities.DTTileEntity;
import com.craftinggamertom.tileEntities.PBTileEntity;
import com.craftinggamertom.tileEntities.VTileEntity;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GUIHandler implements IGuiHandler {

	public static final int PB_TILE_ENTITY = 0;
	public static final int V_TILE_ENTITY = 1;
	public static final int DT_TILE_ENTITY = 2;
	public static final int ADT_TILE_ENTITY = 3;
	
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    	TileEntity tileentity = world.getTileEntity(x, y, z);
    	if(ID == PB_TILE_ENTITY){
    		return new ContainerPlasticBarrel(player.inventory, (PBTileEntity) tileentity);
    	}
    	if(ID == V_TILE_ENTITY)
    	{
    		return new ContainerVacuum(player.inventory, (VTileEntity) tileentity);
    	}
    	if(ID == DT_TILE_ENTITY)
    	{
    		return new ContainerDryTable(player.inventory, (DTTileEntity) tileentity);
    	}
    	if(ID == ADT_TILE_ENTITY)
    	{
    		return new ContainerAdvDryTable(player.inventory, (ADTTileEntity) tileentity);
    	}

    	return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

    	TileEntity tileentity = world.getTileEntity(x, y, z);
    	if(ID == PB_TILE_ENTITY)
    	{
    		return new PlasticBarrelGUI(player.inventory, (PBTileEntity) tileentity);
    	}
    	if(ID == V_TILE_ENTITY)
    	{
    		return new VacuumGUI(player.inventory, (VTileEntity) tileentity);
    	}
    	if(ID == DT_TILE_ENTITY)
    	{
    		return new DryTableGUI(player.inventory, (DTTileEntity) tileentity);
    	}
    	if(ID == ADT_TILE_ENTITY)
    	{
    		return new AdvDryTableGUI(player.inventory, (ADTTileEntity) tileentity);
    	}
        return null;
    }
}