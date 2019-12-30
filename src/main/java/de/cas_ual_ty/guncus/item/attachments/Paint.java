package de.cas_ual_ty.guncus.item.attachments;

import de.cas_ual_ty.guncus.item.ItemAttachment;

public class Paint extends ItemAttachment
{
    public static final Paint DEFAULT = new Paint();
    
    public Paint(Properties properties)
    {
        super(properties);
    }
    
    protected Paint()
    {
        this(new Properties());
    }
    
    @Override
    public EnumAttachmentType getType()
    {
        return EnumAttachmentType.PAINT;
    }
    
    @Override
    public boolean shouldRender()
    {
        return false;
    }
    
    @Override
    public boolean shouldLoadModel()
    {
        return !this.isDefault();
    }
}
