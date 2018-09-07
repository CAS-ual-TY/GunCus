package de.cas_ual_ty.gci.item.attachment;

public class Paint extends Attachment
{
	public Paint(int id, String rl)
	{
		super(id, rl);
	}
	
	@Override
	public EnumAttachmentType getType()
	{
		return EnumAttachmentType.PAINT;
	}
	
	@Override
	public boolean shouldRender()
	{
		return false;
	}
	
	@Override
	public boolean shouldLoadModel()
	{
		return this.getID() != 0;
	}
}
