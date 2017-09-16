package com.craftinggamertom.block;

import java.util.Random;

import com.craftinggamertom.creativeTabs.MCreativeTabs;
import com.craftinggamertom.item.MItems;
import com.craftinggamertom.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.common.util.ForgeDirection;

public class CannabisPlants extends BlockCrops{
	/*
	 * THINGS THAT MUST BE PUT INTO CHILD CLASSES:
	 * -Items To Drop
	 * -Settings
	 * -Override UpdateTick
	 * -Override Bonemeal
	 */
	
	final int LIGHT_NEEDED = 9;
	final int NUMBER_OF_STAGES = 1;
	final int MAX_HEIGHT = 3;
	
	private String nextPlant = "";
	private String startPlant = "";
	private String lastPlant = "";
	
	private Block nextPlantBlock;
	private Block startPlantBlock;
	private Block lastPlantBlock;

	protected CannabisPlants(){
        this.setHardness(0.5F);
        this.setStepSound(soundTypeGrass);
        //this.disableStats(); //Disable statistics for the block, the block will not count for mined or placed.
        this.setTickRandomly(true);
        // this.setCreativeTab(MCreativeTabs.greengrow); // Don't need this in creative
	}
    
    //Plant Name Strings
    public void setNextPlant(String nextPlant){
    	this.nextPlant = nextPlant;
    }
    public String getNextPlant(){
    	return nextPlant;
    }
    public void setStartPlant(String startPlant){
    	this.startPlant = startPlant;
    }
    public String getStartPlant(){
    	return startPlant;
    }
    public void setLastPlant(String lastPlant){
    	this.lastPlant = lastPlant;
    }
    public String getLastPlant(){
    	return lastPlant;
    }
    //Plant Name Blocks
    public void setNextPlantBlock(Block nextPlantBlock){
    	this.nextPlantBlock = nextPlantBlock;
    }
    public Block getNextPlantBlock(){
    	return nextPlantBlock;
    }
    public void setStartPlantBlock(Block startPlantBlock){
    	this.startPlantBlock = nextPlantBlock;
    }
    public Block getStartPlantBlock(){
    	return startPlantBlock;
    }
    public void setLastPlantBlock(Block lastPlantBlock){
    	this.lastPlantBlock = lastPlantBlock;
    }
    public Block getLastPlantBlock(){
    	return lastPlantBlock;
    }
    
