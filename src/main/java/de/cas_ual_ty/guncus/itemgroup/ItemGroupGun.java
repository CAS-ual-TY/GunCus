package de.cas_ual_ty.guncus.itemgroup;

import de.cas_ual_ty.guncus.GunCus;
import de.cas_ual_ty.guncus.item.ItemAttachment;
import de.cas_ual_ty.guncus.item.ItemGun;
import de.cas_ual_ty.guncus.item.attachments.Ammo;
import de.cas_ual_ty.guncus.item.attachments.EnumAttachmentType;
import de.cas_ual_ty.guncus.util.GunCusUtility;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;

public class ItemGroupGun extends ItemGroupShuffle
{
    protected final ItemGun gun;
    
    public ItemGroupGun(String label, ItemGun gun)
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
        
        for(ItemAttachment attachment : this.gun.getAttachments()[EnumAttachmentType.AMMO.getSlot()])
        {
            ammo = (Ammo)attachment;
            
            if(!ammo.isDefault() && ammo.getReplacementBullet() != null && ammo.getReplacementBullet() != this.gun.getBaseBullet())
            {
                items.add(new ItemStack(ammo.getReplacementBullet()));
            }
        }
        
        for(ItemAttachment[] attachments : this.gun.getAttachments())
        {
            for(ItemAttachment attachment : attachments)
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
