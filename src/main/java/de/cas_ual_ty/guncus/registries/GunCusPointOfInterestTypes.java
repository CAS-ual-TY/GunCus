package de.cas_ual_ty.guncus.registries;

import de.cas_ual_ty.guncus.GunCus;
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
        
        try
        {
            registry.register(new PointOfInterestType("arms_dealer", PointOfInterestType.getAllStates(GunCusBlocks.GUN_TABLE), 1, 1).setRegistryName(GunCus.MOD_ID, "arms_dealer"));
        }
        catch (NullPointerException e)
        {
            //NPE thrown during DataGen. no idea why
            new Exception("DataGen? Arms Dealer Villagers wont work.", e).printStackTrace();
        }
    }
}
