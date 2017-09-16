package com.craftinggamertom.slots;

import com.craftinggamertom.item.MItems;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ADTDryingSlot extends Slot{
	public ADTDryingSlot(IInventory inventory, int slotIndex, int xDisplayPosition, int yDisplayPosition)
	{
		super(inventory, slotIndex, xDisplayPosition, yDisplayPosition);
	}

	   @Override
	   public boolean isItemValid(ItemStack itemstack) {
	      return itemstack.getItem() == MItems.GreenCannabis || itemstack.getItem() == MItems.OrangeCannabis || itemstack.getItem() == MItems.PurpleCannabis|| itemstack.getItem() == MItems.Shrooms;
	      
	   }

	   @Override
	   public int getSlotStackLimit() {
	      return 64;
	   }

}
