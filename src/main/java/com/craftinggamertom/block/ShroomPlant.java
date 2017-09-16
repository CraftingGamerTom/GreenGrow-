package com.craftinggamertom.block;

import java.util.ArrayList;
import java.util.Random;

import com.craftinggamertom.creativeTabs.MCreativeTabs;
import com.craftinggamertom.item.MItems;
import com.craftinggamertom.lib.RefStrings;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.block.BlockMushroom;;


public class ShroomPlant extends BlockCrops{
	final int LIGHT_NEEDED = 6;
	final int NUMBER_OF_STAGES = 7;
	final int MAX_HEIGHT = 2;
	
    protected ShroomPlant()
    {
    	this.setBlockName("ShroomPlant");
        // this.setCreativeTab(MCreativeTabs.greengrow); // Not Needed for release
        this.setBlockTextureName(RefStrings.MODID + ":shroom");
        this.setTickRandomly(true);
        this.setBlockBounds(0.2F, 0.0F, 0.2F, 0.8F, 0.5F, 0.8F); //this.setBlockBounds(StartingPointX, StartingPointY, StartingPointZ, X, Y, Z);
        this.setHardness(0.4F);
        this.setStepSound(soundTypeGrass);
        //this.disableStats(); //Disable statistics for the block, the block will not count for mined or placed.

    }

    protected boolean canPlaceBlockOn(Block p_149854_1_)
    {
        return p_149854_1_ == Blocks.farmland;
    }
    
