package de.cas_ual_ty.guncus.network;

import java.util.function.Supplier;

import de.cas_ual_ty.guncus.container.ContainerBulletMaker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageBulletMaker
{
    public String modid;
    public String name;
    
    public MessageBulletMaker(String modid, String name)
    {
        this.modid = modid;
        this.name = name;
    }
    
    public static void encode(MessageBulletMaker msg, PacketBuffer buf)
    {
        buf.writeString(msg.modid);
        buf.writeString(msg.name);
    }
    
    public static MessageBulletMaker decode(PacketBuffer buf)
    {
        return new MessageBulletMaker(buf.readString(), buf.readString());
    }
    
    public static void handle(MessageBulletMaker msg, Supplier<Context> ctx)
    {
        Context context = ctx.get();
        
        context.enqueueWork(() ->
        {
            PlayerEntity player = context.getSender();
            
            if(player != null && player.openContainer instanceof ContainerBulletMaker)
            {
                ContainerBulletMaker c = (ContainerBulletMaker)player.openContainer;
                c.setBullet(msg.modid, msg.name);
            }
        });
        
        context.setPacketHandled(true);
    }
}
