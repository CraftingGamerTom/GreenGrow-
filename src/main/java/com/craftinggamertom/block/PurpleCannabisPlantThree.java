package com.craftinggamertom.block;

import java.util.ArrayList;
import java.util.Random;

import com.craftinggamertom.item.MItems;
import com.craftinggamertom.lib.RefStrings;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PurpleCannabisPlantThree extends CannabisPlants {
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> ItemsToDrop = new ArrayList<ItemStack>();

        //Full Grown - 3 bud, 3 leaves, 1 stem, 2 seed
        //Stage 6 - 1 bud, 3 leaves, 1 stem, 2 seeds
        if(metadata >= 7)
        {
        	ItemsToDrop.add(new ItemStack(MItems.PurpleCannabis, 2));
        }
        if(metadata >= 6)
        {
        	ItemsToDrop.add(new ItemStack(MItems.PurpleCannabis, 1));
        }
        if(metadata >= 5)
        {
        	ItemsToDrop.add(new ItemStack(MItems.CannabisLeaves, 1));	
        }
        if(metadata >= 4)
        {
        	ItemsToDrop.add(new ItemStack(MItems.CannabisLeaves, 1));
            ItemsToDrop.add(new ItemStack(MItems.CannabisStems, 1));
            ItemsToDrop.add(new ItemStack(MItems.CannaSeedPurple, 1));
        }
        if (metadata <= 3)
        {
        	return null;
        }
        return ItemsToDrop;
    }
	
    protected PurpleCannabisPlantThree()
    {
    	this.setBlockName("PurpleCannabisPlantThree");
        this.setBlockTextureName(RefStrings.MODID + ":pc");
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F); //this.setBlockBounds(StartingPointX, StartingPointY, StartingPointZ, X, Y, Z);
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
        return 2;
    }

}
