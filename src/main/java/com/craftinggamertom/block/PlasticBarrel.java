package com.craftinggamertom.block;

import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.server.gui.MinecraftServerGui;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.src.FMLRenderAccessLibrary;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.main.Main;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockClay;

import com.craftinggamertom.creativeTabs.MCreativeTabs;
import com.craftinggamertom.lib.RefStrings;
import com.craftinggamertom.main.GUIHandler;
import com.craftinggamertom.tileEntities.PBTileEntity;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;

import net.minecraft.tileentity.TileEntity;

//------------------
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockCauldron;

public class PlasticBarrel extends Block implements ITileEntityProvider {
    
protected PlasticBarrel(Material p_i45394_1_) {
		super(p_i45394_1_);
		this.setHardness(2.0F);
		this.setResistance(4.0F);
		this.setStepSound(soundTypeWood);
		
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
    return 31;
}

/**
 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
 */
public boolean renderAsNormalBlock()
{
    return false;
}

//Acts as a crafting table but only allows coca leaves and creates coca paste after some time
	//Find the proper classes to extend to allow this
	
/*
 //Could possibly be used in later minecraft version
	//@Override
	public void breakBlock(World world, int xCord, int yCord, int zCord, Block p_149749_5_, int p_149749_6_) {
		PBTileEntity te = (PBTileEntity) world.getTileEntity(xCord, yCord, zCord);
	    InventoryHelper.dropInventoryItems(world, pos, te);
	    super.breakBlock(world, xCord, yCord, zCord, p_149749_5_, p_149749_6_);
	}
*/
	public void breakBlock(World world, int x, int y, int z, Block oldblock, int oldMetadata) {
		//Need to solve how to resolve keep inventory ... In the mean time it is set to false
		final boolean keepInventory = false;
		if (!keepInventory) 
		{
			PBTileEntity tileentity = (PBTileEntity) world.getTileEntity(x, y, z);

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
		//world.removeTileEntity(x, y, z); // MAKE SURE TO ADD TO OTHER CLASSES
		super.breakBlock(world, x, y, z, oldblock, oldMetadata);
	}

	
	@Override
	public boolean onBlockActivated(World world, int xCord, int yCord, int zCord, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
	    if (!world.isRemote) {
	        FMLNetworkHandler.openGui(player, "greengrow", GUIHandler.PB_TILE_ENTITY, world, xCord, yCord, zCord); //'0' represents the PlasticBarrelGUI
	    }
	    return true;
	}

    public TileEntity createNewTileEntity(World world, int meta) {
    	PBTileEntity pbt = new PBTileEntity();
    	pbt.setWorldObj(world);
    	pbt.blockMetadata = meta;
    	return pbt;
    }


    public boolean onBlockEventReceived(World world, int x, int y, int z, int p_149696_5_, int p_149696_6_)
    {
        super.onBlockEventReceived(world, x, y, z, p_149696_5_, p_149696_6_);
        PBTileEntity tileentity = (PBTileEntity) world.getTileEntity(x, y, z); //Casted to PBTileEntity
        return tileentity != null ? tileentity.receiveClientEvent(p_149696_5_, p_149696_6_) : false;
    }
}
