package de.cas_ual_ty.guncus.registries;

import de.cas_ual_ty.guncus.GunCus;
import de.cas_ual_ty.guncus.util.GunCusUtility;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@EventBusSubscriber(modid = GunCus.MOD_ID, bus = Bus.MOD)
@ObjectHolder(GunCus.MOD_ID)
public class GunCusPointOfInterestTypes
{
    public static final PointOfInterestType ARMS_DEALER = null;
    
    @SubscribeEvent
    public static void registerPointOfInterestTypes(Register<PointOfInterestType> event)
    {
        IForgeRegistry<PointOfInterestType> registry = event.getRegistry();
        
        registry.register(GunCusUtility.pointOfInterestType("arms_dealer", GunCusUtility.getAllStates(GunCusBlocks.GUN_TABLE), 1, 1).setRegistryName(GunCus.MOD_ID, "arms_dealer"));
    }
}
