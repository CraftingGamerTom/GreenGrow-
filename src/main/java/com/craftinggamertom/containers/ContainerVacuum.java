package com.craftinggamertom.containers;

import com.craftinggamertom.slots.PBarrelCocaSlot;
import com.craftinggamertom.slots.PBarrelPasteSlot;
import com.craftinggamertom.slots.PBarrelWaterSlot;
import com.craftinggamertom.slots.VacuumBaseSlot;
import com.craftinggamertom.slots.VacuumLeavesSlot;
import com.craftinggamertom.slots.VacuumShatterSlot;
import com.craftinggamertom.tileEntities.PBTileEntity;
import com.craftinggamertom.tileEntities.VTileEntity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerVacuum extends Container{
private VTileEntity te;

private int furnaceBurnTime;
private int currentItemTime;
private int furnaceCookTime;
	
	public ContainerVacuum(InventoryPlayer playerInventory, VTileEntity te)
	{
		this.te = te;
		
		/*
		 * SLOTS:
		 * 
		 * Tile Entity 0-8 ........ 0  - 8
		 * Player Inventory 9-35 .. 9  - 35
		 * Player Inventory 0-8 ... 36 - 44
		 */
		/*
		 * OLDDDDDDD
	    // Tile Entity, Slot 0-8, Slot IDs 0-8
	    for (int y = 0; y < 3; ++y) {
	        for (int x = 0; x < 3; ++x) {
	            this.addSlotToContainer(new Slot(te, x + y * 3, 62 + x * 18, 17 + y * 18));
	        }
	    }
		*/
		
		//New Version
		this.addSlotToContainer(new VacuumBaseSlot(te, 0, 56, 17));
		this.addSlotToContainer(new VacuumLeavesSlot(te, 1, 56, 53));
		this.addSlotToContainer(new VacuumShatterSlot(te, 2, 112, 35));

	    // Player Inventory, Slot 9-35, Slot IDs 9-35
	    for (int y = 0; y < 3; ++y) {
	        for (int x = 0; x < 9; ++x) {
	            this.addSlotToContainer(new Slot(playerInventory, x + (y * 9) + 9, 8 + (x * 18), 84 + (y * 18)));
	        }
	    }

	    // Player Inventory, Slot 0-8, Slot IDs 36-44
	    for (int x = 0; x < 9; ++x) {
	        this.addSlotToContainer(new Slot(playerInventory, x, 8 + (x * 18), 142));
	    }
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.te.isUseableByPlayer(player);
	}
	
	
	/*For Shift + Right-Click stack
	 * We differentiate between different slots and run the mergeItemStack method with different arguments. This method does the real merging process.
 
mergeItemStack(ItemStack stack, int start, int end, boolean backwards)
stack is the ItemStack that should be merged.
start is the slot index where the merge destination range starts (inclusive).
end is the slot index where the merge destination range ends (exclusive!).
If backwards is true, the search for a valid destination is run backwards.
The method returns true if merging was successful.
The method does not check if the Item can be placed in the slot (isItemValidForSlot), remember this when setting up the ranges.

The behaviour I want to achieve is the following:
Shift Right Clicks in the TileEntity's inventory merge the stack to the player's inventory.
Shift Right Clicks in the player's inventory merge the stack to the TileEntity's inventory.
It's possible to do more complicated merges, but this is enough for now.
	 * (non-Javadoc)
	 * @see net.minecraft.inventory.Container#transferStackInSlot(net.minecraft.entity.player.EntityPlayer, int)
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
		 ItemStack previous = null;
		 Slot slot;
		 slot = (Slot) this.inventorySlots.get(fromSlot);
		    if (slot != null && slot.getHasStack()) {
		        ItemStack current = slot.getStack();
		        previous = current.copy();

		        // [...] Custom behavior
		        if (fromSlot < 3) // Set to the number of Tile Entity Slots //CAUSED DUPE IF NOT CORRECT
		        {
		            // From TE Inventory to Player Inventory
		            if (!this.mergeItemStack(current, 3, 39, true)) // set to (current, Number of Title Entity Slots, Number where Inventory starts) -usually 39
		                return null;
		        } else {
		            // From Player Inventory to TE Inventory
		            if (!this.mergeItemStack(current, 0, 3, false)) // set to (current, start of title entity index, last tile entity index)
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
	    {
	    	index = endIndex - 1;
	    }
	        

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
	@Override
    public void addCraftingToCrafters(ICrafting listener)
    {
            super.addCraftingToCrafters(listener);
            listener.sendProgressBarUpdate(this, 0, te.furnaceBurnTime);
            listener.sendProgressBarUpdate(this, 0, te.currentItemTime);
            listener.sendProgressBarUpdate(this, 0, te.furnaceCookTime);
    }
    
    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)crafters.get(i);

            if (furnaceBurnTime != te.furnaceBurnTime)
            {
                icrafting.sendProgressBarUpdate(this, 0, te.furnaceBurnTime);
            }

            if (currentItemTime != te.currentItemTime)
            {
                icrafting.sendProgressBarUpdate(this, 1, te.currentItemTime);
            }

            if (furnaceCookTime != te.furnaceCookTime)
            {
                icrafting.sendProgressBarUpdate(this, 2, te.furnaceCookTime);
            }
        }

        furnaceBurnTime = te.furnaceBurnTime;
        currentItemTime = te.currentItemTime;
        furnaceCookTime = te.furnaceCookTime;
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
	{
		switch (id)
		{
		case 0:
			te.furnaceBurnTime = data;
		case 1:
			te.currentItemTime = data;
		case 2:
			te.furnaceCookTime = data;
		}
	}
}
