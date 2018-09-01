package de.cas_ual_ty.gci;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CreativeTabsGCI extends CreativeTabs
{
	protected ItemStack item;
	
	public CreativeTabsGCI(String label)
	{
		this(label, new ItemStack(Items.GUNPOWDER));
	}
	
	public CreativeTabsGCI(String label, ItemStack item)
	{
		super(label);
		this.item = item;
	}

	@Override
	public ItemStack getTabIconItem()
	{
		return this.item;
	}
}
