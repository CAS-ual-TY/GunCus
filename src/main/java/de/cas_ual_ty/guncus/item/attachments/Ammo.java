package de.cas_ual_ty.guncus.item.attachments;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import de.cas_ual_ty.guncus.GunCus;
import de.cas_ual_ty.guncus.item.AttachmentItem;
import de.cas_ual_ty.guncus.item.BulletItem;
import de.cas_ual_ty.guncus.item.GunItem;
import de.cas_ual_ty.guncus.item.MakerItem;
import net.minecraft.item.ItemStack;

public class Ammo extends AttachmentItem
{
    public static final List<MakerItem> MAKER_AMMOS_LIST = new ArrayList<>();
    
    public static final Ammo DEFAULT = (Ammo)new Ammo().setRegistryName(GunCus.MOD_ID, "ammo_default");
    
    protected Supplier<BulletItem> replacementBullet;
    
    public Ammo(Properties properties, ItemStack... materials)
    {
        super(properties);
        
        this.replacementBullet = () -> null;
        
        if(this.craftAmount > 0 && materials.length > 0)
        {
            Ammo.MAKER_AMMOS_LIST.add(this);
        }
    }
    
    protected Ammo()
    {
        this(new Properties());
    }
    
    public BulletItem getUsedBullet(GunItem gun)
    {
        return this.replacementBullet.get() == null ? gun.getBaseBullet() : this.getReplacementBullet();
    }
    
    @Override
    public EnumAttachmentType getType()
    {
        return EnumAttachmentType.AMMO;
    }
    
    public BulletItem getReplacementBullet()
    {
        return this.replacementBullet.get();
    }
    
    public Ammo setReplacementBullet(Supplier<BulletItem> replacementBullet)
    {
        this.replacementBullet = replacementBullet;
        return this;
    }
}
