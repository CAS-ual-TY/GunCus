package de.cas_ual_ty.gci.item.attachment;

public class Barrel extends Attachment
{
	protected float extraDamage;
	protected float inaccuracyModifier;
	protected float driftModifier;
	protected float bulletSpeedModifier;
	protected float verticalSpreadModifier;
	protected float horizontalSpreadModifier;
	protected boolean isSilenced;
	protected boolean isFlashHider;
	
	public Barrel(int id, String rl)
	{
		super(id, rl);
		
		this.extraDamage = 0F;
		this.inaccuracyModifier = 1F;
		this.driftModifier = 1F;
		this.bulletSpeedModifier = 1F;
		this.verticalSpreadModifier = 1F;
		this.horizontalSpreadModifier = 1F;
		this.isSilenced = false;
		this.isFlashHider = false;
	}
	
	@Override
	public EnumAttachmentType getType()
	{
		return EnumAttachmentType.BARREL;
	}
	
	public float getExtraDamage()
	{
		return this.extraDamage;
	}
	
	public float getInaccuracyModifier()
	{
		return this.inaccuracyModifier;
	}
	
	public float getDriftModifier()
	{
		return this.driftModifier;
	}
	
	public float getBulletSpeedModifier()
	{
		return this.bulletSpeedModifier;
	}
	
	public float getVerticalSpreadModifier()
	{
		return this.verticalSpreadModifier;
	}
	
	public float getHorizontalSpreadModifier()
	{
		return this.horizontalSpreadModifier;
	}
	
	public boolean getIsSilenced()
	{
		return this.isSilenced;
	}
	
	public boolean getIsFlashHider()
	{
		return this.isFlashHider;
	}
	
	public Barrel setExtraDamage(float extraDamage)
	{
		this.extraDamage = extraDamage;
		return this;
	}
	
	public Barrel setInaccuracyModifier(float inaccuracyModifier)
	{
		this.inaccuracyModifier = inaccuracyModifier;
		return this;
	}
	
	public Barrel setDriftModifier(float driftModifier)
	{
		this.driftModifier = driftModifier;
		return this;
	}
	
	public Barrel setBulletSpeedModifier(float bulletSpeedModifier)
	{
		this.bulletSpeedModifier = bulletSpeedModifier;
		return this;
	}
	
	public Barrel setVerticalSpreadModifier(float verticalSpreadModifier)
	{
		this.verticalSpreadModifier = verticalSpreadModifier;
		return this;
	}
	
	public Barrel setHorizontalSpreadModifier(float horizontalSpreadModifier)
	{
		this.horizontalSpreadModifier = horizontalSpreadModifier;
		return this;
	}
	
	public Barrel setIsSilenced(boolean isSilenced)
	{
		this.isSilenced = isSilenced;
		return this;
	}
	
	public Barrel setIsFlashHider(boolean isFlashHider)
	{
		this.isFlashHider = isFlashHider;
		return this;
	}
}
