package de.cas_ual_ty.guncus.item;

import java.util.ArrayList;
import java.util.List;

import de.cas_ual_ty.guncus.GunCus;
import de.cas_ual_ty.guncus.item.attachments.EnumAttachmentType;
import de.cas_ual_ty.guncus.util.RandomTradeBuilder;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public abstract class ItemAttachment extends Item
{
    public static final ArrayList<ItemAttachment> ATTACHMENTS_LIST = new ArrayList<ItemAttachment>();
    
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
    
    public ItemAttachment(Properties properties)
    {
        super(properties);
        
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
        
        ItemAttachment.ATTACHMENTS_LIST.add(this);
    }
    
    public ItemAttachment setDefaultTradeable()
    {
        new RandomTradeBuilder(2, 5, 0.05F).setEmeraldPriceFor(16, this).registerLevel(2);
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
    
    public ItemAttachment setZoomModifier(float zoomModifier)
    {
        this.zoomModifier = zoomModifier;
        return this;
    }
    
    public ItemAttachment setExtraZoom(float extraZoom)
    {
        this.extraZoom = extraZoom;
        return this;
    }
    
    public ItemAttachment setExtraDamage(float extraDamage)
    {
        this.extraDamage = extraDamage;
        return this;
    }
    
    public ItemAttachment setDriftModifier(float driftModifier)
    {
        this.driftModifier = driftModifier;
        return this;
    }
    
    public ItemAttachment setSpeedModifier(float speedModifier)
    {
        this.speedModifier = speedModifier;
        return this;
    }
    
    public ItemAttachment setSpreadModifierVertical(float spreadModifierVertical)
    {
        this.spreadModifierVertical = spreadModifierVertical;
        return this;
    }
    
    public ItemAttachment setSpreadModifierHorizontal(float spreadModifierHorizontal)
    {
        this.spreadModifierHorizontal = spreadModifierHorizontal;
        return this;
    }
    
    public ItemAttachment setInaccuracyModifier(float inaccuracyModifier)
    {
        this.inaccuracyModifier = inaccuracyModifier;
        return this;
    }
    
    public ItemAttachment setInaccuracyModifierMoving(float inaccuracyModifierMoving)
    {
        this.inaccuracyModifierMoving = inaccuracyModifierMoving;
        return this;
    }
    
    public ItemAttachment setInaccuracyModifierStill(float inaccuracyModifierStill)
    {
        this.inaccuracyModifierStill = inaccuracyModifierStill;
        return this;
    }
    
    public final ItemAttachment getDefaultOfSameSlot()
    {
        return EnumAttachmentType.getSlot(this.getSlot()).getDefault();
    }
    
    public boolean isDefault()
    {
        return this == this.getType().getDefault();
    }
    
    public boolean shouldRender()
    {
        return !this.isDefault();
    }
    
    public boolean shouldLoadModel()
    {
        return this.shouldRender();
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
        
        ITextComponent type = this.getType().getDisplayName().setStyle(new Style().setColor(TextFormatting.YELLOW));
        ITextComponent attachment = ItemAttachment.getAttachmentTranslated(false).setStyle(new Style().setColor(TextFormatting.DARK_GRAY));
        tooltip.add(new StringTextComponent(type.getFormattedText() + " " + attachment.getFormattedText()));
    }
    
    public ITextComponent getInformationString()
    {
        return new TranslationTextComponent(this.getTranslationKey()).setStyle(new Style().setColor(TextFormatting.YELLOW));
    }
    
    public static ITextComponent getAttachmentTranslated(boolean plural)
    {
        return new TranslationTextComponent("local." + GunCus.MOD_ID + ".attachment" + (plural ? "s" : ""));
    }
    
    public static ItemAttachment[][] buildDefaultArray()
    {
        ItemAttachment[][] attachments = new ItemAttachment[EnumAttachmentType.LENGTH][];
        
        for (int i = 0; i < attachments.length; ++i)
        {
            attachments[i] = new ItemAttachment[] { EnumAttachmentType.getSlot(i).getDefault() };
        }
        
        return attachments;
    }
}
