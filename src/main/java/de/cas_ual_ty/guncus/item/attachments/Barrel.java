package de.cas_ual_ty.guncus.item.attachments;

import java.util.ArrayList;
import java.util.List;

import de.cas_ual_ty.guncus.GunCus;
import de.cas_ual_ty.guncus.item.AttachmentItem;
import de.cas_ual_ty.guncus.item.MakerItem;
import net.minecraft.item.ItemStack;

public class Barrel extends AttachmentItem
{
    public static final List<MakerItem> MAKER_BARRELS_LIST = new ArrayList<>();
    
    public static final Barrel DEFAULT = (Barrel)new Barrel().setRegistryName(GunCus.MOD_ID, "barrel_default");
    
    protected boolean isSilenced;
    protected boolean isFlashHider; //TODO implement
    
    public Barrel(Properties properties, ItemStack... materials)
    {
        super(properties, materials);
        
        this.isSilenced = false;
        this.isFlashHider = false;
        
        if(this.craftAmount > 0 && materials.length > 0)
        {
            MAKER_BARRELS_LIST.add(this);
        }
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
