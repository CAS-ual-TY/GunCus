package de.cas_ual_ty.gci;

import de.cas_ual_ty.gci.item.ItemGCI;
import de.cas_ual_ty.gci.item.ItemGun;
import de.cas_ual_ty.gci.network.MessageRecoil;
import de.cas_ual_ty.gci.network.MessageShoot;
import de.cas_ual_ty.gci.network.MessageSound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.IForgeRegistry;

public class Proxy
{
	public void registerItem(IForgeRegistry<Item> registry, ItemGCI item)
	{
		registry.register(item);
	}
	
	public void registerGun(IForgeRegistry<Item> registry, ItemGun gun)
	{
		registry.register(gun);
	}
	
	public void preInit(FMLPreInitializationEvent event)
	{
		
	}
	
	public void init(FMLInitializationEvent event)
	{
		GunCus.channel = NetworkRegistry.INSTANCE.newSimpleChannel(GunCus.MOD_ID);
		GunCus.channel.registerMessage(MessageShoot.MessageHandlerShoot.class, MessageShoot.class, 0, Side.SERVER);
		GunCus.channel.registerMessage(MessageSound.MessageHandlerSound.class, MessageSound.class, 1, Side.CLIENT);
		GunCus.channel.registerMessage(MessageRecoil.MessageHandlerRecoil.class, MessageRecoil.class, 2, Side.CLIENT);
		
		EntityRegistry.registerModEntity(new ResourceLocation(GunCus.MOD_ID, "bullet"), EntityBullet.class, "bullet", 5000, GunCus.instance, 500, 1, true);
	}
	
	public EntityPlayer getServerPlayer(MessageContext ctx)
	{
		return ctx.getServerHandler().player;
	}
	
	public EntityPlayer getClientPlayer(MessageContext ctx)
	{
		return ctx.getServerHandler().player;
	}
}
