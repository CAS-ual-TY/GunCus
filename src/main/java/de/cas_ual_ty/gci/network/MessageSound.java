package de.cas_ual_ty.gci.network;

import de.cas_ual_ty.gci.GunCus;
import de.cas_ual_ty.gci.SoundEventGCI;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSound implements IMessage
{
	public int entityID;
	public int soundID;
	
	public MessageSound()
	{
		
	}
	
	public MessageSound(int entityID, int soundID)
	{
		this.entityID = entityID;
		this.soundID = soundID;
	}
	
	public MessageSound(int entityID, SoundEventGCI sound)
	{
		this(entityID, sound.getID());
	}
	
	public MessageSound(Entity entity, int soundID)
	{
		this(entity.getEntityId(), soundID);
	}
	
	public MessageSound(Entity entity, SoundEventGCI sound)
	{
		this(entity.getEntityId(), sound.getID());
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.entityID = buf.readInt();
		this.soundID = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.entityID);
		buf.writeInt(this.soundID);
	}
	
	public static class MessageHandlerSound implements IMessageHandler<MessageSound, IMessage>
	{
		@Override
		public IMessage onMessage(MessageSound message, MessageContext ctx)
		{
			EntityPlayer player = GunCus.proxy.getClientPlayer(ctx);
			
			if(player != null)
			{
				SoundEventGCI sound = SoundEventGCI.soundEventList[message.soundID];
				
				if(sound != null)
					player.playSound(sound, 1F, 1F);
			}
			
			return null;
		}
	}
}
