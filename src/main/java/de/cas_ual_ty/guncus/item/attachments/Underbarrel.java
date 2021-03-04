package de.cas_ual_ty.guncus.item.attachments;

import java.util.ArrayList;
import java.util.List;

import de.cas_ual_ty.guncus.GunCus;
import de.cas_ual_ty.guncus.item.AttachmentItem;
import de.cas_ual_ty.guncus.item.MakerItem;
import net.minecraft.item.ItemStack;

public class Underbarrel extends AttachmentItem
{
    public static final List<MakerItem> UNDERBARRELS_LIST = new ArrayList<>();
    
    public static final Underbarrel DEFAULT = (Underbarrel)new Underbarrel().setRegistryName(GunCus.MOD_ID, "underbarrel_default");
    
    protected float driftModifierShiftStill;
    protected float inaccuracyModifierShiftStill;
    
    public Underbarrel(Properties properties, ItemStack... materials)
    {
        super(properties, materials);
        
        this.driftModifierShiftStill = 1F;
        this.inaccuracyModifierShiftStill = 1F;
        
        if(this.craftAmount > 0 && materials.length > 0)
        {
            UNDERBARRELS_LIST.add(this);
        }
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
