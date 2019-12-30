package de.cas_ual_ty.guncus.network;

import java.util.function.Supplier;

import de.cas_ual_ty.guncus.GunCus;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageHitmarker
{
    public static void encode(MessageHitmarker msg, PacketBuffer buf)
    {
    }
    
    public static MessageHitmarker decode(PacketBuffer buf)
    {
        return new MessageHitmarker();
    }
    
    public static void handle(MessageHitmarker msg, Supplier<Context> ctx)
    {
        Context context = ctx.get();
        
        context.enqueueWork(() -> {
            GunCus.proxy.addHitmarker(GunCus.proxy.getPlayerFromContext(context));
        });
        
        context.setPacketHandled(true);
    }
}
