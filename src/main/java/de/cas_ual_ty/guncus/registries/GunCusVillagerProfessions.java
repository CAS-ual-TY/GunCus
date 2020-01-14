package de.cas_ual_ty.guncus.registries;

import com.google.common.collect.ImmutableSet;

import de.cas_ual_ty.guncus.GunCus;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@EventBusSubscriber(modid = GunCus.MOD_ID, bus = Bus.MOD)
@ObjectHolder(GunCus.MOD_ID)
public class GunCusVillagerProfessions
{
    public static final VillagerProfession ARMS_DEALER = null;
    
    @SubscribeEvent
    public static void registerVillagerProfessions(Register<VillagerProfession> event)
    {
        IForgeRegistry<VillagerProfession> registry = event.getRegistry();
        
        registry.register(new VillagerProfession("arms_dealer", GunCusPointOfInterestTypes.ARMS_DEALER, ImmutableSet.of(), ImmutableSet.of()).setRegistryName(GunCus.MOD_ID, "arms_dealer"));
    }
}
