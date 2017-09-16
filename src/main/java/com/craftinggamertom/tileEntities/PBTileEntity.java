package com.craftinggamertom.tileEntities;

import com.craftinggamertom.block.MBlocks;
import com.craftinggamertom.block.PlasticBarrel;
import com.craftinggamertom.item.MItems;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
// --- Not Needed --- Used for learning ---
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.item.crafting.FurnaceRecipes;
//-----------------------------------------

public class PBTileEntity extends TileEntity implements ISidedInventory {

	private ItemStack[] inventory;
    private String customName = "PlasticBarrelCustomName";
    
    private int myCustomBurnTime = 600;

    //-----------------------------------
    private static final int[] slotsTop = new int[] {0};
    private static final int[] slotsBottom = new int[] {2, 3};
    private static final int[] slotsSides = new int[] {1};
    /** The ItemStacks that hold the items currently being used in the furnace */
    /** The number of ticks that the furnace will keep burning */
    public int furnaceBurnTime;
    /** The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for */
    public int currentItemBurnTime;
    /** The number of ticks that the current item has been cooking for */
    public int furnaceCookTime;
    private String field_145958_o;
    private static final String __OBFID = "CL_00000357";

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getSizeInventory()
    {
        return 4; //Set to how many title entity containers exist
    }

