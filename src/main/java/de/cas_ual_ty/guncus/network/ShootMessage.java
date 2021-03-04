package de.cas_ual_ty.guncus.network;

import java.util.function.Supplier;

import de.cas_ual_ty.guncus.item.GunItem;
import de.cas_ual_ty.guncus.util.GunCusUtility;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class ShootMessage
{
    public boolean aiming;
    public int inaccuracy;
    public int handsInt;
    
    public ShootMessage(boolean aiming, int inaccuracy, int handsInt)
    {
        this.aiming = aiming;
        this.inaccuracy = inaccuracy;
        this.handsInt = handsInt;
    }
    
    public static void encode(ShootMessage msg, PacketBuffer buf)
    {
        buf.writeBoolean(msg.aiming);
        buf.writeInt(msg.inaccuracy);
        buf.writeInt(msg.handsInt);
    }
    
    public static ShootMessage decode(PacketBuffer buf)
    {
        return new ShootMessage(buf.readBoolean(), buf.readInt(), buf.readInt());
    }
    
    public static void handle(ShootMessage msg, Supplier<Context> ctx)
    {
        Context context = ctx.get();
        
        context.enqueueWork(() ->
        {
            PlayerEntity player = context.getSender();
            
            if(player != null)
            {
                GunItem.tryShoot(player, msg.aiming, msg.inaccuracy, GunCusUtility.intToHands(msg.handsInt));
            }
        });
        
        context.setPacketHandled(true);
    }
}