    /*
     * Method to grow the plant
     */
	@Override
	public void updateTick(World worldValue, int xCordValue, int yCordValue, int zCordValue, Random p_149674_5_)
    {
    	int NUMBER_OF_STAGES_test = 7;
    	int l;
    	int x = worldValue.getBlockMetadata(xCordValue, yCordValue, zCordValue);
    	
    	/*
    	System.out.println("x = " + x);
    	System.out.println("this == startplant? " + this.isEqualTo(worldValue.getBlock(xCordValue, yCordValue, zCordValue), startPlantBlock));
    	System.out.println("this == nextplant? " + this.isEqualTo(worldValue.getBlock(xCordValue, yCordValue, zCordValue), nextPlantBlock));
    	System.out.println("this == lastplant? " + this.isEqualTo(worldValue.getBlock(xCordValue, yCordValue, zCordValue), lastPlantBlock));
    	*/
    	
    	for (l = 1; worldValue.getBlock(xCordValue, yCordValue - l, zCordValue) == this; ++l); //Counts how many blocks below are this kind of plant
    	this.checkAndDropBlock(worldValue, xCordValue, yCordValue, zCordValue);
    	
    	if (x >= 7)
    	{
    		return;
    	}
    	/*
    	 * If the new block to be made can stay, the block below is at stage 7, and the light is above LIGHT_NEEDED it will do work.
    	 */
    	if (worldValue.getBlockLightValue(xCordValue, yCordValue + 1, zCordValue) >= LIGHT_NEEDED)
    	{
	        if (this.checkForIllegalPlacement(worldValue, xCordValue, yCordValue, zCordValue))
	        {
	        	//If start plant is at final stage set it to the next plant
	        	if(x == 1)
                {
                  	//Sets new block meta data
                	worldValue.setBlock(xCordValue, yCordValue, zCordValue, nextPlantBlock);
                	++x;
                	worldValue.setBlockMetadataWithNotify(xCordValue, yCordValue, zCordValue, x, 2);
                }
                //If next plant is at final stage set it to the final plant
                else if(x == 3)
                {
                  	//Sets new block meta data
                	worldValue.setBlock(xCordValue, yCordValue, zCordValue, lastPlantBlock);
                	++x;
                	worldValue.setBlockMetadataWithNotify(xCordValue, yCordValue, zCordValue, x, 2);
                }
                else if (x == 5){
                	if(l >= MAX_HEIGHT)
                	{
                    	//Sets parent block meta data higher than NUMBER_OF_STAGES_test so that it no longer loops.
                    	worldValue.setBlockMetadataWithNotify(xCordValue, yCordValue, zCordValue, x+1, 2);	
                	}else{
                		if (worldValue.isAirBlock(xCordValue, yCordValue + 1, zCordValue))
                    	{
    	                	//Sets parent block meta data higher than NUMBER_OF_STAGES_test so that it no longer loops.
    	                	worldValue.setBlockMetadataWithNotify(xCordValue, yCordValue, zCordValue, x+1, 2);
    	          
    	                	//Sets new block meta data
    	                	worldValue.setBlock(xCordValue, yCordValue + 1, zCordValue, startPlantBlock);
    	                	worldValue.setBlockMetadataWithNotify(xCordValue, yCordValue+1, zCordValue, 0, 2);

                    	}	
                	}
                }
                else if (x == 6){
                	if(l >= MAX_HEIGHT)
                	{
                    	//Sets top plant to absolute final stage
                    	worldValue.setBlockMetadataWithNotify(xCordValue, yCordValue, zCordValue, x+1, 2);	
                	}
                	else if(worldValue.isAirBlock(xCordValue, yCordValue + 1, zCordValue))
                	{
	                	//Makes new plant on top
	                	worldValue.setBlock(xCordValue, yCordValue + 1, zCordValue, startPlantBlock);
	                	worldValue.setBlockMetadataWithNotify(xCordValue, yCordValue+1, zCordValue, 0, 2);
                	}else{
                		return;
                	}
                }
                /*
    	         * If crop has not reached NUMBER_OF_STAGES_test
    	         */
                else if ((x % 2) == 0)
    	        {	
                    float f = this.determineGrowability(worldValue, xCordValue, yCordValue, zCordValue);
	                if (p_149674_5_.nextInt((int)(25.0F / f) + 1) == 0)
	                {
	                	
	                    ++x;
	                    worldValue.setBlockMetadataWithNotify(xCordValue, yCordValue, zCordValue, x, 2);

	                }
    	        }
                else
                {
                	System.out.println("GREENGROW:ERROR:COULD_NOT_FIND_GROW_PATH_ERROR UT-001");
                }	
	               
	    	        
	        }
    	}
    }
	
	/*     	                float f = this.determineGrowability(worldValue, xCordValue, yCordValue, zCordValue);
    	                if (p_149674_5_.nextInt((int)(25.0F / f) + 1) == 0)
    	                {
    	                	
    	                    ++x;
    	                    worldValue.setBlockMetadataWithNotify(xCordValue, yCordValue, zCordValue, x, 2);

    	                }
    */
	
