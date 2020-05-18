package de.cas_ual_ty.guncus.block;

import de.cas_ual_ty.guncus.GunCus;
import de.cas_ual_ty.guncus.container.ContainerGunMaker;
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

public class BlockGunMaker extends Block
{
    public static final ITextComponent TITLE_GUN_MAKER = new TranslationTextComponent("container." + GunCus.MOD_ID + ".gun_maker");
    
    public BlockGunMaker(Properties properties)
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
            return new ContainerGunMaker(windowId, inv, pos);
        }, BlockGunMaker.TITLE_GUN_MAKER);
    }
}
