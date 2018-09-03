package de.cas_ual_ty.gci.block;

import de.cas_ual_ty.gci.GunCus;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockGunTable extends BlockGCI
{
	public BlockGunTable(String rl)
	{
		super(rl, Material.IRON);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		playerIn.openGui(GunCus.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
}
