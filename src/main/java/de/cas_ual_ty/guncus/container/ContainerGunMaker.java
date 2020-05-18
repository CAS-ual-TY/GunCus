package de.cas_ual_ty.guncus.container;

import java.util.ArrayList;

import de.cas_ual_ty.guncus.item.ItemGun;
import de.cas_ual_ty.guncus.registries.GunCusBlocks;
import de.cas_ual_ty.guncus.registries.GunCusContainerTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerGunMaker extends Container
{
    public final Inventory inventory;
    public final CraftingInventory craftMatrix;
    
    public final Slot ironSlot;
    public final Slot goldSlot;
    public final Slot redstoneSlot;
    public final Slot ironSlotS; //shown
    public final Slot goldSlotS; //shown
    public final Slot redstoneSlotS; //shown
    
    public final ArrayList<Slot> slots;
    
    public final Slot gunSlot;
    public final Slot gunSlotS; //shown
    
    public final PlayerEntity player;
    
    protected BlockPos pos;
    protected IWorldPosCallable worldPosCallable;
    
    public ContainerGunMaker(int windowId, PlayerInventory playerInv)
    {
        super(GunCusContainerTypes.GUN_MAKER, windowId);
        
        this.pos = null;
        
        this.inventory = new Inventory(4);
        this.craftMatrix = new CraftingInventory(this, 4, 1);
        
        this.player = playerInv.player;
        
        final int slotX1 = 56;
        final int slotX2 = 116;
        final int slotY1 = 48;
        final int slotY2 = 22;
        
        this.slots = new ArrayList<>(8);
        
        this.slots.add(this.ironSlot = new Slot(this.craftMatrix, 0, slotX1 - 18, slotY1)
        {
            @Override
            public boolean isItemValid(ItemStack stack)
            {
                return stack.getItem() == Items.IRON_INGOT;
            }
        });
        
        this.slots.add(this.goldSlot = new Slot(this.craftMatrix, 1, slotX1, slotY1)
        {
            @Override
            public boolean isItemValid(ItemStack stack)
            {
                return stack.getItem() == Items.GOLD_INGOT;
            }
        });
        
        this.slots.add(this.redstoneSlot = new Slot(this.craftMatrix, 2, slotX1 + 18, slotY1)
        {
            @Override
            public boolean isItemValid(ItemStack stack)
            {
                return stack.getItem() == Items.REDSTONE;
            }
        });
        
        this.slots.add(this.ironSlotS = new Slot(this.inventory, 0, slotX1 - 18, slotY2)
        {
            @Override
            public boolean isItemValid(ItemStack stack)
            {
                return false;
            }
            
            @Override
            public boolean canTakeStack(PlayerEntity playerIn)
            {
                return false;
            }
        });
        
        this.slots.add(this.goldSlotS = new Slot(this.inventory, 1, slotX1, slotY2)
        {
            @Override
            public boolean isItemValid(ItemStack stack)
            {
                return false;
            }
            
            @Override
            public boolean canTakeStack(PlayerEntity playerIn)
            {
                return false;
            }
        });
        
        this.slots.add(this.redstoneSlotS = new Slot(this.inventory, 2, slotX1 + 18, slotY2)
        {
            @Override
            public boolean isItemValid(ItemStack stack)
            {
                return false;
            }
            
            @Override
            public boolean canTakeStack(PlayerEntity playerIn)
            {
                return false;
            }
        });
        
        this.slots.add(this.gunSlot = new Slot(this.craftMatrix, 3, slotX2, slotY1)
        {
            @Override
            public boolean isItemValid(ItemStack stack)
            {
                return false;
            }
        });
        
        this.slots.add(this.gunSlotS = new Slot(this.inventory, 3, slotX2, slotY2)
        {
            @Override
            public boolean isItemValid(ItemStack stack)
            {
                return false;
            }
            
            @Override
            public boolean canTakeStack(PlayerEntity playerIn)
            {
                return false;
            }
        });
        
        for(Slot s : this.slots)
        {
            this.addSlot(s);
        }
        
        for(int y1 = 0; y1 < 3; ++y1)
        {
            for(int x1 = 0; x1 < 9; ++x1)
            {
                this.addSlot(new Slot(playerInv, x1 + y1 * 9 + 9, 8 + x1 * 18, 84 + y1 * 18));
            }
        }
        
        for(int x1 = 0; x1 < 9; ++x1)
        {
            this.addSlot(new Slot(playerInv, x1, 8 + x1 * 18, 142));
        }
        
        this.setGun(ItemGun.GUNS_LIST.get(0));
    }
    
    public ContainerGunMaker(int windowId, PlayerInventory playerInv, BlockPos pos)
    {
        this(windowId, playerInv);
        this.pos = pos;
        this.worldPosCallable = IWorldPosCallable.of(this.player.world, this.pos);
    }
    
    public ContainerGunMaker(int windowId, PlayerInventory playerInv, PacketBuffer extraData)
    {
        this(windowId, playerInv, extraData.readBlockPos());
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
            
            Slot slotS;
            Slot slotI;
            
            if(this.wasGunIn && !this.gunSlot.getHasStack())
            {
                for(int i = 3; i < 6; ++i)
                {
                    slotS = this.slots.get(i);
                    slotI = this.slots.get(i - 3);
                    
                    slotI.getStack().shrink(slotS.getStack().getCount());
                }
            }
            
            boolean full = true;
            
            for(int i = 3; i < 6; ++i)
            {
                slotS = this.slots.get(i);
                slotI = this.slots.get(i - 3);
                
                if(slotS.getStack().getCount() > slotI.getStack().getCount())
                {
                    full = false;
                    break;
                }
            }
            
            this.wasGunIn = full;
            
            if(full)
            {
                this.gunSlot.putStack(new ItemStack(this.gunSlotS.getStack().getItem()));
            }
            else
            {
                this.gunSlot.putStack(ItemStack.EMPTY);
            }
            
            this.wasChanging = false;
        }
        
        super.onCraftMatrixChanged(inventoryIn);
    }
    
    public void setGun(ItemGun gun)
    {
        this.gunSlotS.putStack(new ItemStack(gun));
        this.ironSlotS.putStack(new ItemStack(Items.IRON_INGOT, gun.ironAmt));
        this.goldSlotS.putStack(new ItemStack(Items.GOLD_INGOT, gun.goldAmt));
        this.redstoneSlotS.putStack(new ItemStack(Items.REDSTONE, gun.redstoneAmt));
        
        this.wasGunIn = false;
        this.onCraftMatrixChanged(this.craftMatrix);
    }
    
    public void setGun(String modid, String name)
    {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, name));
        if(item instanceof ItemGun)
        {
            this.setGun((ItemGun)item);
        }
    }
    
    @Override
    public boolean canInteractWith(PlayerEntity playerIn)
    {
        return this.pos == null || Container.isWithinUsableDistance(this.worldPosCallable, playerIn, GunCusBlocks.GUN_MAKER);
    }
    
    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index)
    {
        Slot slot = this.getSlot(index);
        ItemStack itemStack = slot.getStack();
        
        if(index >= 8)
        {
            Slot ingredientSlot = null;
            
            if(itemStack.getItem() == Items.IRON_INGOT)
            {
                ingredientSlot = this.ironSlot;
            }
            else if(itemStack.getItem() == Items.GOLD_INGOT)
            {
                ingredientSlot = this.goldSlot;
            }
            else if(itemStack.getItem() == Items.REDSTONE)
            {
                ingredientSlot = this.redstoneSlot;
            }
            
            if(ingredientSlot != null)
            {
                // 15 10 => 25  0 => 10 => MIN(15+10,64) - 15 = 10
                // 10 15 => 25  0 => 15 => MIN(10+15,64) - 10 = 15
                //  5  5 => 10  0 =>  5 => MIN(05+05,64) -  5 =  5
                // 32 32 => 64  0 => 32 => MIN(32+32,64) - 32 = 32
                // 62 63 => 64 61 =>  2 => MIN(62+63,64) - 62 =  2
                int alreadyIn = ingredientSlot.getStack().getCount();
                int available = slot.getStack().getCount();
                
                int toTransfer = Math.min(alreadyIn + available, 64) - alreadyIn;
                
                if(ingredientSlot.getHasStack())
                {
                    ingredientSlot.getStack().grow(toTransfer);
                    slot.getStack().shrink(toTransfer);
                }
                else
                {
                    ingredientSlot.putStack(itemStack);
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
        
        return ItemStack.EMPTY;
    }
    
    @Override
    public void onContainerClosed(PlayerEntity playerIn)
    {
        if(this.ironSlot.getHasStack())
        {
            playerIn.dropItem(this.ironSlot.getStack(), false);
        }
        if(this.goldSlot.getHasStack())
        {
            playerIn.dropItem(this.goldSlot.getStack(), false);
        }
        if(this.redstoneSlot.getHasStack())
        {
            playerIn.dropItem(this.redstoneSlot.getStack(), false);
        }
        
        super.onContainerClosed(playerIn);
    }
}