    /**
     * The bonemeal function
     */
    @Override
    public void func_149863_m(World worldValue, int xCordValue, int yCordValue, int zCordValue)
    {
    	int NUMBER_OF_STAGES_test = 7;
    	int l;
    	int x = worldValue.getBlockMetadata(xCordValue, yCordValue, zCordValue);
    	
    	/*
    	System.out.println("x = " + x);
    	System.out.println("this == startplant? " + this.isEqualTo(worldValue.getBlock(xCordValue, yCordValue, zCordValue), startPlantBlock));
    	System.out.println("this == nextplant? " + this.isEqualTo(worldValue.getBlock(xCordValue, yCordValue, zCordValue), nextPlantBlock));
    	System.out.println("this == lastplant? " + this.isEqualTo(worldValue.getBlock(xCordValue, yCordValue, zCordValue), lastPlantBlock));
    	*/
    	
    	for (l = 1; worldValue.getBlock(xCordValue, yCordValue - l, zCordValue) == this; ++l); //Counts how many blocks below are this kind of plant
    	this.checkAndDropBlock(worldValue, xCordValue, yCordValue, zCordValue);
    	
    	if (x > 7)
    	{
    		return;
    	}
    	/*
    	 * If the new block to be made can stay, the block below is at stage 7, and the light is above LIGHT_NEEDED it will do work.
    	 */
    	if (worldValue.getBlockLightValue(xCordValue, yCordValue + 1, zCordValue) >= LIGHT_NEEDED)
    	{
	        if (this.checkForIllegalPlacement(worldValue, xCordValue, yCordValue, zCordValue))
	        {
	        	//If start plant is at final stage set it to the next plant
	        	if(x == 1)
                {
                  	//Sets new block meta data
                	worldValue.setBlock(xCordValue, yCordValue, zCordValue, nextPlantBlock);
                	++x;
                	worldValue.setBlockMetadataWithNotify(xCordValue, yCordValue, zCordValue, x, 2);
                }
                //If next plant is at final stage set it to the final plant
                else if(x == 3)
                {
                  	//Sets new block meta data
                	worldValue.setBlock(xCordValue, yCordValue, zCordValue, lastPlantBlock);
                	++x;
                	worldValue.setBlockMetadataWithNotify(xCordValue, yCordValue, zCordValue, x, 2);
                }
                else if (x == 5){
                	if(l >= MAX_HEIGHT)
                	{
                    	//Sets parent block meta data higher than NUMBER_OF_STAGES_test so that it no longer loops.
                    	worldValue.setBlockMetadataWithNotify(xCordValue, yCordValue, zCordValue, x+1, 2);	
                	}else{
                		if (worldValue.isAirBlock(xCordValue, yCordValue + 1, zCordValue))
                    	{
    	                	//Sets parent block meta data higher than NUMBER_OF_STAGES_test so that it no longer loops.
    	                	worldValue.setBlockMetadataWithNotify(xCordValue, yCordValue, zCordValue, x+1, 2);
    	          
    	                	//Sets new block meta data
    	                	worldValue.setBlock(xCordValue, yCordValue + 1, zCordValue, startPlantBlock);
    	                	worldValue.setBlockMetadataWithNotify(xCordValue, yCordValue+1, zCordValue, 0, 2);

                    	}	
                	}
                }
                else if (x == 6){
                	if(l >= MAX_HEIGHT)
                	{
                    	//Sets top plant to absolute final stage
                    	worldValue.setBlockMetadataWithNotify(xCordValue, yCordValue, zCordValue, x+1, 2);	
                	}
                	else if(worldValue.isAirBlock(xCordValue, yCordValue + 1, zCordValue))
                	{
	                	//Makes new plant on top
	                	worldValue.setBlock(xCordValue, yCordValue + 1, zCordValue, startPlantBlock);
	                	worldValue.setBlockMetadataWithNotify(xCordValue, yCordValue+1, zCordValue, 0, 2);
                	}else{
                		return;
                	}
                }
                /*
    	         * If crop has not reached NUMBER_OF_STAGES_test
    	         */
                else if ((x % 2) == 0)
    	        {	
    	                    ++x;
    	                    worldValue.setBlockMetadataWithNotify(xCordValue, yCordValue, zCordValue, x, 2);
    	        }
                else
                {
                	System.out.println("GREENGROW:ERROR:COULD_NOT_FIND_GROW_PATH_ERROR UT-001");
                }	
	               
	    	        
	        }
    	}
    }
    protected boolean canPlaceBlockOn(Block blockInQuestion)
    {
        return blockInQuestion == Blocks.farmland;
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

    //Checks if block below is the same plant type - If it is, the block can stay. Otherwise, it uses classic standards of deciding if the block can stay.
    public boolean canBlockStay(World worldValue, int xCordValue, int yCordValue, int zCordValue)
    {
    	Block blockOneBelow = worldValue.getBlock(xCordValue, yCordValue - 1, zCordValue);
    	//Block blockTwoBelow = worldValue.getBlock(xCordValue, yCordValue - 2, zCordValue);
    	
    	if(blockOneBelow == MBlocks.GreenCannabisPlantThree){
    		return true;
    	}
    	if(blockOneBelow == MBlocks.OrangeCannabisPlantThree){
    		return true;
    	}
    	if(blockOneBelow == MBlocks.PurpleCannabisPlantThree){
    		return true;
    	}
        return super.canBlockStay(worldValue, xCordValue, yCordValue, zCordValue);
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
            if (worldValue.getBlock(xCordValue, yCordValue + 1, zCordValue) == this)
            {
            	this.onNeighborBlockChange(worldValue, xCordValue, yCordValue+1, zCordValue, aBlockValue);	
            }

            
            return false;
        }
        else
        {
            return true;
        }
    }
    
    protected String getBlockTypeForComparison(World worldValue, int xCordValue, int yCordValue, int zCordValue)
    {
    	String newString = "";
    	newString = (worldValue.getBlock(xCordValue, yCordValue, zCordValue).toString());
    	int theSymbol = newString.indexOf("@");
    	newString = newString.substring(27, theSymbol);
    	//System.out.println("GET_BLOCK_METHOD: " + newString);
    	return newString;

    }
}
