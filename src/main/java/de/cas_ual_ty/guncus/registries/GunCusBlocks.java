package de.cas_ual_ty.guncus.registries;

import de.cas_ual_ty.guncus.GunCus;
import de.cas_ual_ty.guncus.block.AccessoryMakerBlock;
import de.cas_ual_ty.guncus.block.AmmoMakerBlock;
import de.cas_ual_ty.guncus.block.AuxiliaryMakerBlock;
import de.cas_ual_ty.guncus.block.BarrelMakerBlock;
import de.cas_ual_ty.guncus.block.BulletMakerBlock;
import de.cas_ual_ty.guncus.block.GunMakerBlock;
import de.cas_ual_ty.guncus.block.GunTableBlock;
import de.cas_ual_ty.guncus.block.MagazineMakerBlock;
import de.cas_ual_ty.guncus.block.OpticMakerBlock;
import de.cas_ual_ty.guncus.block.PaintMakerBlock;
import de.cas_ual_ty.guncus.block.UnderbarrelMakerBlock;
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
    public static final Block OPTIC_MAKER = null;
    public static final Block ACCESSORY_MAKER = null;
    public static final Block BARREL_MAKER = null;
    public static final Block UNDERBARREL_MAKER = null;
    public static final Block AUXILIARY_MAKER = null;
    public static final Block AMMO_MAKER = null;
    public static final Block MAGAZINE_MAKER = null;
    public static final Block PAINT_MAKER = null;
    
    @SubscribeEvent
    public static void registerBlocks(Register<Block> event)
    {
        IForgeRegistry<Block> registry = event.getRegistry();
        
        registry.register(new GunTableBlock(Properties.from(Blocks.IRON_BLOCK)).setRegistryName(GunCus.MOD_ID, "gun_table"));
        registry.register(new GunMakerBlock(Properties.from(Blocks.IRON_BLOCK)).setRegistryName(GunCus.MOD_ID, "gun_maker"));
        registry.register(new BulletMakerBlock(Properties.from(Blocks.IRON_BLOCK)).setRegistryName(GunCus.MOD_ID, "bullet_maker"));
        registry.register(new OpticMakerBlock(Properties.from(Blocks.IRON_BLOCK)).setRegistryName(GunCus.MOD_ID, "optic_maker"));
        registry.register(new AccessoryMakerBlock(Properties.from(Blocks.IRON_BLOCK)).setRegistryName(GunCus.MOD_ID, "accessory_maker"));
        registry.register(new BarrelMakerBlock(Properties.from(Blocks.IRON_BLOCK)).setRegistryName(GunCus.MOD_ID, "barrel_maker"));
        registry.register(new UnderbarrelMakerBlock(Properties.from(Blocks.IRON_BLOCK)).setRegistryName(GunCus.MOD_ID, "underbarrel_maker"));
        registry.register(new AuxiliaryMakerBlock(Properties.from(Blocks.IRON_BLOCK)).setRegistryName(GunCus.MOD_ID, "auxiliary_maker"));
        registry.register(new AmmoMakerBlock(Properties.from(Blocks.IRON_BLOCK)).setRegistryName(GunCus.MOD_ID, "ammo_maker"));
        registry.register(new MagazineMakerBlock(Properties.from(Blocks.IRON_BLOCK)).setRegistryName(GunCus.MOD_ID, "magazine_maker"));
        registry.register(new PaintMakerBlock(Properties.from(Blocks.IRON_BLOCK)).setRegistryName(GunCus.MOD_ID, "paint_maker"));
    }
}
