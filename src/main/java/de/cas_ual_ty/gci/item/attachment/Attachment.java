package de.cas_ual_ty.gci.item.attachment;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import de.cas_ual_ty.gci.GunCus;
import de.cas_ual_ty.gci.item.ItemGCI;

public abstract class Attachment extends ItemGCI
{
	public static ArrayList[] attachmentList = new ArrayList[EnumAttachmentType.values().length];
	
	static
	{
		for(int i = 0; i < attachmentList.length; ++i)
		{
			attachmentList[i] = new ArrayList<Attachment>();
		}
	}
	
	public static Attachment getAttachment(int slot, int id)
	{
		return (Attachment) attachmentList[slot].get(id);
	}
	
	public static int getAmmountForSlot(int slot)
	{
		return attachmentList[slot].size();
	}
	
	private int id;
	
	public Attachment(int id, String rl)
	{
		super(rl);
		
		this.id = id;
		
		while(attachmentList[this.getSlot()].size() <= id)
		{
			attachmentList[this.getSlot()].add(null);
		}
		attachmentList[this.getSlot()].set(id, this);
	}
	
	public int getID()
	{
		return this.id;
	}
	
	public int getSlot()
	{
		return this.getType().getSlot();
	}
	
	public String getInformationString()
	{
		return I18n.translateToLocal(this.getUnlocalizedName() + ".name").trim();
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack itemStack)
	{
		return this.getInformationString();
	}
	
	public ItemStack createItemStack()
	{
		if(this.id == 0)
		{
			return ItemStack.EMPTY;
		}
		else
		{
			return new ItemStack(this);
		}
	}
	
	public boolean shouldRegister()
	{
		return this.getID() != 0;
	}
	
	public boolean shouldRender()
	{
		return this.shouldRegister();
	}
	
	public boolean shouldLoadModel()
	{
		return this.shouldRender();
	}
	
	public abstract EnumAttachmentType getType();
	
	@Override
	public void addInformation(ItemStack itemStack, World world, List<String> list, ITooltipFlag flag)
	{
		list.add("§e" + getSlotTranslated(this.getSlot()) + " §8" + getAttachmentTranslated(false) + "");
	}
	
	public static String getSlotTranslated(int slot)
	{
		return I18n.translateToLocal("local." + GunCus.MOD_ID + ":slot" + slot + ".name").trim();
	}
	
	public static String getAttachmentTranslated(boolean plural)
	{
		return I18n.translateToLocal("local." + GunCus.MOD_ID + ":attachment" + (plural ? "s" : "") + ".name").trim();
	}
}
