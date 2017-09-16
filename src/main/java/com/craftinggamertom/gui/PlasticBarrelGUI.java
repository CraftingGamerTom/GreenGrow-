package com.craftinggamertom.gui;

import org.lwjgl.opengl.GL11;

import com.craftinggamertom.containers.ContainerPlasticBarrel;
import com.craftinggamertom.lib.RefStrings;
import com.craftinggamertom.tileEntities.PBTileEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.block.BlockFurnace;

public class PlasticBarrelGUI extends GuiContainer {
	public final ResourceLocation texture = new ResourceLocation(RefStrings.MODID, "textures/gui/pbgui.png"); //Create/rename location and file
	private PBTileEntity pbTile;
public PlasticBarrelGUI(InventoryPlayer inventory, PBTileEntity plasticBarrel) {
	super(new ContainerPlasticBarrel(inventory, plasticBarrel)); //create container
	this.pbTile = plasticBarrel;
	
	xSize = 176;
	ySize = 166;
}
	/*
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	*/
	 /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
	@Override
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        String s = this.pbTile.hasCustomInventoryName() ? this.pbTile.getInventoryName() : I18n.format(this.pbTile.getInventoryName(), new Object[0]);
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(texture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

        if (this.pbTile.isBurning())
        {
            int i1 = this.pbTile.getBurnTimeRemainingScaled(13);
            this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 1);
            i1 = this.pbTile.getCookProgressScaled(24);
            this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
        }
    }
}