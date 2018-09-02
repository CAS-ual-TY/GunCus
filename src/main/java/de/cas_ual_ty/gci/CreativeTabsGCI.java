package de.cas_ual_ty.gci;

import java.util.ArrayList;

import de.cas_ual_ty.gci.item.ItemCartridge;
import de.cas_ual_ty.gci.item.ItemGCI;
import de.cas_ual_ty.gci.item.attachment.Attachment;
import de.cas_ual_ty.gci.item.attachment.EnumAttachmentType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabsGCI extends CreativeTabs
{
	protected ItemStack item;
	protected ArrayList<ItemGCI> items;
	
	public CreativeTabsGCI(String label)
	{
		this(label, null);
	}
	
	public CreativeTabsGCI(String label, ItemStack item)
	{
		super(label);
		this.item = item;
		this.items = new ArrayList<ItemGCI>();
	}
	
	public void shuffleItemStack()
	{
		if(this.items.isEmpty())
		{
			this.items.addAll(ItemCartridge.CARTRIDGES_LIST);
			
			int j;
			
			for(int i = 0; i < EnumAttachmentType.values().length; ++i)
			{
				for(j = 1; j < Attachment.getAmmountForSlot(i); ++j)
				{
					Attachment attachment = Attachment.getAttachment(i, j);
					
					if(attachment != null)
					{
						this.items.add(attachment);
					}
				}
			}
		}
		
		this.item = new ItemStack(this.items.get(GunCus.RANDOM.nextInt(this.items.size())));
	}
	
	@Override
	public ItemStack getIconItemStack()
	{
		return this.getTabIconItem();
	}

	@Override
	public ItemStack getTabIconItem()
	{
		return this.item;
	}
}
