package de.cas_ual_ty.gci.item.attachment;

public class Auxiliary extends Attachment
{
	protected float driftModifierWhenShiftAndStill;
	protected float inaccuracyModifierWhenShiftAndStill;
	protected boolean isAllowingReloadWhileZoomed;
	protected int extraFireRate;
	
	public Auxiliary(int id, String rl)
	
	{
		super(id, rl);
		
		this.driftModifierWhenShiftAndStill = 1F;
		this.inaccuracyModifierWhenShiftAndStill = 1F;
		this.isAllowingReloadWhileZoomed = false;
		this.extraFireRate = 0;
	}
	
	@Override
	public EnumAttachmentType getType()
	{
		return EnumAttachmentType.AUXILIARY;
	}
	
	public float getDriftModifierWhenShiftAndStill()
	{
		return this.driftModifierWhenShiftAndStill;
	}
	
	public float getInaccuracyModifierWhenShiftAndStill()
	{
		return this.inaccuracyModifierWhenShiftAndStill;
	}
	
	public boolean getIsAllowingReloadWhileZoomed()
	{
		return this.isAllowingReloadWhileZoomed;
	}
	
	public Auxiliary setDriftModifierWhenShiftAndStill(float driftModifierWhenShiftAndStill)
	{
		this.driftModifierWhenShiftAndStill = driftModifierWhenShiftAndStill;
		return this;
	}
	
	public Auxiliary setInaccuracyModifierWhenShiftAndStill(float inaccuracyModifierWhenShiftAndStill)
	{
		this.inaccuracyModifierWhenShiftAndStill = inaccuracyModifierWhenShiftAndStill;
		return this;
	}
	
	public Auxiliary setIsAllowingReloadWhileZoomed(boolean isAllowingReloadWhileZoomed)
	{
		this.isAllowingReloadWhileZoomed = isAllowingReloadWhileZoomed;
		return this;
	}
}
