package de.cas_ual_ty.guncus.item.attachments;

import java.util.ArrayList;
import java.util.List;

import de.cas_ual_ty.guncus.GunCus;
import de.cas_ual_ty.guncus.item.AttachmentItem;
import de.cas_ual_ty.guncus.item.MakerItem;
import net.minecraft.item.ItemStack;

public class Paint extends AttachmentItem
{
    public static final List<MakerItem> PAINTS_LIST = new ArrayList<>();
    
    public static final Paint DEFAULT = (Paint)new Paint().setRegistryName(GunCus.MOD_ID, "paint_default");
    
    public Paint(Properties properties, ItemStack... materials)
    {
        super(properties);
        
        if(this.craftAmount > 0 && materials.length > 0)
        {
            PAINTS_LIST.add(this);
        }
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
}
