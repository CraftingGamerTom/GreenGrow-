package com.craftinggamertom.block;


import java.util.ArrayList;
import java.util.Random;

import com.craftinggamertom.item.MItems;
import com.craftinggamertom.lib.RefStrings;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PurpleCannabisPlant extends CannabisPlants{

    /**
     * This returns a complete list of items dropped from this block.
     *
     * @param world The current world
     * @param x X Position
     * @param y Y Position
     * @param z Z Position
     * @param metadata Current metadata
     * @param fortune Breakers fortune level
     * @return A ArrayList containing all items this block drops
     */
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {        
    	ArrayList<ItemStack> ItemsToDrop = new ArrayList<ItemStack>();
        
        return ItemsToDrop;
    }

    protected PurpleCannabisPlant()
    {
    	this.setBlockName("PurpleCannabisPlant");
        this.setBlockTextureName(RefStrings.MODID + ":pc");
        this.setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 0.4F, 0.7F); //this.setBlockBounds(StartingPointX, StartingPointY, StartingPointZ, X, Y, Z);
        
    }
    @Override
    public void updateTick(World worldValue, int xCordValue, int yCordValue, int zCordValue, Random p_149674_5_)
    {
    	super.setStartPlant("PurpleCannabisPlant");
        super.setNextPlant("PurpleCannabisPlantTwo");
        super.setLastPlant("PurpleCannabisPlantThree");
        
        super.setStartPlantBlock(MBlocks.PurpleCannabisPlant);
        super.setNextPlantBlock(MBlocks.PurpleCannabisPlantTwo);
        super.setLastPlantBlock(MBlocks.PurpleCannabisPlantThree);
        super.updateTick(worldValue, xCordValue, yCordValue, zCordValue, p_149674_5_);
    }
    
    @Override
    public void func_149863_m(World worldValue, int xCordValue, int yCordValue, int zCordValue)
    {
    	super.setStartPlant("PurpleCannabisPlant");
        super.setNextPlant("PurpleCannabisPlantTwo");
        super.setLastPlant("PurpleCannabisPlantThree");
        
        super.setStartPlantBlock(MBlocks.PurpleCannabisPlant);
        super.setNextPlantBlock(MBlocks.PurpleCannabisPlantTwo);
        super.setLastPlantBlock(MBlocks.PurpleCannabisPlantThree);
        super.func_149863_m(worldValue, xCordValue, yCordValue, zCordValue);
    }
    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return (p_149650_1_ == 2 || p_149650_1_ == 1) ? this.func_149865_P() : this.func_149866_i();
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(Random p_149745_1_)
    {
        return 1;
    }
    public int quantityDropped(int meta, int fortune, Random random)
    {
        /**
         * Returns the usual quantity dropped by the block plus a bonus of 1 to 'i' (inclusive).
         */
        return quantityDroppedWithBonus(fortune, random);
    }



}