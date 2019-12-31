package de.cas_ual_ty.guncus.item.attachments;

import de.cas_ual_ty.guncus.item.ItemAttachment;

public class Auxiliary extends ItemAttachment
{
    public static final Auxiliary DEFAULT = new Auxiliary();
    
    protected boolean isAllowingReloadWhileZoomed;
    protected int extraFireRate;
    
    public Auxiliary(Properties properties)
    {
        super(properties);
        
        this.isAllowingReloadWhileZoomed = false;
        this.extraFireRate = 0;
    }
    
    protected Auxiliary()
    {
        this(new Properties());
    }
    
    @Override
    public EnumAttachmentType getType()
    {
        return EnumAttachmentType.AUXILIARY;
    }
    
    public boolean getIsAllowingReloadWhileZoomed()
    {
        return this.isAllowingReloadWhileZoomed;
    }
    
    public int getExtraFireRare()
    {
        return this.extraFireRate;
    }
    
    public Auxiliary setIsAllowingReloadWhileZoomed(boolean isAllowingReloadWhileZoomed)
    {
        this.isAllowingReloadWhileZoomed = isAllowingReloadWhileZoomed;
        return this;
    }
    
    public Auxiliary setExtraFireRate(int extraFireRate)
    {
        this.extraFireRate = extraFireRate;
        return this;
    }
}
