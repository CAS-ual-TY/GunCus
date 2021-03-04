package de.cas_ual_ty.guncus.block;

import de.cas_ual_ty.guncus.GunCus;
import de.cas_ual_ty.guncus.container.GunTableContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class GunTableBlock extends Block
{
    public static final ITextComponent TITLE_GUN_TABLE = new TranslationTextComponent("container." + GunCus.MOD_ID + ".gun_table");
    
    public GunTableBlock(Properties properties)
    {
        super(properties);
    }
    
    @Override //onBlockActivated
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        player.openContainer(state.getContainer(worldIn, pos));
        /*if (!worldIn.isRemote)
        {
            NetworkHooks.openGui((ServerPlayerEntity) player, this.getContainer(state, worldIn, pos), pos);
        }*/
        return ActionResultType.SUCCESS;
    }
    
    @Override
    public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos)
    {
        return new SimpleNamedContainerProvider((windowId, inv, player) ->
        {
            return new GunTableContainer(windowId, inv, pos);
        }, GunTableBlock.TITLE_GUN_TABLE);
    }
}
