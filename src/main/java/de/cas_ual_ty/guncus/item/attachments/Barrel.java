package de.cas_ual_ty.guncus.item.attachments;

import de.cas_ual_ty.guncus.item.ItemAttachment;

public class Barrel extends ItemAttachment
{
    public static final Barrel DEFAULT = new Barrel();
    
    protected boolean isSilenced;
    protected boolean isFlashHider; //TODO implement
    
    public Barrel(Properties properties)
    {
        super(properties);
        
        this.isSilenced = false;
        this.isFlashHider = false;
    }
    
    protected Barrel()
    {
        this(new Properties());
    }
    
    public boolean getIsSilenced()
    {
        return this.isSilenced;
    }
    
    public boolean getIsFlashHider()
    {
        return this.isFlashHider;
    }
    
    public Barrel setIsSilenced(boolean isSilenced)
    {
        this.isSilenced = isSilenced;
        return this;
    }
    
    public Barrel setIsFlashHider(boolean isFlashHider)
    {
        this.isFlashHider = isFlashHider;
        return this;
    }
    
    @Override
    public EnumAttachmentType getType()
    {
        return EnumAttachmentType.BARREL;
    }
}
