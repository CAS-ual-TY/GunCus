package de.cas_ual_ty.guncus.container;

import de.cas_ual_ty.guncus.item.ItemAttachment;
import de.cas_ual_ty.guncus.item.ItemGun;
import de.cas_ual_ty.guncus.item.attachments.EnumAttachmentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class SlotAttachment extends Slot
{
    public final SlotGun gunSlot;
    public final EnumAttachmentType type;
    public final PlayerEntity player;
    
    public SlotAttachment(IInventory inventoryIn,int index,int xPosition,int yPosition,SlotGun gunSlot,EnumAttachmentType type,PlayerEntity player)
    {
        super(inventoryIn, index, xPosition, yPosition);
        this.gunSlot = gunSlot;
        this.type = type;
        this.player = player;
    }
    
    public ItemAttachment getAttachment()
    {
        return this.getHasStack() ? (ItemAttachment)this.getStack().getItem() : this.type.getDefault();
    }
    
    @Override
    public boolean isItemValid(ItemStack stack)
    {
        if(this.gunSlot.getHasStack() && !this.getHasStack() && (stack.getItem() instanceof ItemAttachment))
        {
            ItemGun gun = (ItemGun)this.gunSlot.getStack().getItem();
            ItemAttachment attachment = (ItemAttachment)stack.getItem();
            
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
