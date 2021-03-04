package de.cas_ual_ty.guncus.container;

import java.util.List;

import de.cas_ual_ty.guncus.item.MakerItem;
import de.cas_ual_ty.guncus.item.attachments.Barrel;
import de.cas_ual_ty.guncus.registries.GunCusBlocks;
import de.cas_ual_ty.guncus.registries.GunCusContainerTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

public class BarrelMakerContainer extends MakerContainer
{
    public BarrelMakerContainer(int windowId, PlayerInventory playerInv, BlockPos pos)
    {
        super(GunCusContainerTypes.BARREL_MAKER, windowId, playerInv, pos);
    }
    
    public BarrelMakerContainer(int windowId, PlayerInventory playerInv, PacketBuffer extraData)
    {
        super(GunCusContainerTypes.BARREL_MAKER, windowId, playerInv, extraData.readBlockPos());
    }
    
    @Override
    public List<MakerItem> getItemsList()
    {
        return Barrel.BARRELS_LIST;
    }
    
    @Override
    public boolean canInteractWith(PlayerEntity playerIn)
    {
        return this.pos == null || Container.isWithinUsableDistance(this.worldPosCallable, playerIn, GunCusBlocks.BARREL_MAKER);
    }
}
