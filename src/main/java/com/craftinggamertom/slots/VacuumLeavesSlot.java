package com.craftinggamertom.slots;

import com.craftinggamertom.item.MItems;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class VacuumLeavesSlot extends Slot {
	public VacuumLeavesSlot(IInventory inventory, int slotIndex, int xDisplayPosition, int yDisplayPosition)
	{
		super(inventory, slotIndex, xDisplayPosition, yDisplayPosition);
	}

	   @Override
	   public boolean isItemValid(ItemStack itemstack) {
	      return itemstack.getItem() == MItems.CannabisLeaves || itemstack.getItem() == MItems.CannabisStems;
	      
	   }

	   @Override
	   public int getSlotStackLimit() {
	      return 64;
	   }
}