    /**
     * Returns the stack in slot i
     */
    @Override
    public ItemStack getStackInSlot(int index)
    {
        if (index < 0 || index >= this.getSizeInventory())
        {
        	return null;	
        }
        return this.inventory[index];
    }
    //-----------------------------------
    //_#_#_#_#_#_#_#_#_#_#_#_#_#_#_#_#_#_#_#_
    /**
     * Returns an integer between 0 and the passed value representing how close the current item is to being completely
     * cooked
     */
    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int p_145953_1_)
    {
        return this.furnaceCookTime * p_145953_1_ / myCustomBurnTime;
    }

    /**
     * Returns an integer between 0 and the passed value representing how much burn time is left on the current fuel
     * item, where 0 means that the item is exhausted and the passed value means that the item is fresh
     */
    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(int p_145955_1_)
    {
        if (this.currentItemBurnTime == 0)
        {
            this.currentItemBurnTime = myCustomBurnTime;
        }

        return this.furnaceBurnTime * p_145955_1_ / this.currentItemBurnTime;
    }

    /**
     * Furnace isBurning
     */
    public boolean isBurning()
    {
        return this.furnaceBurnTime > 0;
    }

    public void updateEntity()
    {
        boolean flag = this.furnaceBurnTime > 0;
        boolean flag1 = false;

        if (this.furnaceBurnTime > 0)
        {
            --this.furnaceBurnTime;
        }

        if (!this.worldObj.isRemote)
        {
            if (this.furnaceBurnTime != 0 || this.inventory[1] != null && this.inventory[0] != null)
            {
                if (this.furnaceBurnTime == 0 && this.canSmelt())
                {
                    this.currentItemBurnTime = this.furnaceBurnTime = getItemBurnTime(this.inventory[1]);

                    if (this.furnaceBurnTime > 0)
                    {
                        flag1 = true;

                        if (this.inventory[1] != null)
                        {
                            --this.inventory[1].stackSize;
                            --this.inventory[0].stackSize;

                            if (this.inventory[1].stackSize == 0)
                            {
                            	this.inventory[1] = null;
                                //this.inventory[1] = inventory[1].getItem().getContainerItem(inventory[1]); //get container item for fuel if there is one
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canSmelt())
                {
                    ++this.furnaceCookTime;

                    if (this.furnaceCookTime == myCustomBurnTime)
                    {
                        this.furnaceCookTime = 0;
                        this.smeltItem();
                        flag1 = true;
                    }
                }
                else
                {
                    this.furnaceCookTime = 0;
                }
            }

            if (flag != this.furnaceBurnTime > 0)
            {
                flag1 = true;
                this.updatePBBlockState(this.furnaceBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
        }

        if (flag1)
        {
            this.markDirty();
        }
    }

    /**
     * Update which block the furnace is using depending on whether or not it is burning
     */

    private void updatePBBlockState(boolean isBurning, World p_149931_1_, int p_149931_2_, int p_149931_3_, int p_149931_4_)
    {
        int l = p_149931_1_.getBlockMetadata(p_149931_2_, p_149931_3_, p_149931_4_);
        //PBTileEntity tileentity = (PBTileEntity) p_149931_1_.getTileEntity(p_149931_2_, p_149931_3_, p_149931_4_); //Casted to PBTileEntity
        boolean theBoolean; //Used also for when block is broken in BlockFurnace.class
        theBoolean = true;
        /*//This is meant to change how the block looks - currently broken
        if (isBurning)
        {
            p_149931_1_.setBlock(p_149931_2_, p_149931_3_, p_149931_4_, MBlocks.PBInUse);
        }
        else
        {
            p_149931_1_.setBlock(p_149931_2_, p_149931_3_, p_149931_4_, MBlocks.PBStandby);
        }
        
        
        theBoolean = false;
        p_149931_1_.setBlockMetadataWithNotify(p_149931_2_, p_149931_3_, p_149931_4_, l, 2);

        if (this != null)
        {
            this.validate();
            //p_149931_1_.setTileEntity(p_149931_2_, p_149931_3_, p_149931_4_, this);
        }
        */
    }
  
    /**
     * Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc.
     */
    private boolean canSmelt()
    {
        if (this.inventory[0] == null)
        {
            return false;
        }
        else
        {
            ItemStack itemstack = new ItemStack(MItems.CocainePaste, 1);
            
            ItemStack bucket = new ItemStack(Items.bucket);
            if (inventory[3] != null)
            {
            	if (inventory[3].isItemEqual(bucket))
                {
                	int bucketResult = inventory[3].stackSize + bucket.stackSize;
                    if (bucketResult > this.inventory[3].getMaxStackSize()) return false;	
                }	
            }
            
            
            if (this.inventory[2] == null) return true;
            if (!this.inventory[2].isItemEqual(itemstack)) return false;
            int result = inventory[2].stackSize + itemstack.stackSize;
            return result <= getInventoryStackLimit() && result <= this.inventory[2].getMaxStackSize(); //Forge BugFix: Make it respect stack sizes properly.
        }
    }

    /**
     * Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack
     */
    private void smeltItem()
    {
        if (this.canSmelt())
        {
            ItemStack itemstack = new ItemStack(MItems.CocainePaste, 1);
            ItemStack bucket = new ItemStack(Items.bucket, 1);

            if (this.inventory[3] == null)
            {
                this.inventory[3] = bucket.copy();
            }
            else if (this.inventory[3].getItem() == bucket.getItem())
            {
                this.inventory[3].stackSize += bucket.stackSize; // Forge BugFix: Results may have multiple items
            }
            
            
            if (this.inventory[2] == null)
            {
                this.inventory[2] = itemstack.copy();
            }
            else if (this.inventory[2].getItem() == itemstack.getItem())
            {
                this.inventory[2].stackSize += itemstack.stackSize; // Forge BugFix: Results may have multiple items
            }

        }
    }

    /**
     * Returns the number of ticks that the supplied fuel item will keep the furnace burning, or 0 if the item isn't
     * fuel
     */
    private static int getItemBurnTime(ItemStack p_145952_0_)
    {
        if (p_145952_0_ == null)
        {
            return 0;
        }
        else
        {
            Item item = p_145952_0_.getItem();

            if (item == Items.water_bucket)
            {
            	return 600;
            	//return myCustomBurnTime;
            }
            else{
            	return 0;
            }
        }
    }

    private static boolean isItemFuel(ItemStack p_145954_0_)
    {
        /**
         * Returns the number of ticks that the supplied fuel item will keep the furnace burning, or 0 if the item isn't
         * fuel
         */
        return getItemBurnTime(p_145954_0_) > 0;
    }

    //_#_#_#_#_#_#_#_#_#_#_#_#_#_#_#_#_#_#_#_
    
    public PBTileEntity() {
        this.inventory = new ItemStack[this.getSizeInventory()];
    }

    public String getCustomName() {
        return this.customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    //@Override
    public String getName() {
        return this.hasCustomName() ? this.customName : "container.vacuum";
    }

    //@Override
    public boolean hasCustomName() {
        return this.customName != null && !this.customName.equals("");
    }

    //@Override
    public IChatComponent getDisplayName() {
        return this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatComponentTranslation(this.getName());
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        if (this.getStackInSlot(index) != null) {
            ItemStack itemstack;

            if (this.getStackInSlot(index).stackSize <= count) {
                itemstack = this.getStackInSlot(index);
                this.setInventorySlotContents(index, null);
                this.markDirty();
                return itemstack;
            } else {
                itemstack = this.getStackInSlot(index).splitStack(count);

                if (this.getStackInSlot(index).stackSize <= 0) {
                    this.setInventorySlotContents(index, null);
                } else {
                    //Just to show that changes happened
                    this.setInventorySlotContents(index, this.getStackInSlot(index));
                }

                this.markDirty();
                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int index) {
        ItemStack stack = this.getStackInSlot(index);
        this.setInventorySlotContents(index, null);
        return stack;
    }

    // Does not know if it is a valid item for the slot. Use isItemValidForSlot method in GUI - implement it in this file
    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
    		if (index < 0 || index >= this.getSizeInventory())
                return;

            if (stack != null && stack.stackSize > this.getInventoryStackLimit())
                stack.stackSize = this.getInventoryStackLimit();
                
            if (stack != null && stack.stackSize == 0)
                stack = null;

            this.inventory[index] = stack;
            this.markDirty();
	
            }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    //@Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(xCoord, yCoord, zCoord).getDistanceFrom(player.posX, player.posY, player.posZ) <= 64;//getTileEntity(this.getPos()) == this && player.getDistanceSq(this.pos.add(0.5, 0.5, 0.5)) <= 64;
    }
    //@Override
    public void openInventory(EntityPlayer player) {
    	// Apparently this method does nothing
    	System.out.println("opened - test- this is a bug - contact mod owner for assistance");
    }

    //@Override
    public void closeInventory(EntityPlayer player) {
    	// Apparently this method does nothing
    	System.out.println("closed - test- this is a bug - contact mod owner for assistance");
    }
    //This is for automation -> NOT PLAYERS
    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 0)
		{
			if (stack.getItem() == MItems.Coca)
			{
				return true;	
			}else{
				return false;
			}
		}
		if (index == 1)
		{
			if (stack.getItem() == Items.water_bucket)
			{
				return true;
			}else{
				return false;
			}
		}
		/*//Player is not sopposed to place in final product
		if (index == 2)
		{
			if(stack.getItem() == MItems.CocainePaste)
			{
				return true;
			}else{
				return false;
			}
		}
		if (index == 3)
		{
			if(stack.getItem() == Items.bucket)
			{
				return true;
			}else{
				return false;
			}
		}
		*/
	    return false;
	}
    //@Override
    public int getField(int id) {
        return 0;
    }

    //@Override
    public void setField(int id, int value) {
    }

    //@Override
    public int getFieldCount() {
        return 0;
    }
    //@Override
    public void clear() {
        for (int i = 0; i < this.getSizeInventory(); i++)
            this.setInventorySlotContents(i, null);
    }
    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        NBTTagList list = new NBTTagList();
        for (int i = 0; i < this.getSizeInventory(); ++i) {
            if (this.getStackInSlot(i) != null) {
                NBTTagCompound stackTag = new NBTTagCompound();
                stackTag.setByte("Slot", (byte) i);
                this.getStackInSlot(i).writeToNBT(stackTag);
                list.appendTag(stackTag);
            }
        }
        nbt.setTag("Items", list);

        if (this.hasCustomName()) {
            nbt.setString("CustomName", this.getCustomName());
        }
    }


    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        NBTTagList list = nbt.getTagList("Items", 10);
        for (int i = 0; i < list.tagCount(); ++i) {
            NBTTagCompound stackTag = list.getCompoundTagAt(i);
            int slot = stackTag.getByte("Slot") & 255;
            this.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(stackTag));
        }

        if (nbt.hasKey("CustomName", 8)) {
            this.setCustomName(nbt.getString("CustomName"));
        }
    }

	@Override
	public String getInventoryName() {
		// TODO Auto-generated method stub
		return "Plastic Barrel";
	}

	@Override
	public boolean hasCustomInventoryName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void openInventory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeInventory() {
		// TODO Auto-generated method stub
		
	}
	/**
     * Returns an array containing the indices of the slots that can be accessed by automation on the given side of this
     * block.
     */
    public int[] getAccessibleSlotsFromSide(int p_94128_1_)
    {
        return p_94128_1_ == 0 ? slotsBottom : (p_94128_1_ == 1 ? slotsTop : slotsSides);
    }

    /**
     * Returns true if automation can insert the given item in the given slot from the given side. Args: Slot, item,
     * side
     */
    public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_)
    {
        return this.isItemValidForSlot(p_102007_1_, p_102007_2_);
    }

    /**
     * Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item,
     * side
     */
    public boolean canExtractItem(int slot, ItemStack itemStack, int side)
    {
    	if(slot == 2 && itemStack.getItem() == MItems.CocainePaste && side == 0)
    	{
    		return true;
    	}
    	if(slot == 3 && itemStack.getItem() == Items.bucket && side == 0)
    	{
    		return true;
    	}
        return false;
    }
}
