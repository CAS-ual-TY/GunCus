package de.cas_ual_ty.guncus.container;

import java.util.List;

import de.cas_ual_ty.guncus.item.MakerItem;
import de.cas_ual_ty.guncus.item.attachments.Optic;
import de.cas_ual_ty.guncus.registries.GunCusBlocks;
import de.cas_ual_ty.guncus.registries.GunCusContainerTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

public class OpticMakerContainer extends MakerContainer
{
    public OpticMakerContainer(int windowId, PlayerInventory playerInv, BlockPos pos)
    {
        super(GunCusContainerTypes.OPTIC_MAKER, windowId, playerInv, pos);
    }
    
    public OpticMakerContainer(int windowId, PlayerInventory playerInv, PacketBuffer extraData)
    {
        super(GunCusContainerTypes.OPTIC_MAKER, windowId, playerInv, extraData.readBlockPos());
    }
    
    @Override
    public List<MakerItem> getItemsList()
    {
        return Optic.OPTICS_LIST;
    }
    
    @Override
    public boolean canInteractWith(PlayerEntity playerIn)
    {
        return this.pos == null || Container.isWithinUsableDistance(this.worldPosCallable, playerIn, GunCusBlocks.OPTIC_MAKER);
    }
}
