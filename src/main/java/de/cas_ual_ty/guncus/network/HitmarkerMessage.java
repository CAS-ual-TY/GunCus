package de.cas_ual_ty.guncus.network;

import java.util.function.Supplier;

import de.cas_ual_ty.guncus.GunCus;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class HitmarkerMessage
{
    public static void encode(HitmarkerMessage msg, PacketBuffer buf)
    {
    }
    
    public static HitmarkerMessage decode(PacketBuffer buf)
    {
        return new HitmarkerMessage();
    }
    
    public static void handle(HitmarkerMessage msg, Supplier<Context> ctx)
    {
        Context context = ctx.get();
        
        context.enqueueWork(() ->
        {
            GunCus.proxy.addHitmarker(GunCus.proxy.getPlayerFromContext(context));
        });
        
        context.setPacketHandled(true);
    }
}
