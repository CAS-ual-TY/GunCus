package de.cas_ual_ty.guncus.item.attachments;

import de.cas_ual_ty.guncus.item.ItemAttachment;

public class Underbarrel extends ItemAttachment
{
    public static final Underbarrel DEFAULT = new Underbarrel();
    
    protected float driftModifierShiftStill;
    protected float inaccuracyModifierShiftStill;
    
    public Underbarrel(Properties properties)
    {
        super(properties);
        
        this.driftModifierShiftStill = 1F;
        this.inaccuracyModifierShiftStill = 1F;
    }
    
    protected Underbarrel()
    {
        this(new Properties());
    }
    
    @Override
    public EnumAttachmentType getType()
    {
        return EnumAttachmentType.UNDERBARREL;
    }
    
    public float getDriftModifierShiftStill()
    {
        return this.driftModifierShiftStill;
    }
    
    public float getInaccuracyModifierShiftStill()
    {
        return this.inaccuracyModifierShiftStill;
    }
    
    public Underbarrel setDriftModifierShiftStill(float driftModifierShiftStill)
    {
        this.driftModifierShiftStill = driftModifierShiftStill;
        return this;
    }
    
    public Underbarrel setInaccuracyModifierShiftStill(float inaccuracyModifierShiftStill)
    {
        this.inaccuracyModifierShiftStill = inaccuracyModifierShiftStill;
        return this;
    }
}
