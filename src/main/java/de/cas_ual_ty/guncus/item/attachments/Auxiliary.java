package de.cas_ual_ty.guncus.item.attachments;

import java.util.ArrayList;
import java.util.List;

import de.cas_ual_ty.guncus.GunCus;
import de.cas_ual_ty.guncus.item.AttachmentItem;
import de.cas_ual_ty.guncus.item.MakerItem;
import net.minecraft.item.ItemStack;

public class Auxiliary extends AttachmentItem
{
    public static final List<MakerItem> MAKER_AUXILIARIES_LIST = new ArrayList<>();
    
    public static final Auxiliary DEFAULT = (Auxiliary)new Auxiliary().setRegistryName(GunCus.MOD_ID, "auxiliary_default");
    
    protected boolean isAllowingReloadWhileZoomed;
    protected int extraFireRate;
    
    public Auxiliary(Properties properties, ItemStack... materials)
    {
        super(properties, materials);
        
        this.isAllowingReloadWhileZoomed = false;
        this.extraFireRate = 0;
        
        if(this.craftAmount > 0 && materials.length > 0)
        {
            MAKER_AUXILIARIES_LIST.add(this);
        }
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
