package de.cas_ual_ty.guncus.container;

import java.util.ArrayList;
import java.util.List;

import de.cas_ual_ty.guncus.item.MakerItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;

public abstract class MakerContainer extends Container
{
    public final PlayerEntity player;
    protected BlockPos pos;
    protected IWorldPosCallable worldPosCallable;
    
    private int selectedItemIndex;
    
    public final Inventory inventory;
    
    // Shown items (cant take them)
    public final ArrayList<Slot> materialsSlots;
    public final Slot selectedItemSlot;
    
    public MakerContainer(ContainerType<?> type, int windowId, PlayerInventory playerInv, BlockPos pos)
    {
        super(type, windowId);
        
        this.player = playerInv.player;
        this.pos = pos;
        this.worldPosCallable = IWorldPosCallable.of(this.player.world, this.pos);
        
        this.inventory = new Inventory(10);
        
        this.materialsSlots = new ArrayList<>(9);
        
        for(int y = 0; y < 3; ++y)
        {
            for(int x = 0; x < 3; ++x)
            {
                this.materialsSlots.add(new Slot(this.inventory, x + y * 3, 30 + 18 * x, 17 + 18 * y)
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
            }
        }
        
        this.selectedItemSlot = new Slot(this.inventory, 9, 124, 35)
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
        };
        
        for(Slot s : this.materialsSlots)
        {
            this.addSlot(s);
        }
        this.addSlot(this.selectedItemSlot);
        
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
        
        this.selectedItemIndex = -1;
        this.nextItem();
    }
    
    public MakerContainer(ContainerType<?> type, int windowId, PlayerInventory playerInv, PacketBuffer extraData)
    {
        this(type, windowId, playerInv, extraData.readBlockPos());
    }
    
    public abstract List<MakerItem> getItemsList();
    
    public void nextItem()
    {
        if(this.getItemsList().isEmpty())
        {
            return;
        }
        
        this.selectedItemIndex++;
        
        if(this.selectedItemIndex >= this.getItemsList().size())
        {
            this.selectedItemIndex = 0;
        }
        
        MakerItem item = this.getItemsList().get(this.selectedItemIndex);
        this.selectedItemSlot.putStack(new ItemStack(item));
        
        this.populate();
    }
    
    public void prevItem()
    {
        if(this.getItemsList().isEmpty())
        {
            return;
        }
        
        this.selectedItemIndex--;
        
        if(this.selectedItemIndex < 0)
        {
            this.selectedItemIndex = this.getItemsList().size() - 1;
        }
        
        MakerItem item = this.getItemsList().get(this.selectedItemIndex);
        this.selectedItemSlot.putStack(new ItemStack(item));
        
        this.populate();
    }
    
    public void populate()
    {
        if(!this.selectedItemSlot.getHasStack() || !(this.selectedItemSlot.getStack().getItem() instanceof MakerItem))
        {
            return;
        }
        
        MakerItem item = (MakerItem)this.selectedItemSlot.getStack().getItem();
        List<ItemStack> materials = item.getMakerMaterials();
        
        this.selectedItemSlot.putStack(new ItemStack(item));
        
        for(Slot s : this.materialsSlots)
        {
            s.putStack(ItemStack.EMPTY);
        }
        
        int index;
        
        for(int y = 0; y < 3; ++y)
        {
            for(int x = 0; x < 3; ++x)
            {
                index = x + y * 3;
                
                if(index >= this.materialsSlots.size() || index >= materials.size())
                {
                    break;
                }
                
                this.materialsSlots.get(index).putStack(materials.get(index));
            }
        }
        
        this.detectAndSendChanges();
    }
    
    public void create()
    {
        if(!this.selectedItemSlot.getHasStack() || !(this.selectedItemSlot.getStack().getItem() instanceof MakerItem))
        {
            return;
        }
        
        MakerItem item = (MakerItem)this.selectedItemSlot.getStack().getItem();
        List<ItemStack> materials = item.getMakerMaterials();
        
        if(!this.player.isCreative())
        {
            for(ItemStack material : materials)
            {
                if(!this.playerHasMaterial(material, false))
                {
                    return;
                }
            }
            
            for(ItemStack material : materials)
            {
                if(!this.playerHasMaterial(material, true))
                {
                    return;
                }
            }
        }
        
        this.player.dropItem(new ItemStack(item, item.getMakerOutputAmount()), false);
    }
    
    public boolean playerHasMaterial(ItemStack material, boolean consume)
    {
        PlayerInventory playerInv = this.player.inventory;
        
        for(int i = 0; i < playerInv.getSizeInventory(); ++i)
        {
            ItemStack itemStack = playerInv.getStackInSlot(i);
            
            if(this.itemFitsMaterial(itemStack, material))
            {
                if(consume)
                {
                    itemStack.shrink(material.getCount());
                }
                return true;
            }
        }
        
        return false;
    }
    
    protected boolean itemFitsMaterial(ItemStack itemStack, ItemStack material)
    {
        return itemStack.getItem() == material.getItem() && itemStack.getCount() >= material.getCount();
    }
    
    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index)
    {
        return ItemStack.EMPTY;
    }
    
    @Override
    public void onContainerClosed(PlayerEntity playerIn)
    {
        super.onContainerClosed(playerIn);
    }
}
