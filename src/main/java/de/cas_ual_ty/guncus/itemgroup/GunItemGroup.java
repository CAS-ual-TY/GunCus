package de.cas_ual_ty.guncus.itemgroup;

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
    
    public GunItemGroup(String label, GunItem gun)
    {
        super(label);
        this.gun = gun;
        this.icon = new ItemStack(this.gun);
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
        
        if(GunCus.FULL_CREATIVE_TABS)
        {
            items.addAll(GunCusUtility.createAllVariants(this.gun));
        }
    }
    
    @Override
    public ITextComponent getGroupName()
    {
        return this.gun.getName();
    }
    
    @Override
    public ItemStack shuffle()
    {
        return GunCus.FULL_CREATIVE_TABS ? super.shuffle() : this.icon;
    }
}
