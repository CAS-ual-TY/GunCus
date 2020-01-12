package de.cas_ual_ty.guncus.container;

import de.cas_ual_ty.guncus.item.ItemGun;
import de.cas_ual_ty.guncus.item.attachments.EnumAttachmentType;
import de.cas_ual_ty.guncus.registries.GunCusBlocks;
import de.cas_ual_ty.guncus.registries.GunCusContainerTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;

public class ContainerGunTable extends Container
{
    public final CraftingInventory craftMatrix;
    public final SlotGun gunSlot;
    
    public final PlayerEntity player;
    
    public final SlotAttachment[] attachmentSlots;
    
    protected BlockPos pos;
    protected IWorldPosCallable worldPosCallable;
    
    public ContainerGunTable(int windowId, PlayerInventory playerInv)
    {
        super(GunCusContainerTypes.GUN_TABLE, windowId);
        
        this.pos = null;
        
        this.craftMatrix = new CraftingInventory(this, 3, 3);
        this.attachmentSlots = new SlotAttachment[EnumAttachmentType.LENGTH];
        
        this.player = playerInv.player;
        
        final int attachmentsX = 62;
        final int attachmentsY = 17;
        
        final int gunSlotX = 1;
        final int gunSlotY = 1;
        this.addSlot(this.gunSlot = new SlotGun(this.craftMatrix, EnumAttachmentType.LENGTH, attachmentsX + gunSlotX * 18, attachmentsY + gunSlotY * 18, this.player));
        
        for(EnumAttachmentType type : EnumAttachmentType.VALUES)
        {
            this.addSlot(this.attachmentSlots[type.getSlot()] = new SlotAttachment(this.craftMatrix, type.getSlot(), attachmentsX + type.getX() * 18, attachmentsY + type.getY() * 18, this.gunSlot, type, this.player));
        }
        
        for(int y = 0; y < 3; ++y)
        {
            for(int x = 0; x < 9; ++x)
            {
                this.addSlot(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }
        
        for(int x = 0; x < 9; ++x)
        {
            this.addSlot(new Slot(playerInv, x, 8 + x * 18, 142));
        }
    }
    
    public ContainerGunTable(int windowId, PlayerInventory playerInv, BlockPos pos)
    {
        this(windowId, playerInv);
        this.pos = pos;
        this.worldPosCallable = IWorldPosCallable.of(this.player.world, this.pos);
    }
    
    public ContainerGunTable(int windowId, PlayerInventory playerInv, PacketBuffer extraData)
    {
        this(windowId, playerInv, extraData.readBlockPos());
    }
    
    @Override
    public boolean canInteractWith(PlayerEntity playerIn)
    {
        return this.pos == null || Container.isWithinUsableDistance(this.worldPosCallable, playerIn, GunCusBlocks.GUN_TABLE);
    }
    
    private boolean wasGunIn = false;
    private boolean wasChanging = false;
    
    @Override
    public void onCraftMatrixChanged(IInventory inventoryIn)
    {
        if(inventoryIn == this.craftMatrix)
        {
            if(this.wasChanging)
            {
                return;
            }
            
            this.wasChanging = true;
            
            if(this.gunSlot.getHasStack())
            {
                if(!this.wasGunIn)
                {
                    ItemGun gun = (ItemGun)this.gunSlot.getStack().getItem();
                    
                    for(SlotAttachment slot : this.attachmentSlots)
                    {
                        slot.putStack(gun.getAttachmentItemStack(this.gunSlot.getStack(), slot.type));
                    }
                }
                else
                {
                    ItemGun gun = (ItemGun)this.gunSlot.getStack().getItem();
                    
                    for(SlotAttachment slot : this.attachmentSlots)
                    {
                        gun.setAttachment(this.gunSlot.getStack(), slot.getAttachment());
                    }
                }
            }
            else
            {
                for(SlotAttachment slot : this.attachmentSlots)
                {
                    slot.putStack(ItemStack.EMPTY);
                }
            }
            
            this.wasChanging = false;
        }
        
        this.wasGunIn = this.gunSlot.getHasStack();
        
        super.onCraftMatrixChanged(inventoryIn);
    }
    
    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index)
    {
        /* TODO fix transferStackInSlot: You can shift click more than 1 attachment right now into the slots.
        Slot slot = this.getSlot(index);
        ItemStack itemStack = slot.getStack();
        
        if(index >= 9)
        {
            if(itemStack.getItem() instanceof ItemGun)
            {
                if(this.gunSlot.isItemValid(itemStack))
                {
                    this.gunSlot.putStack(itemStack);
                    slot.putStack(ItemStack.EMPTY);
                }
            }
            else if(itemStack.getItem() instanceof ItemAttachment)
            {
                ItemAttachment attachment = (ItemAttachment)itemStack.getItem();
                
                if(this.attachmentSlots[attachment.getSlot()].isItemValid(itemStack))
                {
                    this.attachmentSlots[attachment.getSlot()].putStack(itemStack);
                    slot.putStack(ItemStack.EMPTY);
                }
            }
        }
        else
        {
            if(playerIn.inventory.addItemStackToInventory(itemStack))
            {
                slot.putStack(ItemStack.EMPTY);
            }
        }
        */
        
        return ItemStack.EMPTY;
    }
    
    @Override
    public void onContainerClosed(PlayerEntity playerIn)
    {
        if(this.gunSlot.getHasStack())
        {
            playerIn.dropItem(this.gunSlot.getStack(), false);
        }
        
        super.onContainerClosed(playerIn);
    }
}
