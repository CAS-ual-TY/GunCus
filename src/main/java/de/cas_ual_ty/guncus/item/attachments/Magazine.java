package de.cas_ual_ty.guncus.item.attachments;

import de.cas_ual_ty.guncus.item.ItemAttachment;

public class Magazine extends ItemAttachment
{
    public static final Magazine DEFAULT = new Magazine();
    
    protected int extraCapacity;
    protected float reloadTimeModifier;
    
    public Magazine(Properties properties)
    {
        super(properties);
        
        this.extraCapacity = 0;
        this.reloadTimeModifier = 1F;
    }
    
    protected Magazine()
    {
        this(new Properties());
    }
    
    @Override
    public EnumAttachmentType getType()
    {
        return EnumAttachmentType.MAGAZINE;
    }
    
    public int getExtraCapacity()
    {
        return this.extraCapacity;
    }
    
    public float getReloadTimeModifier()
    {
        return this.reloadTimeModifier;
    }
    
    public Magazine setExtraCapacity(int extraCapacity)
    {
        this.extraCapacity = extraCapacity;
        return this;
    }
    
    public Magazine setReloadTimeModifier(float reloadTimeModifier)
    {
        this.reloadTimeModifier = reloadTimeModifier;
        return this;
    }
}
