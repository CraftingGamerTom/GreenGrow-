package com.craftinggamertom.tileEntities;

import com.craftinggamertom.block.MBlocks;
import com.craftinggamertom.block.Vacuum;
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
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

import net.minecraft.tileentity.TileEntityFurnace;

public class VTileEntity extends TileEntity implements IInventory {
	private ItemStack[] inventory;
    private String customName = "VacuumCustomName";
	private boolean inProgress;
	private int usedEnergy; //furnaceCookTime
	public int currentItemTime; //currentItemBurnTime
	final private int energyNeededToFinish = 640; //"getItemBurnTime(itemstack)"
	public int furnaceCookTime;
	public int furnaceBurnTime;

    public VTileEntity() {
        this.inventory = new ItemStack[this.getSizeInventory()];
        //storage.addEnergy(750); //Experimental COFH
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
    public int getSizeInventory() {
        return 3;
    }
    @Override
    public ItemStack getStackInSlot(int index) {
        if (index < 0 || index >= this.getSizeInventory())
            return null;
        return this.inventory[index];
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
    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
    	if (index == 0)
    	{
    		if (stack.getItem() == MItems.OilBase)
    		{
    			return true;	
    		}else{
    			return false;
    		}
    	}
    	if (index == 1)
    	{
    		if (stack.getItem() == MItems.CannabisLeaves || stack.getItem() == MItems.CannabisStems)
    		{
    			return true;
    		}else{
    			return false;
    		}
    	}
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
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        NBTTagList nbttaglist = nbt.getTagList("Items", 10);
        this.inventory = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.inventory.length)
            {
                this.inventory[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        this.furnaceBurnTime = nbt.getShort("BurnTime");
        this.furnaceCookTime = nbt.getShort("CookTime");
        this.currentItemTime = getItemBurnTime(this.inventory[1]);

        if (nbt.hasKey("CustomName", 8))
        {
            this.setCustomName(nbt.getString("CustomName"));
        }
    }

    public void writeToNBT(NBTTagCompound p_145841_1_)
    {
        super.writeToNBT(p_145841_1_);
        p_145841_1_.setShort("BurnTime", (short)this.furnaceBurnTime);
        p_145841_1_.setShort("CookTime", (short)this.furnaceCookTime);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.inventory.length; ++i)
        {
            if (this.inventory[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.inventory[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        p_145841_1_.setTag("Items", nbttaglist);

        if (this.hasCustomInventoryName())
        {
            p_145841_1_.setString("CustomName", this.getCustomName());
        }
    }

    
	@Override
	public String getInventoryName() {
		return "Vacuum";
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
     * Returns an integer between 0 and the passed value representing how close the current item is to being completely
     * cooked
     */
    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int p_145953_1_)
    {
        return this.furnaceCookTime * p_145953_1_ / energyNeededToFinish;
    }

    /**
     * Returns an integer between 0 and the passed value representing how much burn time is left on the current fuel
     * item, where 0 means that the item is exhausted and the passed value means that the item is fresh
     */
    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(int p_145955_1_)
    {
        if (this.currentItemTime == 0)
        {
            this.currentItemTime = energyNeededToFinish;
        }

        return this.furnaceBurnTime * p_145955_1_ / this.currentItemTime;
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
                    this.currentItemTime = this.furnaceBurnTime = getItemBurnTime(this.inventory[1]);

                    if (this.furnaceBurnTime > 0)
                    {
                        flag1 = true;

                        if (this.inventory[1] != null)
                        {
                            --this.inventory[1].stackSize;

                            if (this.inventory[1].stackSize == 0)
                            {
                                this.inventory[1] = null;
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canSmelt())
                {
                    ++this.furnaceCookTime;

                    if (this.furnaceCookTime == energyNeededToFinish)
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
                Vacuum.updateVBlockState(this.furnaceBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
        }

        if (flag1)
        {
            this.markDirty();
        }
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
            ItemStack itemstack = new ItemStack(MItems.Shatter, 1);
            if (this.inventory[2] == null) return true;
            if (!this.inventory[2].isItemEqual(itemstack)) return false;
            int result = inventory[2].stackSize + itemstack.stackSize;
            return result <= getInventoryStackLimit() && result <= this.inventory[2].getMaxStackSize(); //Forge BugFix: Make it respect stack sizes properly.
        }
    }

    /**
     * Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack
     */
    public void smeltItem()
    {
        if (this.canSmelt())
        {
            ItemStack itemstack = new ItemStack(MItems.Shatter, 1);

            if (this.inventory[2] == null)
            {
                this.inventory[2] = itemstack.copy();
            }
            else if (this.inventory[2].getItem() == itemstack.getItem())
            {
                this.inventory[2].stackSize += itemstack.stackSize; // Forge BugFix: Results may have multiple items
            }

            --this.inventory[0].stackSize;

            if (this.inventory[0].stackSize <= 0)
            {
                this.inventory[0] = null;
            }
        }
    }

    /**
     * Returns the number of ticks that the supplied fuel item will keep the furnace burning, or 0 if the item isn't
     * fuel
     */
    public static int getItemBurnTime(ItemStack p_145952_0_)
    {
        if (p_145952_0_ == null)
        {
            return 0;
        }
        else
        {
            Item item = p_145952_0_.getItem();

            if(item == MItems.CannabisStems)
            {
            	return 50;
            }
            if (item == MItems.CannabisLeaves)
            {
                return 150;
            }else{
            	return 0;
            }
        }
    }

    public static boolean isItemFuel(ItemStack p_145954_0_)
    {
        /**
         * Returns the number of ticks that the supplied fuel item will keep the furnace burning, or 0 if the item isn't
         * fuel
         */
        return getItemBurnTime(p_145954_0_) > 0;
    }

	
	/*
	public void updateEntity()
	{
		 inProgress = this.usedEnergy > 0;
	        boolean flag1 = false;

	        
	        if (this.inProgress)
	        {
	            usedEnergy = usedEnergy + storage.extractEnergy(8, false);
	        }

	        if (!this.worldObj.isRemote)
	        {
	            if (this.inProgress || this.inventory[1] != null && this.inventory[0] != null)
	            {
	                if (this.usedEnergy == 0 && this.canVacuum())
	                {
	                    this.currentItemTime = this.usedEnergy;

	                    if (this.usedEnergy > 0)
	                    {
	                        flag1 = true;

	                        if (this.inventory[1] != null)
	                        {
	                            --this.inventory[0].stackSize;

	                            if (this.inventory[1].stackSize == 0)
	                            {
	                            	this.inventory[1] = null;
	                                //this.inventory[1] = inventory[1].getItem().getContainerItem(inventory[1]); //get container item for fuel if there is one
	                            }
	                        }
	                    }
	                }

	                if (this.isInProgress() && this.canVacuum())
	                {
	                	usedEnergy = usedEnergy + storage.extractEnergy(8, false);

	                    if (this.usedEnergy == energyNeededToFinish)
	                    {
	                        this.usedEnergy = 0;
	                        this.vacuumItem();
	                        flag1 = true;
	                    }
	                }
	                else
	                {
	                    this.usedEnergy = 0;
	                }
	            }

	            if (inProgress != this.usedEnergy > 0)
	            {
	                flag1 = true;
	                Vacuum.updateVBlockState(this.usedEnergy > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
	            }
	        }

	        if (flag1)
	        {
	            this.markDirty();
	        }
	}
    public boolean isInProgress()
    {
        return this.usedEnergy > 0;
    }
    public void vacuumItem()
    {
        if (this.canVacuum())
        {
            ItemStack itemstack = new ItemStack(MItems.Shatter, 1);

            if (this.inventory[1] == null)
            {
                this.inventory[1] = itemstack.copy();
            }
            else if (this.inventory[1].getItem() == itemstack.getItem())
            {
                this.inventory[1].stackSize += itemstack.stackSize; // Forge BugFix: Results may have multiple items
            }

            --this.inventory[0].stackSize;

            if (this.inventory[0].stackSize <= 0)
            {
                this.inventory[0] = null;
            }
        }
    }
    public boolean canVacuum()
    {	
    		ItemStack itemstack = new ItemStack(MItems.Shatter, 1);
            if (this.inventory[1] == null) return true;
            if (!this.inventory[1].isItemEqual(itemstack)) return false;
            int result = inventory[1].stackSize + itemstack.stackSize;
            return result <= getInventoryStackLimit() && result <= this.inventory[1].getMaxStackSize(); //Forge BugFix: Make it respect stack sizes properly.
    }
    */
    /**
     * Returns an integer between 0 and the passed value representing how close the current item is to being completely
     * cooked
     */
	/*
    @SideOnly(Side.CLIENT)
    public int getVacuumProgressScaled(int p_145953_1_)
    {
        return this.usedEnergy * p_145953_1_ / energyNeededToFinish;
    }
*/
    /**
     * Returns an integer between 0 and the passed value representing how much burn time is left on the current fuel
     * item, where 0 means that the item is exhausted and the passed value means that the item is fresh
     */
	/*
    @SideOnly(Side.CLIENT)
    public int getTimeRemaining(int p_145955_1_)
    {
        if (this.currentItemTime == 0)
        {
            this.currentItemTime = energyNeededToFinish;
        }

        return this.usedEnergy * p_145955_1_ / 32000 ;
    }
    */
}
