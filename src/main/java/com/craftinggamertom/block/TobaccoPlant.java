package com.craftinggamertom.block;

import java.util.ArrayList;
import java.util.Random;

import com.craftinggamertom.creativeTabs.MCreativeTabs;
import com.craftinggamertom.item.MItems;
import com.craftinggamertom.lib.RefStrings;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockReed;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;


/*
 * TODO:
 * 
 * 
 * 	this.setTickRandomly(true);
	float aFloat = 0.5F;
	this.setBlockBounds(0.5F - aFloat, 0.0F, 0.5F - aFloat, 0.5F + aFloat, 0.25F, 0.5F + aFloat);
	this.setCreativeTab((CreativeTabs)null);
	this.setHardness(0.0F);
	this.setStepSound(soundGrassFootstep);
	this.disableStats();
 */

public class TobaccoPlant extends BlockCrops{
	

	final int LIGHT_NEEDED = 0;
	final int NUMBER_OF_STAGES = 7;
	final int MAX_HEIGHT = 2;
	
    protected TobaccoPlant()
    {
    	this.setBlockName("TobaccoPlant");
        // this.setCreativeTab(MCreativeTabs.greengrow); // not needed for release
        this.setBlockTextureName(RefStrings.MODID + ":tobacco");
        this.setTickRandomly(true);
        this.setBlockBounds(0.2F, 0.0F, 0.2F, 0.8F, 1.0F, 0.8F); //this.setBlockBounds(StartingPointX, StartingPointY, StartingPointZ, X, Y, Z);
        this.setHardness(0.2F);
        this.setStepSound(soundTypeGrass);
        //this.disableStats(); //Disable statistics for the block, the block will not count for mined or placed.

    }

	
    protected boolean canPlaceBlockOn(Block p_149854_1_)
    {
        return p_149854_1_ == Blocks.farmland;
    }
    
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> ItemsToDrop = new ArrayList<ItemStack>();

