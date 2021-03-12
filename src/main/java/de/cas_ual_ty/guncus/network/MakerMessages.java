package de.cas_ual_ty.guncus.network;

import java.util.function.Consumer;
import java.util.function.Supplier;

import de.cas_ual_ty.guncus.container.MakerContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MakerMessages
{
    private static void doForContainer(PlayerEntity player, Consumer<MakerContainer> containerConsumer)
    {
        if(player != null && player.openContainer instanceof MakerContainer)
        {
            containerConsumer.accept((MakerContainer)player.openContainer);
        }
    }
    
    public static class Ready
    {
        public static void encode(Ready msg, PacketBuffer buf)
        {
        }
        
        public static Ready decode(PacketBuffer buf)
        {
            return new Ready();
        }
        
        public static void handle(Ready msg, Supplier<Context> ctx)
        {
            Context context = ctx.get();
            
            context.enqueueWork(() ->
            {
                MakerMessages.doForContainer(context.getSender(), container -> container.populate());
            });
            
            context.setPacketHandled(true);
        }
    }
    
    public static class Next
    {
        public static void encode(Next msg, PacketBuffer buf)
        {
        }
        
        public static Next decode(PacketBuffer buf)
        {
            return new Next();
        }
        
        public static void handle(Next msg, Supplier<Context> ctx)
        {
            Context context = ctx.get();
            
            context.enqueueWork(() ->
            {
                MakerMessages.doForContainer(context.getSender(), container -> container.nextItem());
            });
            
            context.setPacketHandled(true);
        }
    }
    
    public static class Prev
    {
        public static void encode(Prev msg, PacketBuffer buf)
        {
        }
        
        public static Prev decode(PacketBuffer buf)
        {
            return new Prev();
        }
        
        public static void handle(Prev msg, Supplier<Context> ctx)
        {
            Context context = ctx.get();
            
            context.enqueueWork(() ->
            {
                MakerMessages.doForContainer(context.getSender(), container -> container.prevItem());
            });
            
            context.setPacketHandled(true);
        }
    }
    
    public static class Create
    {
        public static void encode(Create msg, PacketBuffer buf)
        {
        }
        
        public static Create decode(PacketBuffer buf)
        {
            return new Create();
        }
        
        public static void handle(Create msg, Supplier<Context> ctx)
        {
            Context context = ctx.get();
            
            context.enqueueWork(() ->
            {
                MakerMessages.doForContainer(context.getSender(), container -> container.create());
            });
            
            context.setPacketHandled(true);
        }
    }
}
