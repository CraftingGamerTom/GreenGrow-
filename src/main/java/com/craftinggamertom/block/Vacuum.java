package com.craftinggamertom.block;

import java.util.Random;

import com.craftinggamertom.creativeTabs.MCreativeTabs;
import com.craftinggamertom.lib.RefStrings;
import com.craftinggamertom.main.GUIHandler;
import com.craftinggamertom.tileEntities.PBTileEntity;
import com.craftinggamertom.tileEntities.VTileEntity;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import net.minecraft.block.BlockFurnace;


public class Vacuum extends Block implements ITileEntityProvider{
//Vacuum converts FineTrimmings and FineBud to BaseOil using butane after some time
	//Find classes to allow this
	//create butane to drain over time
	
    @SideOnly(Side.CLIENT)
    public IIcon field_150029_a; 
    @SideOnly(Side.CLIENT)
    public IIcon field_150028_b; 
    @SideOnly(Side.CLIENT)
    public IIcon field_150030_M; 
    @SideOnly(Side.CLIENT)
    private IIcon field_149935_N;
    @SideOnly(Side.CLIENT)
    private IIcon field_149936_O;
    
    final private boolean isOn;
    private static boolean field_149934_M;
    
    /**
     * Gets the block's texture. Args: side, meta
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return p_149691_1_ == 1 ? this.field_149935_N : (p_149691_1_ == 0 ? this.field_149935_N : (p_149691_1_ != p_149691_2_ ? this.blockIcon : this.field_149936_O));
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.blockIcon = p_149651_1_.registerIcon(RefStrings.MODID + ":v_side");
        this.field_149936_O = p_149651_1_.registerIcon(this.isOn ? RefStrings.MODID + ":v_front_on" : RefStrings.MODID + ":v_front_off");
        this.field_149935_N = p_149651_1_.registerIcon(RefStrings.MODID + ":v_top");
        System.out.println("******************************" + field_149935_N.toString());
    }
	
	protected Vacuum(boolean isOn) {
		super(Material.anvil);
		this.setHardness(4.0F);
		this.setResistance(4.0F);
		this.setStepSound(soundTypeStone);

        this.isOn = isOn;
	}

	public void breakBlock(World world, int x, int y, int z, Block oldblock, int oldMetadata) {
		//Need to solve how to resolve keep inventory ... In the mean time it is set to false
		final boolean keepInventory = false;
		if (!keepInventory) 
		{
			VTileEntity tileentity = (VTileEntity) world.getTileEntity(x, y, z);

			if (tileentity != null) 
			{
				for (int i = 0; i < tileentity.getSizeInventory(); i++) 
				{
					ItemStack itemstack = tileentity.getStackInSlot(i);

					if (itemstack != null) 
					{
						float f = 0.8F;
						float f1 = 0.8F;
						float f2 = 0.8F;
						//float F0 = this.rand.nextFloat() * 0.8F + 0.1F; (example of old)

						while (itemstack.stackSize > 0) 
						{
							int j = world.rand.nextInt(21) + 10;

							if (j > itemstack.stackSize) 
							{
								j = itemstack.stackSize;
							}

							itemstack.stackSize -= j;

							EntityItem item = new EntityItem(world, (double) ((float) x + f), (double) ((float) y + f1),
									(double) ((float) z + f2),
									new ItemStack(itemstack.getItem(), j, itemstack.getItemDamage()));

							if (itemstack.hasTagCompound()) 
							{
								item.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
							}

							world.spawnEntityInWorld(item);
						}
					}
				}

				world.func_147453_f(x, y, z, oldblock);
			}
		}

		super.breakBlock(world, x, y, z, oldblock, oldMetadata);
        world.removeTileEntity(x, y, z);
	}

	@Override
	public void onBlockPlacedBy(World world, int xCord, int yCord, int zCord, EntityLivingBase placer, ItemStack stack) {
		 int l = MathHelper.floor_double((double)(placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

	        if (l == 0)
	        {
	            world.setBlockMetadataWithNotify(xCord, yCord, zCord, 2, 2);
	        }

	        if (l == 1)
	        {
	        	world.setBlockMetadataWithNotify(xCord, yCord, zCord, 5, 2);
	        }

	        if (l == 2)
	        {
	        	world.setBlockMetadataWithNotify(xCord, yCord, zCord, 3, 2);
	        }

	        if (l == 3)
	        {
	        	world.setBlockMetadataWithNotify(xCord, yCord, zCord, 4, 2);
	        }

		if (stack.hasDisplayName()) {
	        ((VTileEntity) world.getTileEntity(xCord, yCord, zCord)).setCustomName(stack.getDisplayName());
	    }
	    super.onBlockPlacedBy(world, xCord, yCord, zCord, placer, stack);
	}
    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
    {
        super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
        this.func_149930_e(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
    }

    private void func_149930_e(World p_149930_1_, int p_149930_2_, int p_149930_3_, int p_149930_4_)
    {
        if (!p_149930_1_.isRemote)
        {
            Block block = p_149930_1_.getBlock(p_149930_2_, p_149930_3_, p_149930_4_ - 1);
            Block block1 = p_149930_1_.getBlock(p_149930_2_, p_149930_3_, p_149930_4_ + 1);
            Block block2 = p_149930_1_.getBlock(p_149930_2_ - 1, p_149930_3_, p_149930_4_);
            Block block3 = p_149930_1_.getBlock(p_149930_2_ + 1, p_149930_3_, p_149930_4_);
            byte b0 = 3;

            if (block.func_149730_j() && !block1.func_149730_j())
            {
                b0 = 3;
            }

            if (block1.func_149730_j() && !block.func_149730_j())
            {
                b0 = 2;
            }

            if (block2.func_149730_j() && !block3.func_149730_j())
            {
                b0 = 5;
            }

            if (block3.func_149730_j() && !block2.func_149730_j())
            {
                b0 = 4;
            }

            p_149930_1_.setBlockMetadataWithNotify(p_149930_2_, p_149930_3_, p_149930_4_, b0, 2);
        }
    }
	
    /**
     * Gets an item for the block being called on. Args: world, x, y, z
     */
    @SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
    {
        return Item.getItemFromBlock(this);
    }
    
