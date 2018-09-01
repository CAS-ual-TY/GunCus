package de.cas_ual_ty.gci.network;

import de.cas_ual_ty.gci.GunCus;
import de.cas_ual_ty.gci.SoundEventGCI;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageRecoil implements IMessage
{
	public float pitch;
	public float yaw;
	
	public MessageRecoil()
	{
		
	}
	
	public MessageRecoil(float pitch, float yaw)
	{
		this.pitch = pitch;
		this.yaw = yaw;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.pitch = buf.readFloat();
		this.yaw = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeFloat(this.pitch);
		buf.writeFloat(this.yaw);
	}
	
	public static class MessageHandlerRecoil implements IMessageHandler<MessageRecoil, IMessage>
	{
		@Override
		public IMessage onMessage(MessageRecoil message, MessageContext ctx)
		{
			EntityPlayer player = GunCus.proxy.getClientPlayer(ctx);
			
			if(player != null)
			{
				player.rotationPitch += message.pitch;
				player.rotationYaw += message.yaw;
			}
			
			return null;
		}
	}
}
