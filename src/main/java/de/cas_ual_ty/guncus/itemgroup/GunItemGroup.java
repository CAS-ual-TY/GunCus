package de.cas_ual_ty.guncus.itemgroup;

import java.util.List;

import de.cas_ual_ty.guncus.GunCus;
import de.cas_ual_ty.guncus.item.AttachmentItem;
import de.cas_ual_ty.guncus.item.GunItem;
import de.cas_ual_ty.guncus.item.attachments.Ammo;
import de.cas_ual_ty.guncus.item.attachments.EnumAttachmentType;
import de.cas_ual_ty.guncus.util.GunCusUtility;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;

public class GunItemGroup extends ShuffleItemGroup
{
    protected final GunItem gun;
    
    protected List<ItemStack> allVariants;
    
    public GunItemGroup(String label, GunItem gun)
    {
        super(label);
        this.gun = gun;
        this.icon = new ItemStack(this.gun);
        this.allVariants = null;
    }
    
    @Override
    public void fill(NonNullList<ItemStack> items)
    {
        super.fill(items);
        
        items.add(new ItemStack(this.gun));
        items.add(new ItemStack(this.gun.getBaseBullet()));
        
        Ammo ammo;
        
        for(AttachmentItem attachment : this.gun.getAttachments()[EnumAttachmentType.AMMO.getSlot()])
        {
            ammo = (Ammo)attachment;
            
            if(!ammo.isDefault() && ammo.getReplacementBullet() != null && ammo.getReplacementBullet() != this.gun.getBaseBullet())
            {
                items.add(new ItemStack(ammo.getReplacementBullet()));
            }
        }
        
        for(AttachmentItem[] attachments : this.gun.getAttachments())
        {
            for(AttachmentItem attachment : attachments)
            {
                if(!attachment.isDefault())
                {
                    items.add(new ItemStack(attachment));
                }
            }
        }
        
        if(GunCus.FULL_CREATIVE_TABS && this.allVariants != null)
        {
            GunCusUtility.addAllToNonNullList(items, this.allVariants);
        }
    }
    
    @Override
    public void fillShuffleList(NonNullList<ItemStack> list)
    {
        if(this.allVariants != null)
        {
            GunCusUtility.addAllToNonNullList(list, this.allVariants);
        }
    }
    
    @Override
    public ITextComponent getGroupName()
    {
        return this.gun.getName();
    }
    
    public void init()
    {
        this.allVariants = GunCusUtility.createAllVariants(this.gun);
    }
}
