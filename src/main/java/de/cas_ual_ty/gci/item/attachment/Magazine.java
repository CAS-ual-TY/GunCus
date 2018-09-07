package de.cas_ual_ty.gci.item.attachment;

public class Magazine extends Attachment
{
	protected int extraCapacity;
	protected float reloadTimeModifier;
	
	public Magazine(int id, String rl)
	{
		super(id, rl);
		
		this.extraCapacity = 0;
		this.reloadTimeModifier = 1F;
	}
	
	@Override
	public EnumAttachmentType getType()
	{
		return EnumAttachmentType.MAGAZINE;
	}
	
	public int getExtraCapacity()
	{
		return this.extraCapacity;
	}
	
	public float getReloadTimeModifier()
	{
		return this.reloadTimeModifier;
	}
	
	public Magazine setExtraCapacity(int extraCapacity)
	{
		this.extraCapacity = extraCapacity;
		return this;
	}
	
	public Magazine setReloadTimeModifier(float reloadTimeModifier)
	{
		this.reloadTimeModifier = reloadTimeModifier;
		return this;
	}
}