        if(metadata >= 7)
        {
        	ItemsToDrop.add(new ItemStack(MItems.Tobacco, 2));
        }
        if(metadata >= 6)
        {
        	ItemsToDrop.add(new ItemStack(MItems.Tobacco, 1));
        }
        if(metadata >= 5)
        {
        	
        }
        if(metadata >= 4)
        {
        	
        }
        if (metadata <= 3)
        {
        	return null;
        }
        return ItemsToDrop;
    }

	public void updateTick(World worldValue, int xCordValue, int yCordValue, int zCordValue, Random p_149674_5_)
    {	    	
		int l;
        int currentStage = worldValue.getBlockMetadata(xCordValue, yCordValue, zCordValue);
        int newStage = currentStage + 1;
        
    	this.checkAndDropBlock(worldValue, xCordValue, yCordValue, zCordValue);
    	
    	if (currentStage > NUMBER_OF_STAGES){
    		return;
    	}
    
    	/*
    	 * If the new block to be made can stay, the block below is at stage 7, and the light is above LIGHT_NEEDED it will do work.
    	 */
    	if (this.checkForIllegalPlacement(worldValue, xCordValue, yCordValue, zCordValue))
    	{
	    	if (worldValue.getBlockLightValue(xCordValue, yCordValue + 1, zCordValue) >= LIGHT_NEEDED)
	    	{
	    	   	
	            for (l = 1; worldValue.getBlock(xCordValue, yCordValue - l, zCordValue) == this; ++l); //Counts how many blocks below are this kind of plant
	            if (currentStage == NUMBER_OF_STAGES - 1 && l < MAX_HEIGHT)
	            {
	            	if(worldValue.isAirBlock(xCordValue, yCordValue + 1, zCordValue))
	            	{
	            		newStage = 6;
	                	worldValue.setBlock(xCordValue, yCordValue + 1, zCordValue, this);
	                    worldValue.setBlockMetadataWithNotify(xCordValue, yCordValue+1, zCordValue, 0, 4);	
	            	}
	            	
	            }
	            else if (currentStage == NUMBER_OF_STAGES - 1 && l >= MAX_HEIGHT)
	            {
	            		newStage = 7;        	
	            		worldValue.setBlockMetadataWithNotify(xCordValue, yCordValue, zCordValue, 7, 2);
	            }
	            else if (newStage < 7)
	            {
	            	float f = this.determineGrowability(worldValue, xCordValue, yCordValue, zCordValue);
					if (p_149674_5_.nextInt((int) (25.0F / f) + 1) == 0) 
					{
	
						worldValue.setBlockMetadataWithNotify(xCordValue, yCordValue, zCordValue, newStage, 2);
	
					}	
	            }
	    	}
    	}	
    }
    //Removes block if it has illegal placement.
    protected final boolean checkForIllegalPlacement(World worldValue, int xCordValue, int yCordValue, int zCordValue)
    {
    	
        if (!this.canBlockStay(worldValue, xCordValue, yCordValue, zCordValue))
        {
            this.dropBlockAsItem(worldValue, xCordValue, yCordValue, zCordValue, worldValue.getBlockMetadata(xCordValue, yCordValue, zCordValue), 0);
            worldValue.setBlockToAir(xCordValue, yCordValue, zCordValue);
            return false;
        }
        else
        {
            return true;
        }
        
    }
    //Checks if block below is the same plant type - If it is, the block can stay. Otherwise, it uses classic standards of deciding if the block can stay.
    public boolean canBlockStay(World worldValue, int xCordValue, int yCordValue, int zCordValue)
    {
    	Block blockOneBelow = worldValue.getBlock(xCordValue, yCordValue - 1, zCordValue);
    	//Block blockTwoBelow = worldValue.getBlock(xCordValue, yCordValue - 2, zCordValue);
    	
    	if(blockOneBelow == this){
    		return true;
    	}
        return super.canBlockStay(worldValue, xCordValue, yCordValue, zCordValue);
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

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 1;
    }
    /**
     * The bonemeal function
     */
    @Override
    public void func_149863_m(World worldValue, int xCordValue, int yCordValue, int zCordValue)
    {
    	int l;
        int currentStage = worldValue.getBlockMetadata(xCordValue, yCordValue, zCordValue);
        int newStage = currentStage + 1;
        
        for (l = 1; worldValue.getBlock(xCordValue, yCordValue - l, zCordValue) == this; ++l); //Counts how many blocks below are this kind of plant
        if (currentStage == NUMBER_OF_STAGES - 1 && l < MAX_HEIGHT)
        {
        	if(worldValue.isAirBlock(xCordValue, yCordValue + 1, zCordValue))
        	{
        		newStage = 6;
            	worldValue.setBlock(xCordValue, yCordValue + 1, zCordValue, this);
                worldValue.setBlockMetadataWithNotify(xCordValue, yCordValue+1, zCordValue, 0, 4);	
        	}
        	
        }
        else if (currentStage == NUMBER_OF_STAGES - 1 && l >= MAX_HEIGHT)
        {
        		newStage = 7;        	
        		worldValue.setBlockMetadataWithNotify(xCordValue, yCordValue, zCordValue, 7, 2);
        }
        else if (newStage < 7)
        {
        	worldValue.setBlockMetadataWithNotify(xCordValue, yCordValue, zCordValue, newStage, 2);	
        }
        
    }
    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor Block
     */
    public void onNeighborBlockChange(World worldValue, int xCordValue, int yCordValue, int zCordValue, Block aBlockValue)
    {
        this.checkAndRemoveChangedBlock(worldValue, xCordValue, yCordValue, zCordValue, aBlockValue);
    }
    /*
     * When called by onNeighborBlockChange: Checks block above - if it is the same and can no longer stay
     * it then checks the block above to see if it is the same and can no longer stay.
     * After finding all of the same plants above that can no longer stay it removes them (Recursive)
     */
    protected final boolean checkAndRemoveChangedBlock(World worldValue, int xCordValue, int yCordValue, int zCordValue, Block aBlockValue)
    {
        if (!this.canBlockStay(worldValue, xCordValue, yCordValue, zCordValue))
        {
            this.dropBlockAsItem(worldValue, xCordValue, yCordValue, zCordValue, worldValue.getBlockMetadata(xCordValue, yCordValue, zCordValue), 0);
            worldValue.setBlockToAir(xCordValue, yCordValue, zCordValue);
            if (worldValue.getBlock(xCordValue, yCordValue + 1, zCordValue) == this){
            	this.onNeighborBlockChange(worldValue, xCordValue, yCordValue+1, zCordValue, aBlockValue);	
            }
            
            return false;
        }
        else
        {
            return true;
        }
    }
}
