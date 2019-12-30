package de.cas_ual_ty.guncus.item.attachments;

import java.util.function.Supplier;

import de.cas_ual_ty.guncus.item.ItemAttachment;
import de.cas_ual_ty.guncus.item.ItemBullet;
import de.cas_ual_ty.guncus.item.ItemGun;

public class Ammo extends ItemAttachment
{
    public static final Ammo DEFAULT = new Ammo();
    
    protected Supplier<ItemBullet> replacementBullet;
    
    public Ammo(Properties properties)
    {
        super(properties);
        
        this.replacementBullet = () -> null;
    }
    
    protected Ammo()
    {
        this(new Properties());
    }
    
    public ItemBullet getUsedBullet(ItemGun gun)
    {
        return this.replacementBullet.get() == null ? gun.getBaseBullet() : this.getReplacementBullet();
    }
    
    @Override
    public boolean shouldRender()
    {
        return false;
    }
    
    @Override
    public EnumAttachmentType getType()
    {
        return EnumAttachmentType.AMMO;
    }
    
    public ItemBullet getReplacementBullet()
    {
        return this.replacementBullet.get();
    }
    
    public Ammo setReplacementBullet(Supplier<ItemBullet> replacementBullet)
    {
        this.replacementBullet = replacementBullet;
        return this;
    }
}
