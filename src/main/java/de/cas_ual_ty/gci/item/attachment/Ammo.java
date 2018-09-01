package de.cas_ual_ty.gci.item.attachment;

import de.cas_ual_ty.gci.item.ItemGun;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Ammo extends Attachment
{
	protected Item replacementCartridge;
	
	public Ammo(int id, String rl)
	{
		super(id, rl);
		
		this.replacementCartridge = null;
	}
	
	public Item getUsedCartrige(ItemStack itemStack, ItemGun itemGun)
	{
		return this.replacementCartridge == null ? itemGun.getCartridge() : this.getReplacementCartridge();
	}
	
	@Override
	public ItemStack createItemStack()
	{
		return new ItemStack(this);
	}
	
	@Override
	public boolean shouldRender()
	{
		return false;
	}
	
	@Override
	public EnumAttachmentType getType()
	{
		return EnumAttachmentType.AMMO;
	}

	public Item getReplacementCartridge()
	{
		return replacementCartridge;
	}

	public Ammo setReplacementCartridge(Item replacementCartridge)
	{
		this.replacementCartridge = replacementCartridge;
		return this;
	}
}
