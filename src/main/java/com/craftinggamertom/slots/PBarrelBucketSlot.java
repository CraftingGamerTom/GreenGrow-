package com.craftinggamertom.slots;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class PBarrelBucketSlot extends Slot{
	public PBarrelBucketSlot(IInventory inventory, int slotIndex, int xDisplayPosition, int yDisplayPosition)
	{
		super(inventory, slotIndex, xDisplayPosition, yDisplayPosition);
	}

	   @Override
	   public boolean isItemValid(ItemStack itemstack) {
	      //return itemstack.getItem() == Items.bucket; //Allows placement of item into slot
		  return false;
	   }

	   @Override
	   public int getSlotStackLimit() {
	      return 16;
	   }
}
