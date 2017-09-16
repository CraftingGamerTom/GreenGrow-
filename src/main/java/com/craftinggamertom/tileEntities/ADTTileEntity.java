package com.craftinggamertom.tileEntities;

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
//-------------------
import net.minecraft.tileentity.TileEntityFurnace;

public class ADTTileEntity extends TileEntity implements ISidedInventory {
	private static final int[] slotsTop = new int[] { 1, 2, 3, 4, 5, 6, 7, 8 };
	private static final int[] slotsBottom = new int[] { 0 };
	private static final int[] slotsSides = new int[] { 1, 2, 3, 4, 5, 6, 7, 8 };

	private ItemStack[] inventory;
	private String customName;

	public int timeDrying;
	private boolean isOn;
	private int timeNeededToDry = 5000; // should be at least 2500

	public static int blockLight;
	private final int LIGHT_NEEDED = 6;

	public ADTTileEntity() {
		this.inventory = new ItemStack[this.getSizeInventory()];
	}

	public void updateEntity() {
		// Needs sun, and time, and proper items in the inventory.
		boolean updateFlag = false;
		boolean wasOn = isOn;
		if (!this.worldObj.isRemote) {
			if (hasSun()) {
				isOn = true;
				if (dryable()) {
					updateFlag = true;
					if (readyToDry()) {
						dry(inventory[1].getItem());
						timeDrying = 0;
					} else {
						timeDrying++;
					}
				} else {
					timeDrying = 0;
				}
			} else {
				isOn = false;
				if (!dryable()) {
					timeDrying = 0;
				}
			}

		}
		if (wasOn != isOn) {
			updateFlag = true;
			// Allows for changing the look of the block based on if it is
			// being used
			// this.updateADTBlockState(this.timeDrying > 0, this.worldObj,
			// this.xCoord, this.yCoord, this.zCoord);
		}
		if (updateFlag) {
			// System.out.println(timeDrying); // Prints the time spent drying
			this.markDirty();
		}
	}

	private boolean dryable() {
		if (inventory == null) {
			return false;
		}
		for (int i = 1; i < this.inventory.length; i++) {
			if (inventory[i] == null) {
				return false;
			}
			Item theItemToCheck = inventory[i].getItem();
			if (!inventory[1].getItem().equals(inventory[i].getItem())) {
				return false;
			}
			if (!theItemToCheck.equals(MItems.GreenCannabis) && !theItemToCheck.equals(MItems.OrangeCannabis)
					&& !theItemToCheck.equals(MItems.PurpleCannabis) && !theItemToCheck.equals(MItems.Shrooms)) {
				return false;
			}
		}
		return true;
	}

	private boolean readyToDry() {
		return timeDrying >= timeNeededToDry;
		/*
		 * if (this.inventory[0] == null) return true; // Checks if the product
		 * slot is empty. if (!this.inventory[0].isItemEqual(itemstack)) return
		 * false; // Checks if the item is the same in the product slot. int
		 * result = inventory[0].stackSize + itemstack.stackSize; // Gets the
		 * resulting stacksize after being dried to make sure it is legal.
		 * return result <= getInventoryStackLimit() && result <=
		 * this.inventory[2].getMaxStackSize(); //Forge BugFix: Make it respect
		 * stack sizes properly.
		 */
	}

	private boolean hasSun() {
		boolean foo = worldObj.getBlockLightValue(xCoord, yCoord, zCoord) >= LIGHT_NEEDED;
		isOn = foo;
		return foo;
	}

