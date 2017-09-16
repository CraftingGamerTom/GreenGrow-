package com.craftinggamertom.creativeTabs;

import com.craftinggamertom.item.MItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTab extends CreativeTabs {

	public CreativeTab(String lable) {
		super(lable);
	}

	@Override
	public Item getTabIconItem() {
		
		return MItems.CannaSeedGreen;
	}

}
