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
	public float volume;
	public float pitch;
	
	public MessageSound()
	{
		
	}
	
	public MessageSound(int entityID, int soundID, float volume, float pitch)
	{
		this.entityID = entityID;
		this.soundID = soundID;
		this.volume = volume;
		this.pitch = pitch;
	}
	
	public MessageSound(int entityID, SoundEventGCI sound, float volume, float pitch)
	{
		this(entityID, sound.getID(), volume, pitch);
	}
	
	public MessageSound(Entity entity, int soundID, float volume, float pitch)
	{
		this(entity.getEntityId(), soundID, volume, pitch);
	}
	
	public MessageSound(Entity entity, SoundEventGCI sound, float volume, float pitch)
	{
		this(entity.getEntityId(), sound.getID(), volume, pitch);
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.entityID = buf.readInt();
		this.soundID = buf.readInt();
		this.volume = buf.readFloat();
		this.pitch = buf.readFloat();
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.entityID);
		buf.writeInt(this.soundID);
		buf.writeFloat(this.volume);
		buf.writeFloat(this.pitch);
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
				{
					player.playSound(sound, message.volume, message.pitch);
				}
			}
			
			return null;
		}
	}
}
