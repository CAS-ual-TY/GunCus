package de.cas_ual_ty.guncus.registries;

import de.cas_ual_ty.guncus.GunCus;
import de.cas_ual_ty.guncus.block.BlockGunTable;
import net.minecraft.block.Block;
import net.minecraft.block.Block.Properties;
import net.minecraft.block.Blocks;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@EventBusSubscriber(modid = GunCus.MOD_ID, bus = Bus.MOD)
@ObjectHolder(GunCus.MOD_ID)
public class GunCusBlocks
{
    public static final Block GUN_TABLE = null;
    
    @SubscribeEvent
    public static void registerBlocks(Register<Block> event)
    {
        IForgeRegistry<Block> registry = event.getRegistry();
        
        registry.register(new BlockGunTable(Properties.from(Blocks.IRON_BLOCK)).setRegistryName(GunCus.MOD_ID, "gun_table"));
    }
}
