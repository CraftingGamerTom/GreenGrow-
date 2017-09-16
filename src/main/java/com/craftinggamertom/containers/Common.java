package com.craftinggamertom.containers;

import com.craftinggamertom.main.GUIHandler;
import com.craftinggamertom.tileEntities.PBTileEntity;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class Common {

	/*
	public class ContainerPlasticBarrel extends Container{

	private PBTileEntity te;
	
	public ContainerPlasticBarrel(InventoryPlayer playerInventory, PBTileEntity te){
		this.te = te;
		
		
	//	 * SLOTS:
	//	 * 
	//	 * Tile Entity 0-8 ........ 0  - 8
	//	 * Player Inventory 9-35 .. 9  - 35
	//	 * Player Inventory 0-8 ... 36 - 44
		 
		
	    // Tile Entity, Slot 0-8, Slot IDs 0-8
	    for (int y = 0; y < 3; ++y) {
	        for (int x = 0; x < 3; ++x) {
	            this.addSlotToContainer(new Slot(te, x + y * 3, 62 + x * 18, 17 + y * 18));
	        }
	    }

	    // Player Inventory, Slot 9-35, Slot IDs 9-35
	    for (int y = 0; y < 3; ++y) {
	        for (int x = 0; x < 9; ++x) {
	            this.addSlotToContainer(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
	        }
	    }

	    // Player Inventory, Slot 0-8, Slot IDs 36-44
	    for (int x = 0; x < 9; ++x) {
	        this.addSlotToContainer(new Slot(playerInventory, x, 8 + x * 18, 142));
	    }

	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.te.isUseableByPlayer(player);
	}
//For Shift + Right-Click stack
//We differentiate between different slots and run the mergeItemStack method with different arguments. This method does the real merging process.
// 
//mergeItemStack(ItemStack stack, int start, int end, boolean backwards)
//stack is the ItemStack that should be merged.
//start is the slot index where the merge destination range starts (inclusive).
//end is the slot index where the merge destination range ends (exclusive!).
//If backwards is true, the search for a valid destination is run backwards.
//The method returns true if merging was successful.
//The method does not check if the Item can be placed in the slot (isItemValidForSlot), remember this when setting up the ranges.
//
//The behaviour I want to achieve is the following:
//Shift Right Clicks in the TileEntity's inventory merge the stack to the player's inventory.
//Shift Right Clicks in the player's inventory merge the stack to the TileEntity's inventory.
//It's possible to do more complicated merges, but this is enough for now.
//	  (non-Javadoc)
//	  @see net.minecraft.inventory.Container#transferStackInSlot(net.minecraft.entity.player.EntityPlayer, int)
	 
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
		 ItemStack previous = null;
		    Slot slot = (Slot) this.inventorySlots.get(fromSlot);

		    if (slot != null && slot.getHasStack()) {
		        ItemStack current = slot.getStack();
		        previous = current.copy();

		        // [...] Custom behavior
		        if (fromSlot < 9) {
		            // From TE Inventory to Player Inventory
		            if (!this.mergeItemStack(current, 9, 45, true))
		                return null;
		        } else {
		            // From Player Inventory to TE Inventory
		            if (!this.mergeItemStack(current, 0, 9, false))
		                return null;
		        }
		        // [...] End Custom behavior

		        if (current.stackSize == 0)
		            slot.putStack((ItemStack) null);
		        else
		            slot.onSlotChanged();

		        if (current.stackSize == previous.stackSize)
		            return null;
		        slot.onPickupFromSlot(playerIn, current);
		    }
		    return previous;
	}
	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean useEndIndex) {
	    boolean success = false;
	    int index = startIndex;

	    if (useEndIndex)
	        index = endIndex - 1;

	    Slot slot;
	    ItemStack stackinslot;

	    if (stack.isStackable()) {
	        while (stack.stackSize > 0 && (!useEndIndex && index < endIndex || useEndIndex && index >= startIndex)) {
	            slot = (Slot) this.inventorySlots.get(index);
	            stackinslot = slot.getStack();

	            if (stackinslot != null && stackinslot.getItem() == stack.getItem() && (!stack.getHasSubtypes() || stack.getItem() == stackinslot.getItem()) && ItemStack.areItemStackTagsEqual(stack, stackinslot)) {
	                int l = stackinslot.stackSize + stack.stackSize;
	                int maxsize = Math.min(stack.getMaxStackSize(), this.determineStackLimit(stack));

	                if (l <= maxsize) {
	                    stack.stackSize = 0;
	                    stackinslot.stackSize = l;
	                    slot.onSlotChanged();
	                    success = true;
	                } else if (stackinslot.stackSize < maxsize) {
	                    stack.stackSize -= stack.getMaxStackSize() - stackinslot.stackSize;
	                    stackinslot.stackSize = stack.getMaxStackSize();
	                    slot.onSlotChanged();
	                    success = true;
	                }
	            }

	            if (useEndIndex) {
	                --index;
	            } else {
	                ++index;
	            }
	        }
	    }

	    if (stack.stackSize > 0) {
	        if (useEndIndex) {
	            index = endIndex - 1;
	        } else {
	            index = startIndex;
	        }

	        while (!useEndIndex && index < endIndex || useEndIndex && index >= startIndex && stack.stackSize > 0) {
	            slot = (Slot) this.inventorySlots.get(index);
	            stackinslot = slot.getStack();

	            // Forge: Make sure to respect isItemValid in the slot.
	            if (stackinslot == null && slot.isItemValid(stack)) {
	                if (stack.stackSize < this.determineStackLimit(stack)) {
	                    slot.putStack(stack.copy());
	                    stack.stackSize = 0;
	                    success = true;
	                    break;
	                } else {
	                    ItemStack newstack = stack.copy();
	                    newstack.stackSize = this.determineStackLimit(stack);
	                    slot.putStack(newstack);
	                    stack.stackSize -= this.determineStackLimit(stack);
	                    success = true;
	                }
	            }

	            if (useEndIndex) {
	                --index;
	            } else {
	                ++index;
	            }
	        }
	    }

	    return success;
	}
	protected int determineStackLimit(ItemStack itemstack)
	{
		int StackLimit = itemstack.getItem().getItemStackLimit();
		return StackLimit;
	}
}

	 * --------------------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * inside of common proxy:
	public void init(FMLInitializationEvent e) 
    {
    	NetworkRegistry.INSTANCE.registerGuiHandler("greengrow", new GUIHandler());
    }
	 * 
	 * --------------------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * package com.craftinggamertom.tileEntities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class PBTileEntity extends TileEntity implements IInventory {

	private ItemStack[] inventory;
    private String customName;

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
        return this.hasCustomName() ? this.customName : "container.plastic_barrel";
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
        return 9;
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
    	System.out.println("opened - test- this is a bug - contact mod owner for assistance");
    }

    //@Override
    public void closeInventory(EntityPlayer player) {
    	System.out.println("closed - test- this is a bug - contact mod owner for assistance");
    }
    
    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
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
		return null;
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
}

	 * 
	 * 
	 */
	
	
}
