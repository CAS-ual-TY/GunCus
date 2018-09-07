package de.cas_ual_ty.gci.network;

import de.cas_ual_ty.gci.GunCus;
import de.cas_ual_ty.gci.item.ItemGun;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageShoot implements IMessage
{
	public boolean aiming;
	public boolean moving;
	
	public MessageShoot()
	{
		
	}
	
	public MessageShoot(boolean aiming, boolean moving)
	{
		this.aiming = aiming;
		this.moving = moving;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.aiming = buf.readBoolean();
		this.moving = buf.readBoolean();
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeBoolean(this.aiming);
		buf.writeBoolean(this.moving);
	}
	
	public static class MessageHandlerShoot implements IMessageHandler<MessageShoot, IMessage>
	{
		@Override
		public IMessage onMessage(MessageShoot message, MessageContext ctx)
		{
			EntityPlayer player = GunCus.proxy.getServerPlayer(ctx);
			
			if(player != null)
			{
				ItemStack item;
				
				for(EnumHand hand : EnumHand.values())
				{
					item = player.getHeldItem(hand);
					
					if(/*!item.isEmpty() && */item.getItem() instanceof ItemGun)
					{
						((ItemGun)item.getItem()).tryShootOrReload(player, item, message.aiming, message.moving);
					}
				}
			}
			
			return null;
		}
	}
}
