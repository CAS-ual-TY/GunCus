package de.cas_ual_ty.gci.network;

import de.cas_ual_ty.gci.ContainerGunTable;
import de.cas_ual_ty.gci.client.GuiGunTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandlerGCI implements IGuiHandler
{
	@Override
	public Object getClientGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z)
	{
		switch(id)
		{
			case 0: return new GuiGunTable(new ContainerGunTable(entityPlayer, world, new BlockPos(x, y, z)));
		}
		
		return null;
	}
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z)
	{
		switch(id)
		{
			case 0: return new ContainerGunTable(entityPlayer, world, new BlockPos(x, y, z));
		}
		
		return null;
	}
	
}
