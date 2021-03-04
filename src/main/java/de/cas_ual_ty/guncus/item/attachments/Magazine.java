package de.cas_ual_ty.guncus.item.attachments;

import java.util.ArrayList;
import java.util.List;

import de.cas_ual_ty.guncus.GunCus;
import de.cas_ual_ty.guncus.item.AttachmentItem;
import de.cas_ual_ty.guncus.item.MakerItem;
import net.minecraft.item.ItemStack;

public class Magazine extends AttachmentItem
{
    public static final List<MakerItem> MAGAZINES_LIST = new ArrayList<>();
    
    public static final Magazine DEFAULT = (Magazine)new Magazine().setRegistryName(GunCus.MOD_ID, "magazine_default");
    
    protected int extraCapacity;
    protected float reloadTimeModifier;
    
    public Magazine(Properties properties, ItemStack... materials)
    {
        super(properties, materials);
        
        this.extraCapacity = 0;
        this.reloadTimeModifier = 1F;
        
        if(this.craftAmount > 0 && materials.length > 0)
        {
            MAGAZINES_LIST.add(this);
        }
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
