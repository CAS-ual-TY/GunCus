package de.cas_ual_ty.gci.item.attachment;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import de.cas_ual_ty.gci.item.ItemGun;

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
			return r;
		}

		public float getG()
		{
			return g;
		}

		public float getB()
		{
			return b;
		}

		public double getMaxRange()
		{
			return maxRange;
		}

		public boolean isBeam()
		{
			return isBeam;
		}

		public boolean isPoint()
		{
			return isPoint;
		}

		public boolean isRangeFinder()
		{
			return isRangeFinder;
		}
	}
}
