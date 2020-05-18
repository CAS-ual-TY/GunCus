package de.cas_ual_ty.guncus.network;

import java.util.function.Supplier;

import de.cas_ual_ty.guncus.container.ContainerGunMaker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageGunMaker
{
    public String modid;
    public String name;
    
    public MessageGunMaker(String modid, String name)
    {
        this.modid = modid;
        this.name = name;
    }
    
    public static void encode(MessageGunMaker msg, PacketBuffer buf)
    {
        buf.writeString(msg.modid);
        buf.writeString(msg.name);
    }
    
    public static MessageGunMaker decode(PacketBuffer buf)
    {
        return new MessageGunMaker(buf.readString(), buf.readString());
    }
    
    public static void handle(MessageGunMaker msg, Supplier<Context> ctx)
    {
        Context context = ctx.get();
        
        context.enqueueWork(() ->
        {
            PlayerEntity player = context.getSender();
            
            if(player != null && player.openContainer instanceof ContainerGunMaker)
            {
                ContainerGunMaker c = (ContainerGunMaker)player.openContainer;
                c.setGun(msg.modid, msg.name);
            }
        });
        
        context.setPacketHandled(true);
    }
}