	private void dry(Item itemType) {
		Item product;
		if (itemType.equals(MItems.GreenCannabis)) {
			product = MItems.GreenDriedCannabis;
		} else if (itemType.equals(MItems.OrangeCannabis)) {
			product = MItems.OrangeDriedCannabis;
		} else if (itemType.equals(MItems.PurpleCannabis)) {
			product = MItems.PurpleDriedCannabis;
		} else {
			product = MItems.MagicMushrooms;
		}

		ItemStack theProduct = new ItemStack(product, 9);
		for (int i = 1; i < inventory.length; i++) {
			if (inventory[i] == null) {
			}
			else if (inventory[i].stackSize == 1) {
				inventory[i] = null;
			}
			else if (inventory[i].stackSize >= 2) {
				inventory[i].stackSize--;	
			}

			
		}
		if (inventory[0] == null) {
			inventory[0] = theProduct;
			return;
		}
		Item material = inventory[0].getItem();
		if (material == product) {
			theProduct = new ItemStack(product, inventory[0].stackSize + 9);
			inventory[0] = theProduct;
		}
		this.markDirty();

	}

	public boolean isInProgress() {
		return hasSun();
	}

	/**
	 * Returns an integer between 0 and the passed value representing how close
	 * the current item is to being completely cooked
	 */
	@SideOnly(Side.CLIENT)
	public int getDryProgressScaled(int p_145953_1_) {
		return this.timeDrying * p_145953_1_ / timeNeededToDry;
	}

	/**
	 * Returns an integer between 0 and the passed value representing how much
	 * burn time is left on the current fuel item, where 0 means that the item
	 * is exhausted and the passed value means that the item is fresh
	 */
	@SideOnly(Side.CLIENT)
	public int getLightValueScaled(int scaleVal) {
		if (worldObj.getBlockLightValue(xCoord, yCoord, zCoord) >= LIGHT_NEEDED) {
			return 0;
		}

		return 13;
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
	public int getSizeInventory() {
		return 10;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		// TODO Auto-generated method stub
		ItemStack item = inventory[index];
		return item;
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
					// Just to show that changes happened
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

	// Does not know if it is a valid item for the slot. Use isItemValidForSlot
	// method in GUI - implement it in this file
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
	public String getInventoryName() {
		return "Adv Dry Table";
	}

	@Override
	public boolean hasCustomInventoryName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(xCoord, yCoord, zCoord).getDistanceFrom(player.posX, player.posY,
				player.posZ) <= 64;// getTileEntity(this.getPos()) == this &&
									// player.getDistanceSq(this.pos.add(0.5,
									// 0.5, 0.5)) <= 64;
	}

	@Override
	public void openInventory() {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeInventory() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 1 || index == 2 || index == 3 || index == 4 || index == 5 || index == 6 || index == 7 || index == 8
				|| index == 9) {
			if (stack.getItem() == MItems.GreenCannabis || stack.getItem() == MItems.OrangeCannabis
					|| stack.getItem() == MItems.PurpleCannabis || stack.getItem() == MItems.Shrooms) {
				return true;
			} else {
				return false;
			}
		}
		if (index == 0) {
			return false;
		}
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return p_94128_1_ == 0 ? slotsBottom : (p_94128_1_ == 1 ? slotsTop : slotsSides);
	}

	@Override
	public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
		return this.isItemValidForSlot(p_102007_1_, p_102007_2_);
	}

	/**
	 * Returns true if automation can extract the given item in the given slot
	 * from the given side. Args: Slot, item, side
	 */
	public boolean canExtractItem(int slot, ItemStack itemStack, int side) {
		// Can extract only from the bottom and it will extract any and all
		// dried cannabis or magic mushrooms.
		if (side == 0 && itemStack.getItem() == MItems.GreenDriedCannabis
				|| itemStack.getItem() == MItems.OrangeDriedCannabis
				|| itemStack.getItem() == MItems.PurpleDriedCannabis) {
			return true;
		}
		if (side == 0 && itemStack.getItem() == MItems.MagicMushrooms) {
			return true;
		}
		return false;
	}

	public String getCustomName() {
		return this.customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	// @Override
	public String getName() {
		return this.hasCustomName() ? this.customName : "container.advdrytable";
	}

	// @Override
	public boolean hasCustomName() {
		return this.customName != null && !this.customName.equals("");
	}

	public IChatComponent getDisplayName() {
		return this.hasCustomName() ? new ChatComponentText(this.getName())
				: new ChatComponentTranslation(this.getName());
	}
}