	@Override
	public boolean onBlockActivated(World world, int xCord, int yCord, int zCord, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
	    if (!world.isRemote) {
	        
	        VTileEntity tileentity = (VTileEntity)world.getTileEntity(xCord, yCord, zCord);

            if (tileentity != null)
            {
                player.func_146100_a(tileentity);
            }
	    }
	    FMLNetworkHandler.openGui(player, "greengrow", GUIHandler.V_TILE_ENTITY, world, xCord, yCord, zCord); //V_TILE_ENTITY represents '1'
	    return true;
	}

    /**
     * Update which block the furnace is using depending on whether or not it is burning
     */
    public static void updateVBlockState(boolean isOn, World p_149931_1_, int p_149931_2_, int p_149931_3_, int p_149931_4_)
    {
        int l = p_149931_1_.getBlockMetadata(p_149931_2_, p_149931_3_, p_149931_4_);
        TileEntity tileentity = (TileEntity) p_149931_1_.getTileEntity(p_149931_2_, p_149931_3_, p_149931_4_); //Casted to PBTileEntity
        field_149934_M = true;
        /*
        if (isOn)
        {
            p_149931_1_.setBlock(p_149931_2_, p_149931_3_, p_149931_4_, MBlocks.VacuumOn);
        }
        else
        {
            p_149931_1_.setBlock(p_149931_2_, p_149931_3_, p_149931_4_, MBlocks.Vacuum);
        }
        */
        
        field_149934_M = false;
        /*
        p_149931_1_.setBlockMetadataWithNotify(p_149931_2_, p_149931_3_, p_149931_4_, l, 2);
        if (tileentity != null)
        {
            tileentity.validate();
            p_149931_1_.setTileEntity(p_149931_2_, p_149931_3_, p_149931_4_, tileentity);
        }
        */
    }
    
    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_)
    {
        if (this.isOn)
        {
            int l = p_149734_1_.getBlockMetadata(p_149734_2_, p_149734_3_, p_149734_4_);
            float f = (float)p_149734_2_ + 0.5F;
            float f1 = (float)p_149734_3_ + 0.0F + p_149734_5_.nextFloat() * 6.0F / 16.0F;
            float f2 = (float)p_149734_4_ + 0.5F;
            float f3 = 0.52F;
            float f4 = p_149734_5_.nextFloat() * 0.6F - 0.3F;

            if (l == 4)
            {
                p_149734_1_.spawnParticle("smoke", (double)(f - f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
                p_149734_1_.spawnParticle("flame", (double)(f - f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
            }
            else if (l == 5)
            {
                p_149734_1_.spawnParticle("smoke", (double)(f + f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
                p_149734_1_.spawnParticle("flame", (double)(f + f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
            }
            else if (l == 2)
            {
                p_149734_1_.spawnParticle("smoke", (double)(f + f4), (double)f1, (double)(f2 - f3), 0.0D, 0.0D, 0.0D);
                p_149734_1_.spawnParticle("flame", (double)(f + f4), (double)f1, (double)(f2 - f3), 0.0D, 0.0D, 0.0D);
            }
            else if (l == 3)
            {
                p_149734_1_.spawnParticle("smoke", (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0D, 0.0D, 0.0D);
                p_149734_1_.spawnParticle("flame", (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0D, 0.0D, 0.0D);
            }
        }
    }
    
    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World world, int meta) 
    {
    	return new VTileEntity();
    }
    
    public boolean onBlockEventReceived(World world, int x, int y, int z, int p_149696_5_, int p_149696_6_)
    {
        super.onBlockEventReceived(world, x, y, z, p_149696_5_, p_149696_6_);
        VTileEntity tileentity = (VTileEntity) world.getTileEntity(x, y, z); //Casted to VTileEntity
        return tileentity != null ? tileentity.receiveClientEvent(p_149696_5_, p_149696_6_) : false;
    }
    
    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 31; //101 is my custom name for Vacuum Rendering type
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
}
