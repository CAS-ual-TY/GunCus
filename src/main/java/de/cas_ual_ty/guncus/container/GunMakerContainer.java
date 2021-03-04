package de.cas_ual_ty.guncus.container;

import java.util.List;

import de.cas_ual_ty.guncus.item.GunItem;
import de.cas_ual_ty.guncus.item.MakerItem;
import de.cas_ual_ty.guncus.registries.GunCusBlocks;
import de.cas_ual_ty.guncus.registries.GunCusContainerTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

public class GunMakerContainer extends MakerContainer
{
    public GunMakerContainer(int windowId, PlayerInventory playerInv, BlockPos pos)
    {
        super(GunCusContainerTypes.GUN_MAKER, windowId, playerInv, pos);
    }
    
    public GunMakerContainer(int windowId, PlayerInventory playerInv, PacketBuffer extraData)
    {
        super(GunCusContainerTypes.GUN_MAKER, windowId, playerInv, extraData.readBlockPos());
    }
    
    @Override
    public List<MakerItem> getItemsList()
    {
        return GunItem.MAKER_GUNS_LIST;
    }
    
    @Override
    public boolean canInteractWith(PlayerEntity playerIn)
    {
        return this.pos == null || Container.isWithinUsableDistance(this.worldPosCallable, playerIn, GunCusBlocks.GUN_MAKER);
    }
}
