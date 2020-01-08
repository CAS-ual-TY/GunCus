package de.cas_ual_ty.guncus.registries;

import de.cas_ual_ty.guncus.GunCus;
import de.cas_ual_ty.guncus.container.ContainerGunTable;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@EventBusSubscriber(modid = GunCus.MOD_ID, bus = Bus.MOD)
@ObjectHolder(GunCus.MOD_ID)
public class GunCusContainerTypes
{
    public static final ContainerType<ContainerGunTable> GUN_TABLE = null;
    
    @SubscribeEvent
    public static void registerContainerTypes(Register<ContainerType<? extends Container>> event)
    {
        IForgeRegistry<ContainerType<? extends Container>> registry = event.getRegistry();
        
        registry.register(new ContainerType<>(ContainerGunTable::new).setRegistryName(GunCus.MOD_ID, "gun_table"));
    }
}
