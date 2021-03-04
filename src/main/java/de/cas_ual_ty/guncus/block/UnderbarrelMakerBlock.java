package de.cas_ual_ty.guncus.block;

import de.cas_ual_ty.guncus.GunCus;
import de.cas_ual_ty.guncus.container.MakerContainer;
import de.cas_ual_ty.guncus.container.UnderbarrelMakerContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class UnderbarrelMakerBlock extends MakerBlock
{
    public static final ITextComponent TITLE = new TranslationTextComponent("container." + GunCus.MOD_ID + ".underbarrel_maker");
    
    public UnderbarrelMakerBlock(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public MakerContainer makeContainer(int windowId, PlayerInventory playerInv, BlockPos pos)
    {
        return new UnderbarrelMakerContainer(windowId, playerInv, pos);
    }
    
    @Override
    public ITextComponent getContainerTitle()
    {
        return TITLE;
    }
}
