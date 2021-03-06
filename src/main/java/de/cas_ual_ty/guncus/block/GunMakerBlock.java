package de.cas_ual_ty.guncus.block;

import de.cas_ual_ty.guncus.GunCus;
import de.cas_ual_ty.guncus.container.GunMakerContainer;
import de.cas_ual_ty.guncus.container.MakerContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class GunMakerBlock extends MakerBlock
{
    public static final ITextComponent TITLE = new TranslationTextComponent("container." + GunCus.MOD_ID + ".gun_maker");
    
    public GunMakerBlock(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public MakerContainer makeContainer(int windowId, PlayerInventory playerInv, BlockPos pos)
    {
        return new GunMakerContainer(windowId, playerInv, pos);
    }
    
    @Override
    public ITextComponent getContainerTitle()
    {
        return GunMakerBlock.TITLE;
    }
}
