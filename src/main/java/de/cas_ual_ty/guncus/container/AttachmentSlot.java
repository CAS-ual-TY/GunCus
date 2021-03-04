package de.cas_ual_ty.guncus.container;

import de.cas_ual_ty.guncus.item.AttachmentItem;
import de.cas_ual_ty.guncus.item.GunItem;
import de.cas_ual_ty.guncus.item.attachments.EnumAttachmentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class AttachmentSlot extends Slot
{
    public final GunSlot gunSlot;
    public final EnumAttachmentType type;
    public final PlayerEntity player;
    
    public AttachmentSlot(IInventory inventoryIn, int index, int xPosition, int yPosition, GunSlot gunSlot, EnumAttachmentType type, PlayerEntity player)
    {
        super(inventoryIn, index, xPosition, yPosition);
        this.gunSlot = gunSlot;
        this.type = type;
        this.player = player;
    }
    
    public AttachmentItem getAttachment()
    {
        return this.getHasStack() ? (AttachmentItem)this.getStack().getItem() : this.type.getDefault();
    }
    
    @Override
    public boolean isItemValid(ItemStack stack)
    {
        if(this.gunSlot.getHasStack() && !this.getHasStack() && (stack.getItem() instanceof AttachmentItem))
        {
            GunItem gun = (GunItem)this.gunSlot.getStack().getItem();
            AttachmentItem attachment = (AttachmentItem)stack.getItem();
            
            return (attachment.getType() == this.type) && gun.canSetAttachment(attachment);
        }
        else
        {
            return false;
        }
    }
    
    /* @Override
    public boolean isEnabled()
    {
        if(!(this.gunSlot.getStack().getItem() instanceof ItemGun))
        {
            return false;
        }
        
        ItemGun gun = (ItemGun) this.gunSlot.getStack().getItem();
        
        if(!gun.isSlotAvailable(this.type))
        {
            return false;
        }
        
        ItemStack held = this.player.inventory.getItemStack();
        
        if(held.isEmpty())
        {
            return true;
        }
        
        if(!(held.getItem() instanceof ItemAttachment))
        {
            return false;
        }
        
        ItemAttachment attachment = (ItemAttachment) held.getItem();
        
        return attachment.getType() == this.type;
    } */
    
    @Override
    public int getSlotStackLimit()
    {
        return 1;
    }
}
