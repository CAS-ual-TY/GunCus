package de.cas_ual_ty.guncus.item.attachments;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import de.cas_ual_ty.guncus.GunCus;
import de.cas_ual_ty.guncus.item.AttachmentItem;
import de.cas_ual_ty.guncus.item.MakerItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class Optic extends AttachmentItem
{
    public static final List<MakerItem> MAKER_OPTICS_LIST = new ArrayList<>();
    
    public static final Optic DEFAULT = (Optic)new Optic().setNoOverlay().setRegistryName(GunCus.MOD_ID, "optic_default");
    
    protected Supplier<ResourceLocation> overlay;
    protected EnumOpticType opticType;
    
    public Optic(Properties properties, ItemStack... materials)
    {
        super(properties, materials);
        
        this.setExtraZoom(1F);
        this.overlay = () -> new ResourceLocation(this.getRegistryName().getNamespace(), "textures/gui/sights/" + this.getRegistryName().getPath() + ".png");
        this.opticType = EnumOpticType.NORMAL;
        
        if(this.craftAmount > 0 && materials.length > 0)
        {
            MAKER_OPTICS_LIST.add(this);
        }
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
        return true;
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
    
    public Optic setNoOverlay()
    {
        this.overlay = () -> null;
        return this;
    }
    
    public Optic setOverlay(Supplier<ResourceLocation> overlay)
    {
        if(overlay != null)
        {
            this.overlay = overlay;
        }
        return this;
    }
    
    public Optic setOpticType(EnumOpticType opticType)
    {
        this.opticType = opticType;
        return this;
    }
    
    @Override
    public IFormattableTextComponent getInformationString()
    {
        return super.getInformationString().append(this.getInformationStringSuffix());
    }
    
    public IFormattableTextComponent getInformationStringSuffix()
    {
        return new StringTextComponent((this.isDefault() ? "" : (" (" + this.getZoom() + ")"))).setStyle(Style.EMPTY.applyFormatting(TextFormatting.WHITE));
    }
    
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        
        tooltip.add(
            new StringTextComponent("" + this.getZoom())
                .appendString(" ")
                .append(new TranslationTextComponent("local.guncus.zoom")).setStyle(Style.EMPTY.applyFormatting(TextFormatting.WHITE)));
    }
    
    public static enum EnumOpticType
    {
        NORMAL, NIGHT_VISION, THERMAL;
    }
}
