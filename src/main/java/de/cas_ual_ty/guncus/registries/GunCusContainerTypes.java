package de.cas_ual_ty.guncus.registries;

import de.cas_ual_ty.guncus.GunCus;
import de.cas_ual_ty.guncus.container.ContainerBulletMaker;
import de.cas_ual_ty.guncus.container.ContainerGunMaker;
import de.cas_ual_ty.guncus.container.ContainerGunTable;
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
    public static final ContainerType<ContainerGunMaker> GUN_MAKER = null;
    public static final ContainerType<ContainerBulletMaker> BULLET_MAKER = null;
    
    @SubscribeEvent
    public static void registerContainerTypes(Register<ContainerType<?>> event)
    {
        IForgeRegistry<ContainerType<?>> registry = event.getRegistry();
        
        registry.register(new ContainerType<>(ContainerGunTable::new).setRegistryName(GunCus.MOD_ID, "gun_table"));
        registry.register(new ContainerType<>(ContainerGunMaker::new).setRegistryName(GunCus.MOD_ID, "gun_maker"));
        registry.register(new ContainerType<>(ContainerBulletMaker::new).setRegistryName(GunCus.MOD_ID, "bullet_maker"));
    }
}
