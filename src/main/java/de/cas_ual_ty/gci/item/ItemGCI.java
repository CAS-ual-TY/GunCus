package de.cas_ual_ty.gci.item;

import javax.annotation.Nullable;

import de.cas_ual_ty.gci.GunCus;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemGCI extends Item
{
	private String modelRL;
	
	public ItemGCI(String rl)
	{
		this(rl, null);
	}
	
	public ItemGCI(String rl, @Nullable CreativeTabs tab)
	{
		this.setUnlocalizedName(GunCus.MOD_ID + ":" + rl);
		this.setRegistryName(GunCus.MOD_ID + ":" + rl);
		this.modelRL = rl;
		
		if(tab != null)
			this.setCreativeTab(tab);
		else
			this.setCreativeTab(GunCus.TAB_GUNCUS);
	}
	
	public String getModelRL()
	{
		return this.modelRL;
	}
}
