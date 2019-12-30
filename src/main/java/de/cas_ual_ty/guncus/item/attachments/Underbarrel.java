package de.cas_ual_ty.guncus.item.attachments;

import de.cas_ual_ty.guncus.item.ItemAttachment;

public class Underbarrel extends ItemAttachment
{
    public static final Underbarrel DEFAULT = new Underbarrel();
    
    public Underbarrel(Properties properties)
    {
        super(properties);
        
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
}
