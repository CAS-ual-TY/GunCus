package de.cas_ual_ty.guncus.item;

import java.util.ArrayList;
import java.util.List;

import de.cas_ual_ty.guncus.GunCus;
import de.cas_ual_ty.guncus.item.attachments.EnumAttachmentType;
import de.cas_ual_ty.guncus.util.RandomTradeBuilder;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public abstract class AttachmentItem extends MakerItem
{
    public static final List<AttachmentItem> ALL_ATTACHMENTS_LIST = new ArrayList<>();
    
    protected float zoomModifier;
    protected float extraZoom;
    protected float extraDamage;
    protected float driftModifier;
    protected float speedModifier;
    protected float spreadModifierVertical;
    protected float spreadModifierHorizontal;
    protected float inaccuracyModifier;
    protected float inaccuracyModifierMoving;
    protected float inaccuracyModifierStill;
    
    public AttachmentItem(Properties properties, int craftAmount, ItemStack... materials)
    {
        super(properties, craftAmount, materials);
        
        this.zoomModifier = 1F;
        this.extraZoom = 0F;
        this.extraDamage = 0F;
        this.driftModifier = 1F;
        this.speedModifier = 1F;
        this.spreadModifierVertical = 1F;
        this.spreadModifierHorizontal = 1F;
        this.inaccuracyModifier = 1F;
        this.inaccuracyModifierMoving = 1F;
        this.inaccuracyModifierStill = 1F;
        
        AttachmentItem.ALL_ATTACHMENTS_LIST.add(this);
    }
    
    public AttachmentItem(Properties properties, ItemStack... materials)
    {
        this(properties, 1, materials);
    }
    
    public AttachmentItem setDefaultTradeable()
    {
        new RandomTradeBuilder(2, 50, 0.05F).setEmeraldPriceFor(16, this).registerLevel(2);
        return this;
    }
    
    public abstract EnumAttachmentType getType();
    
    public float getZoomModifier()
    {
        return this.zoomModifier;
    }
    
    public float getExtraZoom()
    {
        return this.extraZoom;
    }
    
    public float getExtraDamage()
    {
        return this.extraDamage;
    }
    
    public float getDriftModifier()
    {
        return this.driftModifier;
    }
    
    public float getSpeedModifier()
    {
        return this.speedModifier;
    }
    
    public float getSpreadModifierVertical()
    {
        return this.spreadModifierVertical;
    }
    
    public float getSpreadModifierHorizontal()
    {
        return this.spreadModifierHorizontal;
    }
    
    public float getInaccuracyModifier()
    {
        return this.inaccuracyModifier;
    }
    
    public float getInaccuracyModifierMoving()
    {
        return this.inaccuracyModifierMoving;
    }
    
    public float getInaccuracyModifierStill()
    {
        return this.inaccuracyModifierStill;
    }
    
    public AttachmentItem setZoomModifier(float zoomModifier)
    {
        this.zoomModifier = zoomModifier;
        return this;
    }
    
    public AttachmentItem setExtraZoom(float extraZoom)
    {
        this.extraZoom = extraZoom;
        return this;
    }
    
    public AttachmentItem setExtraDamage(float extraDamage)
    {
        this.extraDamage = extraDamage;
        return this;
    }
    
    public AttachmentItem setDriftModifier(float driftModifier)
    {
        this.driftModifier = driftModifier;
        return this;
    }
    
    public AttachmentItem setSpeedModifier(float speedModifier)
    {
        this.speedModifier = speedModifier;
        return this;
    }
    
    public AttachmentItem setSpreadModifierVertical(float spreadModifierVertical)
    {
        this.spreadModifierVertical = spreadModifierVertical;
        return this;
    }
    
    public AttachmentItem setSpreadModifierHorizontal(float spreadModifierHorizontal)
    {
        this.spreadModifierHorizontal = spreadModifierHorizontal;
        return this;
    }
    
    public AttachmentItem setInaccuracyModifier(float inaccuracyModifier)
    {
        this.inaccuracyModifier = inaccuracyModifier;
        return this;
    }
    
    public AttachmentItem setInaccuracyModifierMoving(float inaccuracyModifierMoving)
    {
        this.inaccuracyModifierMoving = inaccuracyModifierMoving;
        return this;
    }
    
    public AttachmentItem setInaccuracyModifierStill(float inaccuracyModifierStill)
    {
        this.inaccuracyModifierStill = inaccuracyModifierStill;
        return this;
    }
    
    public final AttachmentItem getDefaultOfSameSlot()
    {
        return EnumAttachmentType.getSlot(this.getSlot()).getDefault();
    }
    
    public boolean isDefault()
    {
        return this == this.getType().getDefault();
    }
    
    public int getSlot()
    {
        return this.getType().getSlot();
    }
    
    public String getAttachmentRegistryName()
    {
        return this.isDefault() ? "" : this.getRegistryName().toString();
    }
    
    @Override
    public String getTranslationKey()
    {
        return this.isDefault() ? "item." + GunCus.MOD_ID + "." + this.getType().getKey() + "_default" : super.getTranslationKey();
    }
    
    public String getStringSuffix()
    {
        return this.isDefault() ? "" : ("_" + this.getRegistryName().getPath().toString());
    }
    
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        
        tooltip.add(
            this.getType().getDisplayName().setStyle(Style.EMPTY.applyFormatting(TextFormatting.YELLOW))
                .appendString(" ")
                .append(AttachmentItem.getAttachmentTranslated(false).setStyle(Style.EMPTY.applyFormatting(TextFormatting.DARK_GRAY))));
    }
    
    public IFormattableTextComponent getInformationString()
    {
        return new TranslationTextComponent(this.getTranslationKey()).setStyle(Style.EMPTY.applyFormatting(TextFormatting.YELLOW));
    }
    
    public static IFormattableTextComponent getAttachmentTranslated(boolean plural)
    {
        return new TranslationTextComponent("local." + GunCus.MOD_ID + ".attachment" + (plural ? "s" : ""));
    }
    
    public static AttachmentItem[][] buildDefaultArray()
    {
        AttachmentItem[][] attachments = new AttachmentItem[EnumAttachmentType.LENGTH][];
        
        for(int i = 0; i < attachments.length; ++i)
        {
            attachments[i] = new AttachmentItem[] { EnumAttachmentType.getSlot(i).getDefault() };
        }
        
        return attachments;
    }
}
