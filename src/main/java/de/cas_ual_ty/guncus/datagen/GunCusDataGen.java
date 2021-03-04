package de.cas_ual_ty.guncus.datagen;

import de.cas_ual_ty.guncus.GunCus;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class GunCusDataGen
{
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();
        generator.addProvider(new GunCusBlockModels(generator, GunCus.MOD_ID, event.getExistingFileHelper()));
        generator.addProvider(new GunCusBlockStates(generator, GunCus.MOD_ID, event.getExistingFileHelper()));
        generator.addProvider(new GunCusGunModels(generator, GunCus.MOD_ID, event.getExistingFileHelper()));
        generator.addProvider(new GunCusItemModels(generator, GunCus.MOD_ID, event.getExistingFileHelper()));
    }
}
