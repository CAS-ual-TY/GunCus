package de.cas_ual_ty.guncus.server;

import de.cas_ual_ty.guncus.GunCus;
import de.cas_ual_ty.guncus.IProxy;
import de.cas_ual_ty.guncus.item.GunItem;
import de.cas_ual_ty.guncus.network.HitmarkerMessage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import net.minecraftforge.fml.network.PacketDistributor;

public class ServerProxy implements IProxy
{
    @Override
    public void registerModEventListeners(IEventBus bus)
    {
    }
    
    @Override
    public void registerForgeEventListeners(IEventBus bus)
    {
    }
    
    @Override
    public void init()
    {
    }
    
    @Override
    public void addHitmarker(PlayerEntity player)
    {
        GunCus.channel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)player), new HitmarkerMessage());
    }
    
    @Override
    public PlayerEntity getPlayerFromContext(Context context)
    {
        return context.getSender();
    }
    
    @Override
    public void shot(ItemStack itemStack, GunItem gun, PlayerEntity player, Hand hand)
    {
        
    }
}
