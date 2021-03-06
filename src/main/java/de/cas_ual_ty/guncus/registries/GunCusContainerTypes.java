package de.cas_ual_ty.guncus.registries;

import de.cas_ual_ty.guncus.GunCus;
import de.cas_ual_ty.guncus.container.AccessoryMakerContainer;
import de.cas_ual_ty.guncus.container.AmmoMakerContainer;
import de.cas_ual_ty.guncus.container.AuxiliaryMakerContainer;
import de.cas_ual_ty.guncus.container.BarrelMakerContainer;
import de.cas_ual_ty.guncus.container.BulletMakerContainer;
import de.cas_ual_ty.guncus.container.GunMakerContainer;
import de.cas_ual_ty.guncus.container.GunTableContainer;
import de.cas_ual_ty.guncus.container.MagazineMakerContainer;
import de.cas_ual_ty.guncus.container.OpticMakerContainer;
import de.cas_ual_ty.guncus.container.PaintMakerContainer;
import de.cas_ual_ty.guncus.container.UnderbarrelMakerContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@EventBusSubscriber(modid = GunCus.MOD_ID, bus = Bus.MOD)
@ObjectHolder(GunCus.MOD_ID)
public class GunCusContainerTypes
{
    public static final ContainerType<GunTableContainer> GUN_TABLE = null;
    public static final ContainerType<GunMakerContainer> GUN_MAKER = null;
    public static final ContainerType<BulletMakerContainer> BULLET_MAKER = null;
    public static final ContainerType<OpticMakerContainer> OPTIC_MAKER = null;
    public static final ContainerType<AccessoryMakerContainer> ACCESSORY_MAKER = null;
    public static final ContainerType<BarrelMakerContainer> BARREL_MAKER = null;
    public static final ContainerType<UnderbarrelMakerContainer> UNDERBARREL_MAKER = null;
    public static final ContainerType<AuxiliaryMakerContainer> AUXILIARY_MAKER = null;
    public static final ContainerType<AmmoMakerContainer> AMMO_MAKER = null;
    public static final ContainerType<MagazineMakerContainer> MAGAZINE_MAKER = null;
    public static final ContainerType<PaintMakerContainer> PAINT_MAKER = null;
    
    @SubscribeEvent
    public static void registerContainerTypes(Register<ContainerType<?>> event)
    {
        IForgeRegistry<ContainerType<?>> registry = event.getRegistry();
        
        registry.register(new ContainerType<>(GunTableContainer::new).setRegistryName(GunCus.MOD_ID, "gun_table"));
        registry.register(new ContainerType<>((IContainerFactory<GunMakerContainer>)GunMakerContainer::new).setRegistryName(GunCus.MOD_ID, "gun_maker"));
        registry.register(new ContainerType<>((IContainerFactory<BulletMakerContainer>)BulletMakerContainer::new).setRegistryName(GunCus.MOD_ID, "bullet_maker"));
        registry.register(new ContainerType<>((IContainerFactory<OpticMakerContainer>)OpticMakerContainer::new).setRegistryName(GunCus.MOD_ID, "optic_maker"));
        registry.register(new ContainerType<>((IContainerFactory<AccessoryMakerContainer>)AccessoryMakerContainer::new).setRegistryName(GunCus.MOD_ID, "accessory_maker"));
        registry.register(new ContainerType<>((IContainerFactory<BarrelMakerContainer>)BarrelMakerContainer::new).setRegistryName(GunCus.MOD_ID, "barrel_maker"));
        registry.register(new ContainerType<>((IContainerFactory<UnderbarrelMakerContainer>)UnderbarrelMakerContainer::new).setRegistryName(GunCus.MOD_ID, "underbarrel_maker"));
        registry.register(new ContainerType<>((IContainerFactory<AuxiliaryMakerContainer>)AuxiliaryMakerContainer::new).setRegistryName(GunCus.MOD_ID, "auxiliary_maker"));
        registry.register(new ContainerType<>((IContainerFactory<AmmoMakerContainer>)AmmoMakerContainer::new).setRegistryName(GunCus.MOD_ID, "ammo_maker"));
        registry.register(new ContainerType<>((IContainerFactory<MagazineMakerContainer>)MagazineMakerContainer::new).setRegistryName(GunCus.MOD_ID, "magazine_maker"));
        registry.register(new ContainerType<>((IContainerFactory<PaintMakerContainer>)PaintMakerContainer::new).setRegistryName(GunCus.MOD_ID, "paint_maker"));
    }
}
