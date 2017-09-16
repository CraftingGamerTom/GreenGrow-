package com.craftinggamertom.slots;

import com.craftinggamertom.item.MItems;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class PBarrelWaterSlot extends Slot{
	public PBarrelWaterSlot(IInventory inventory, int slotIndex, int xDisplayPosition, int yDisplayPosition)
	{
		super(inventory, slotIndex, xDisplayPosition, yDisplayPosition);
	}

	   @Override
	   public boolean isItemValid(ItemStack itemstack) {
	      return itemstack.getItem() == Items.water_bucket;
	   }

	   @Override
	   public int getSlotStackLimit() {
	      return 1;
	   }
}
