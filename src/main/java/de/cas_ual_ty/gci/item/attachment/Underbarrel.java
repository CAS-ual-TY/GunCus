package de.cas_ual_ty.gci.item.attachment;

public class Underbarrel extends Attachment
{
	protected float driftModifier;
	protected float inaccuracyModifierMoving;
	protected float inaccuracyModifierStill;
	
	public Underbarrel(int id, String rl)
	{
		super(id, rl);
		
		this.driftModifier = 0F;
		this.inaccuracyModifierMoving = 0F;
		this.inaccuracyModifierStill = 0F;
	}

	@Override
	public EnumAttachmentType getType()
	{
		return EnumAttachmentType.UNDERBARREL;
	}
	
	public float getDriftModifier()
	{
		return this.driftModifier;
	}
	
	public float getInaccuracyModifierMoving()
	{
		return this.inaccuracyModifierMoving;
	}
	
	public float getInaccuracyModifierStill()
	{
		return this.inaccuracyModifierStill;
	}

	public Underbarrel setDriftModifier(float driftModifier)
	{
		this.driftModifier = driftModifier;
		return this;
	}
	
	public Underbarrel setInaccuracyModifierMoving(float inaccuracyModifierMoving)
	{
		this.inaccuracyModifierMoving = inaccuracyModifierMoving;
		return this;
	}

	public Underbarrel setInaccuracyModifierStill(float inaccuracyModifierStill)
	{
		this.inaccuracyModifierStill = inaccuracyModifierStill;
		return this;
	}
}
