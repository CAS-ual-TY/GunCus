package de.cas_ual_ty.guncus.container;

import de.cas_ual_ty.guncus.item.GunItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class GunSlot extends Slot
{
    public final PlayerEntity player;
    
    public GunSlot(IInventory inventoryIn, int index, int xPosition, int yPosition, PlayerEntity player)
    {
        super(inventoryIn, index, xPosition, yPosition);
        this.player = player;
    }
    
    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return stack.getItem() instanceof GunItem && ((GunItem)stack.getItem()).getNBTCurrentAmmo(stack) <= 0;
    }
    
    @Override
    public int getSlotStackLimit()
    {
        return 1;
    }
    
    /* @Override
    public boolean isEnabled()
    {
        return this.player.inventory.getItemStack().isEmpty() || (this.player.inventory.getItemStack().getItem() instanceof ItemGun);
    } */
}
