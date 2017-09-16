package com.craftinggamertom.block;

import com.craftinggamertom.creativeTabs.MCreativeTabs;
import com.craftinggamertom.lib.RefStrings;
import com.craftinggamertom.tileEntities.PBTileEntity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class PBStandby extends PlasticBarrel{
    @SideOnly(Side.CLIENT)
    private IIcon field_150029_a;
    @SideOnly(Side.CLIENT)
    private IIcon field_150028_b;
    @SideOnly(Side.CLIENT)
    private IIcon field_150030_M;
    
		public PBStandby(Material material){
			super(material);
			this.setBlockName("Plastic_Barrel_Standby");
			this.setBlockTextureName("pbs");
	        this.setCreativeTab(MCreativeTabs.greengrow);
		}
		@Override
		public void onBlockPlacedBy(World world, int xCord, int yCord, int zCord, EntityLivingBase placer, ItemStack stack) {
		    if (stack.hasDisplayName()) {
		        ((PBTileEntity) world.getTileEntity(xCord, yCord, zCord)).setCustomName(stack.getDisplayName());
		    }
		    super.onBlockPlacedBy(world, xCord, yCord, zCord, placer, stack);
		}
		/**
		 * Gets the block's texture. Args: side, meta
		 */
		@SideOnly(Side.CLIENT)
		public IIcon getIcon(int p_149691_1_, int p_149691_2_)
		{
		    return p_149691_1_ == 1 ? this.field_150028_b : (p_149691_1_ == 0 ? this.field_150030_M : this.blockIcon);
		}

		@SideOnly(Side.CLIENT)
		public void registerBlockIcons(IIconRegister p_149651_1_)
		{
		    this.field_150029_a = p_149651_1_.registerIcon(RefStrings.MODID + ":pb_inner");
		    this.field_150028_b = p_149651_1_.registerIcon(RefStrings.MODID + ":pb_top");
		    this.field_150030_M = p_149651_1_.registerIcon(RefStrings.MODID + ":pb_bottom");
		    this.blockIcon = p_149651_1_.registerIcon(RefStrings.MODID + ":pb_side");
		    //this.blockIcon = p_149651_1_.registerIcon(this.getTextureName() + "_side");
		}
}
