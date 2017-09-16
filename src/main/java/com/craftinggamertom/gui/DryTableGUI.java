package com.craftinggamertom.gui;

import org.lwjgl.opengl.GL11;

import com.craftinggamertom.containers.ContainerDryTable;
import com.craftinggamertom.containers.ContainerVacuum;
import com.craftinggamertom.lib.RefStrings;
import com.craftinggamertom.tileEntities.DTTileEntity;
import com.craftinggamertom.tileEntities.PBTileEntity;
import com.craftinggamertom.tileEntities.VTileEntity;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import net.minecraft.client.renderer.texture.TextureManager;
public class DryTableGUI extends GuiContainer{
	private IInventory playerInv;
	private DTTileEntity te;
	public final ResourceLocation texture = new ResourceLocation(RefStrings.MODID, "textures/gui/dtgui.png"); //Create/rename location and file

public DryTableGUI(InventoryPlayer inventory, DTTileEntity drytable) {
	super(new ContainerDryTable(inventory, drytable)); //create container
	
	this.playerInv = inventory;
	this.te = drytable;
	
	xSize = 176;
	ySize = 166;
}

	/**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
@Override
protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
{
    String s = this.te.hasCustomInventoryName() ? this.te.getInventoryName() : I18n.format(this.te.getInventoryName(), new Object[0]);
    this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
    this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
}
@Override
protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
{
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    this.mc.renderEngine.bindTexture(texture);
    int k = (this.width - this.xSize) / 2;
    int l = (this.height - this.ySize) / 2;
    this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

    this.drawTexturedModalRect(k + 95, l + 18 + 0, 176, 0, 14, 0);
    
    if (this.te.isInProgress())
    {
        // int i1 = this.te.getLightValueScaled(13);
        this.drawTexturedModalRect(k + 95, l + 20, 176, 0, 14, 14);
    }
    if (!this.te.isInProgress()) {
    	this.drawTexturedModalRect(k + 95, l + 20, 176, 0, 14, 0);
    }
    if (this.te.timeDrying > 0) {
        int i1 = this.te.getDryProgressScaled(24);
        this.drawTexturedModalRect(k + 91, l + 38, 176, 14, i1 + 1, 16);
        // this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
    }
    
}
}
