package de.cas_ual_ty.guncus.registries;

import de.cas_ual_ty.guncus.GunCus;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@EventBusSubscriber(modid = GunCus.MOD_ID, bus = Bus.MOD)
@ObjectHolder(GunCus.MOD_ID)
public class GunCusSoundEvents
{
    public static final SoundEvent RELOAD = null;
    public static final SoundEvent SHOOT_SILENCED = null;
    public static final SoundEvent SHOOT_SNIPER = null;
    public static final SoundEvent SHOOT = null;
    
    @SubscribeEvent
    public static void registerBlocks(Register<SoundEvent> event)
    {
        IForgeRegistry<SoundEvent> registry = event.getRegistry();
        
        registry.register(new SoundEvent(new ResourceLocation(GunCus.MOD_ID, "reload")).setRegistryName(GunCus.MOD_ID, "reload"));
        registry.register(new SoundEvent(new ResourceLocation(GunCus.MOD_ID, "shoot_silenced")).setRegistryName(GunCus.MOD_ID, "shoot_silenced"));
        registry.register(new SoundEvent(new ResourceLocation(GunCus.MOD_ID, "shoot_sniper")).setRegistryName(GunCus.MOD_ID, "shoot_sniper"));
        registry.register(new SoundEvent(new ResourceLocation(GunCus.MOD_ID, "shoot")).setRegistryName(GunCus.MOD_ID, "shoot"));
    }
}
