package de.cas_ual_ty.guncus.item.attachments;

import java.util.List;
import java.util.function.Supplier;

import de.cas_ual_ty.guncus.item.ItemAttachment;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class Optic extends ItemAttachment
{
    public static final Optic DEFAULT = new Optic();
    
    protected Supplier<ResourceLocation> overlay;
    protected EnumOpticType opticType;
    
    public Optic(Properties properties)
    {
        super(properties);
        
        this.setExtraZoom(1F);
        this.overlay = this::getDefaultOverlay;
        this.opticType = EnumOpticType.NORMAL;
    }
    
    protected Optic()
    {
        this(new Properties());
    }
    
    @Override
    public EnumAttachmentType getType()
    {
        return EnumAttachmentType.OPTIC;
    }
    
    public boolean isCompatibleWithMagnifiers()
    {
        return this.getZoom() <= 4F;
    }
    
    public boolean isCompatibleWithExtraZoom()
    {
        return !this.isCompatibleWithMagnifiers();
    }
    
    public boolean canAim()
    {
        return !this.isDefault() && this.getOverlay() != null;
    }
    
    public ResourceLocation getDefaultOverlay()
    {
        ResourceLocation rl = this.getRegistryName();
        return new ResourceLocation(rl.getNamespace(), "textures/gui/sights/" + this.getRegistryName().getPath() + ".png");
    }
    
    public ResourceLocation getOverlay()
    {
        return this.overlay.get();
    }
    
    public boolean getIsClosedScope()
    {
        return this.getZoom() >= 6F;
    }
    
    public float getZoom()
    {
        return this.getExtraZoom();
    }
    
    public EnumOpticType getOpticType()
    {
        return this.opticType;
    }
    
    public Optic setOverlay(Supplier<ResourceLocation> overlay)
    {
        this.overlay = overlay;
        return this;
    }
    
    public Optic setOpticType(EnumOpticType opticType)
    {
        this.opticType = opticType;
        return this;
    }
    
    @Override
    public ITextComponent getInformationString()
    {
        return new StringTextComponent(super.getInformationString().getString() + (this.isDefault() ? "" : (" (" + this.getZoom() + ")")));
    }
    
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        
        ITextComponent value = new StringTextComponent("" + this.getZoom());
        ITextComponent zoom = new TranslationTextComponent("local.guncus.zoom");
        zoom.getStyle().applyFormatting(TextFormatting.DARK_GRAY);
        tooltip.add(new StringTextComponent(value.getString() + " " + zoom.getString()));
    }
    
    public static enum EnumOpticType
    {
        NORMAL, NIGHT_VISION, THERMAL;
    }
}
