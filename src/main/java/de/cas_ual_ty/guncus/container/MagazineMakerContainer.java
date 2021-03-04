package de.cas_ual_ty.guncus.container;

import java.util.List;

import de.cas_ual_ty.guncus.item.MakerItem;
import de.cas_ual_ty.guncus.item.attachments.Magazine;
import de.cas_ual_ty.guncus.registries.GunCusBlocks;
import de.cas_ual_ty.guncus.registries.GunCusContainerTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

public class MagazineMakerContainer extends MakerContainer
{
    public MagazineMakerContainer(int windowId, PlayerInventory playerInv, BlockPos pos)
    {
        super(GunCusContainerTypes.MAGAZINE_MAKER, windowId, playerInv, pos);
    }
    
    public MagazineMakerContainer(int windowId, PlayerInventory playerInv, PacketBuffer extraData)
    {
        super(GunCusContainerTypes.MAGAZINE_MAKER, windowId, playerInv, extraData.readBlockPos());
    }
    
    @Override
    public List<MakerItem> getItemsList()
    {
        return Magazine.MAKER_MAGAZINES_LIST;
    }
    
    @Override
    public boolean canInteractWith(PlayerEntity playerIn)
    {
        return this.pos == null || Container.isWithinUsableDistance(this.worldPosCallable, playerIn, GunCusBlocks.MAGAZINE_MAKER);
    }
}
