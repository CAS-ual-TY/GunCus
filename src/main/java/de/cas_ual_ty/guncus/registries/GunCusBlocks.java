package de.cas_ual_ty.guncus.registries;

import de.cas_ual_ty.guncus.GunCus;
import de.cas_ual_ty.guncus.block.BlockBulletMaker;
import de.cas_ual_ty.guncus.block.BlockGunMaker;
import de.cas_ual_ty.guncus.block.BlockGunTable;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
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
    public static final Block GUN_MAKER = null;
    public static final Block BULLET_MAKER = null;
    
    @SubscribeEvent
    public static void registerBlocks(Register<Block> event)
    {
        IForgeRegistry<Block> registry = event.getRegistry();
        
        registry.register(new BlockGunTable(Properties.from(Blocks.IRON_BLOCK)).setRegistryName(GunCus.MOD_ID, "gun_table"));
        registry.register(new BlockGunMaker(Properties.from(Blocks.IRON_BLOCK)).setRegistryName(GunCus.MOD_ID, "gun_maker"));
        registry.register(new BlockBulletMaker(Properties.from(Blocks.IRON_BLOCK)).setRegistryName(GunCus.MOD_ID, "bullet_maker"));
    }
}
