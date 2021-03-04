package de.cas_ual_ty.guncus;

import de.cas_ual_ty.guncus.item.GunItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public interface IProxy
{
    public void registerModEventListeners(IEventBus bus);
    
    public void registerForgeEventListeners(IEventBus bus);
    
    public void init();
    
    public void addHitmarker(PlayerEntity player);
    
    public PlayerEntity getPlayerFromContext(Context context);
    
    public void shot(ItemStack itemStack, GunItem gun, PlayerEntity player, Hand hand);
}
