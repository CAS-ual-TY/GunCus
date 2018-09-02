package de.cas_ual_ty.gci.item.attachment;

import de.cas_ual_ty.gci.GunCus;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

public class Optic extends Attachment
{
	protected final ResourceLocation overlay;

	protected float zoom;
	protected EnumOpticType opticType;

	public Optic(int id, String rl)
	{
		super(id, rl);

		this.overlay = new ResourceLocation(GunCus.MOD_ID, "textures/gui/sights/" + rl + ".png");

		this.zoom = 1F;
		this.opticType = EnumOpticType.NORMAL;
	}

	@Override
	public EnumAttachmentType getType()
	{
		return EnumAttachmentType.OPTIC;
	}

	@Override
	public String getInformationString()
	{
		return I18n.translateToLocal(this.getUnlocalizedName() + ".name").trim() + " §f(" + this.getZoom() + "x)";
	}

	public boolean canAim()
	{
		return this.shouldRegister();
	}

	public final ResourceLocation getOverlay()
	{
		return this.overlay;
	}

	public boolean getIsClosedScope()
	{
		return this.getZoom() >= 6F;
	}

	public float getZoom()
	{
		return this.zoom;
	}

	public EnumOpticType getOpticType()
	{
		return this.opticType;
	}

	public Optic setZoom(float zoom)
	{
		this.zoom = zoom;
		return this;
	}

	public Optic setOpticType(EnumOpticType opticType)
	{
		this.opticType = opticType;
		return this;
	}

	public static enum EnumOpticType
	{
		NORMAL, NIGHT_VISION, THERMAL; 
	}
}
