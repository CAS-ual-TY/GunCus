package de.cas_ual_ty.gci.item.attachment;

import de.cas_ual_ty.gci.item.ItemGun;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;

public class Accessory extends Attachment
{
	protected float zoomModifier;
	protected float hipFireInccuracyModifier;
	protected float extraZoom;
	protected Laser laser;
	
	public Accessory(int id, String rl)
	{
		super(id, rl);
		
		this.zoomModifier = 1F;
		this.hipFireInccuracyModifier = 0F;
		this.extraZoom = 0F;
		this.laser = null;
	}
	
	@Override
	public EnumAttachmentType getType()
	{
		return EnumAttachmentType.ACCESSORY;
	}
	
	@Override
	public String getInformationString(ItemGun gun, ItemStack gunStack)
	{
		return this.getInformationString() + (this.getID() == 0 ? "" : (" §f(" + (gun.isAccessoryTurnedOn(gunStack) ? "+" : "-") + ")"));
	}
	
	@Override
	public String getInformationString()
	{
		return I18n.translateToLocal(this.getUnlocalizedName() + ".name").trim();
	}
	
	public float getZoomModifier()
	{
		return this.zoomModifier;
	}
	
	public float getHipFireInccuracyModifier()
	{
		return this.hipFireInccuracyModifier;
	}
	
	public float getExtraZoom()
	{
		return this.extraZoom;
	}
	
	public Laser getLaser()
	{
		return this.laser;
	}
	
	public Accessory setZoomModifier(float zoomModifier)
	{
		this.zoomModifier = zoomModifier;
		return this;
	}
	
	public Accessory setHipFireInccuracyModifier(float hipFireInccuracyModifier)
	{
		this.hipFireInccuracyModifier = hipFireInccuracyModifier;
		return this;
	}
	
	public Accessory setExtraZoom(float extraZoom)
	{
		this.extraZoom = extraZoom;
		return this;
	}
	
	public Accessory setLaser(Laser laser)
	{
		this.laser = laser;
		return this;
	}
	
	public static class Laser
	{
		protected float r;
		protected float g;
		protected float b;
		
		protected double maxRange;
		
		protected boolean isBeam;
		protected boolean isPoint;
		protected boolean isRangeFinder;
		
		public Laser(float r, float g, float b, double maxRange, boolean isBeam, boolean isPoint, boolean isRangeFinder)
		{
			this.r = r;
			this.g = g;
			this.b = b;
			this.maxRange = maxRange;
			this.isBeam = isBeam;
			this.isPoint = isPoint;
			this.isRangeFinder = isRangeFinder;
		}
		
		public float getR()
		{
			return this.r;
		}
		
		public float getG()
		{
			return this.g;
		}
		
		public float getB()
		{
			return this.b;
		}
		
		public double getMaxRange()
		{
			return this.maxRange;
		}
		
		public boolean isBeam()
		{
			return this.isBeam;
		}
		
		public boolean isPoint()
		{
			return this.isPoint;
		}
		
		public boolean isRangeFinder()
		{
			return this.isRangeFinder;
		}
	}
}