    public ArrayList<ItemStack> getDrops(World worldValue, int xCordValue, int yCordValue, int zCordValue, int metadata, int fortune)
    {
        ArrayList<ItemStack> ItemsToDrop = new ArrayList<ItemStack>();
        int x = worldValue.getBlockMetadata(xCordValue, yCordValue, zCordValue);
        if(x == 7)
        {
        	ItemsToDrop.add(new ItemStack(MItems.Spors, 1));
        	ItemsToDrop.add(new ItemStack(MItems.Shrooms, 1));
        }
        ItemsToDrop.add(new ItemStack(MItems.Spors, 1));
        return ItemsToDrop;
    }
	 public void updateTick(World worldValue, int xCordValue, int yCordValue, int zCordValue, Random p_149674_5_)
	    {	    	
	        int currentStage = worldValue.getBlockMetadata(xCordValue, yCordValue, zCordValue);
	        int newStage = currentStage + 1;
	        if (this.checkForIllegalPlacement(worldValue, xCordValue, yCordValue, zCordValue))
	    	{
		        if (newStage >= NUMBER_OF_STAGES)
		        {
		        	newStage = 7;
		        }
		        if (currentStage < NUMBER_OF_STAGES)
		        {
		        	float f = this.determineGrowability(worldValue, xCordValue, yCordValue, zCordValue);
		        	if (p_149674_5_.nextInt((int) (25.0F / f) + 1) == 0) 
					{
	
						worldValue.setBlockMetadataWithNotify(xCordValue, yCordValue, zCordValue, newStage, 2);
	
					}	

		        }
	    	}
	    }
	 public void func_149863_m(World worldValue, int xCordValue, int yCordValue, int zCordValue)
	    {
		 
	        int currentStage = worldValue.getBlockMetadata(xCordValue, yCordValue, zCordValue);
	        int newStage = currentStage + 1;
	        if (this.checkForIllegalPlacement(worldValue, xCordValue, yCordValue, zCordValue))
	    	{
		        if (newStage >= NUMBER_OF_STAGES)
		        {
		        	newStage = 7;
		        }
		        if (currentStage < NUMBER_OF_STAGES)
		        {
		        	worldValue.setBlockMetadataWithNotify(xCordValue, yCordValue, zCordValue, newStage, 2);
		        }
	    	}
	    }
	    /**
	     * Called upon block activation (right click on the block.)
	     */
	    public boolean onBlockActivated(World worldValue, int xCordValue, int yCordValue, int zCordValue, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
	    {
	        int currentStage = worldValue.getBlockMetadata(xCordValue, yCordValue, zCordValue);
	    	if (currentStage == 7)
	    	{
	    		ItemStack itemsToDrop = (new ItemStack(MItems.Shrooms, 1));
	        	myDropBlockAsItem(worldValue, xCordValue, yCordValue, zCordValue, itemsToDrop);
	        	worldValue.setBlockMetadataWithNotify(xCordValue, yCordValue, zCordValue, 0, 2);
	    	}
	    	return false;
	    }
	    protected void myDropBlockAsItem(World worldValue, int xCordValue, int yCordValue, int zCordValue, ItemStack itemsToDrop)
	    {
	        if (!worldValue.isRemote && worldValue.getGameRules().getGameRuleBooleanValue("doTileDrops") && !worldValue.restoringBlockSnapshots) // do not drop items while restoring blockstates, prevents item dupe
	        {
	            if (captureDrops.get())
	            {
	                capturedDrops.get().add(itemsToDrop);
	                return;
	            }
	            float f = 0.7F;
	            double d0 = (double)(worldValue.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
	            double d1 = (double)(worldValue.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
	            double d2 = (double)(worldValue.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
	            EntityItem entityitem = new EntityItem(worldValue, (double)xCordValue + d0, (double)yCordValue + d1, (double)zCordValue + d2, itemsToDrop);
	            entityitem.delayBeforeCanPickup = 10;
	            worldValue.spawnEntityInWorld(entityitem);
	        }
	    }
	    //Removes block is it has illegal placement.
	    protected final boolean checkForIllegalPlacement(World p_150170_1_, int p_150170_2_, int p_150170_3_, int p_150170_4_)
	    {
	    	
	        if (!this.canBlockStay(p_150170_1_, p_150170_2_, p_150170_3_, p_150170_4_))
	        {
	            this.dropBlockAsItem(p_150170_1_, p_150170_2_, p_150170_3_, p_150170_4_, p_150170_1_.getBlockMetadata(p_150170_2_, p_150170_3_, p_150170_4_), 0);
	            p_150170_1_.setBlockToAir(p_150170_2_, p_150170_3_, p_150170_4_);
	            return false;
	        }
	        else
	        {
	            return true;
	        }
	        
	    }
	    //Checks if block below is the same plant type - If it is, the block can stay. Otherwise, it uses classic standards of deciding if the block can stay.
	    public boolean canBlockStay(World p_149718_1_, int p_149718_2_, int p_149718_3_, int p_149718_4_)
	    {
	    	
	    	if(p_149718_1_.getBlock(p_149718_2_, p_149718_3_ - 1, p_149718_4_) == this){
	    		return true;
	    	}
	        return super.canBlockStay(p_149718_1_, p_149718_2_, p_149718_3_, p_149718_4_);
	    }
	    /**
	     * The type of render function that is called for this block
	     */
	    public int getRenderType()
	    {
	    	/*
	    	1: BlockSign
	    	1: BlockFlower, BlockReed
	    	2: BlockTorch
	    	3: BlockFire
	    	4: BlockFluids
	    	5: BlockRedstoneWire
	    	6: BlockCrops
	    	7: BlockDoor
	    	8: BlockLadder
	    	9: BlockMinecartTrack
	    	10: BlockStairs
	    	11: BlockFence
	    	12: BlockLever
	    	13: BlockCactus
	    	14: BlockBed
	    	15: BlockRedstoneRepeater
	        */
	        return 1;
	    }
	    //Allow Silk Touch
	    protected boolean canSilkHarvest()
	    {
	        return true;
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
	     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	     */
	    public boolean renderAsNormalBlock()
	    {
	        return false;
	    }
	    //Decides the likelihood that the plant will grow.
	    private float determineGrowability(World growabilityWorldValue, int growabilityX_Cord, int growabilityY_Cord, int growabilityZ_Cord)
	    {
	        float f = 1.0F;
	        Block block_S = growabilityWorldValue.getBlock(growabilityX_Cord, growabilityY_Cord, growabilityZ_Cord - 1);
	        Block block_N = growabilityWorldValue.getBlock(growabilityX_Cord, growabilityY_Cord, growabilityZ_Cord + 1);
	        Block block_W = growabilityWorldValue.getBlock(growabilityX_Cord - 1, growabilityY_Cord, growabilityZ_Cord);
	        Block block_E = growabilityWorldValue.getBlock(growabilityX_Cord + 1, growabilityY_Cord, growabilityZ_Cord);
	        Block block_SW = growabilityWorldValue.getBlock(growabilityX_Cord - 1, growabilityY_Cord, growabilityZ_Cord - 1);
	        Block block_SE = growabilityWorldValue.getBlock(growabilityX_Cord + 1, growabilityY_Cord, growabilityZ_Cord - 1);
	        Block block_NE = growabilityWorldValue.getBlock(growabilityX_Cord + 1, growabilityY_Cord, growabilityZ_Cord + 1);
	        Block block_NW = growabilityWorldValue.getBlock(growabilityX_Cord - 1, growabilityY_Cord, growabilityZ_Cord + 1);
	        boolean flag = block_W == this || block_E == this;
	        boolean flag1 = block_S == this || block_N == this;
	        boolean flag2 = block_SW == this || block_SE == this || block_NE == this || block_NW == this;

	        for (int l = growabilityX_Cord - 1; l <= growabilityX_Cord + 1; ++l)
	        {
	            for (int i1 = growabilityZ_Cord - 1; i1 <= growabilityZ_Cord + 1; ++i1)
	            {
	                float f1 = 0.0F;

	                if (growabilityWorldValue.getBlock(l, growabilityY_Cord - 1, i1).canSustainPlant(growabilityWorldValue, l, growabilityY_Cord - 1, i1, ForgeDirection.UP, this))
	                {
	                    f1 = 1.0F;

	                    if (growabilityWorldValue.getBlock(l, growabilityY_Cord - 1, i1).isFertile(growabilityWorldValue, l, growabilityY_Cord - 1, i1))
	                    {
	                        f1 = 3.0F;
	                    }
	                }

	                if (l != growabilityX_Cord || i1 != growabilityZ_Cord)
	                {
	                    f1 /= 4.0F;
	                }

	                f += f1;
	            }
	        }

	        //Essentially - If a block on (any corner || either side) if the same plant - divide f by 2.0F
	        if (flag2 || flag && flag1)
	        {
	            f /= 2.0F;
	        }

	        return f;
	    }
}
