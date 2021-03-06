package de.cas_ual_ty.guncus.registries;

import de.cas_ual_ty.guncus.GunCus;
import de.cas_ual_ty.guncus.item.AttachmentItem;
import de.cas_ual_ty.guncus.item.AttachmentMakerBlockItem;
import de.cas_ual_ty.guncus.item.BulletItem;
import de.cas_ual_ty.guncus.item.GunItem;
import de.cas_ual_ty.guncus.item.attachments.Accessory;
import de.cas_ual_ty.guncus.item.attachments.Accessory.Laser;
import de.cas_ual_ty.guncus.item.attachments.Ammo;
import de.cas_ual_ty.guncus.item.attachments.Auxiliary;
import de.cas_ual_ty.guncus.item.attachments.Barrel;
import de.cas_ual_ty.guncus.item.attachments.EnumAttachmentType;
import de.cas_ual_ty.guncus.item.attachments.Magazine;
import de.cas_ual_ty.guncus.item.attachments.Optic;
import de.cas_ual_ty.guncus.item.attachments.Paint;
import de.cas_ual_ty.guncus.item.attachments.Underbarrel;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@EventBusSubscriber(modid = GunCus.MOD_ID, bus = Bus.MOD)
@ObjectHolder(GunCus.MOD_ID)
public class GunCusItems
{
    public static final BlockItem GUN_TABLE = null;
    public static final BlockItem GUN_MAKER = null;
    public static final BlockItem BULLET_MAKER = null;
    public static final BlockItem OPTIC_MAKER = null;
    public static final BlockItem ACCESSORY_MAKER = null;
    public static final BlockItem BARREL_MAKER = null;
    public static final BlockItem UNDERBARREL_MAKER = null;
    public static final BlockItem AUXILIARY_MAKER = null;
    public static final BlockItem AMMO_MAKER = null;
    public static final BlockItem MAGAZINE_MAKER = null;
    public static final BlockItem PAINT_MAKER = null;
    
    public static final GunItem GUN_M16A4 = null;
    public static final GunItem GUN_AK_74M = null;
    public static final GunItem GUN_SCAR_L = null;
    public static final GunItem GUN_SCAR_H = null;
    public static final GunItem GUN_M27_IAR = null;
    public static final GunItem GUN_SV98 = null;
    public static final GunItem GUN_COLT_ANACONDA = null;
    
    public static final BulletItem BULLET_5_56x45MM = null;
    public static final BulletItem BULLET_5_45x39MM = null;
    public static final BulletItem BULLET_7_62x51MM = null;
    public static final BulletItem BULLET_7_62x54MM = null;
    public static final BulletItem BULLET_12G_BUCKSHOT = null;
    public static final BulletItem BULLET_12G_DART = null;
    public static final BulletItem BULLET_12G_FRAG = null;
    public static final BulletItem BULLET_12G_SLUG = null;
    public static final BulletItem BULLET__44_MAGNUM = null;
    public static final BulletItem BULLET__338_MAGNUM = null;
    public static final BulletItem BULLET_9MM = null;
    
    public static final Optic OPTIC_REFLEX = null;
    public static final Optic OPTIC_COYOTE = null;
    public static final Optic OPTIC_KOBRA = null;
    public static final Optic OPTIC_HOLO = null;
    public static final Optic OPTIC_HD33 = null;
    public static final Optic OPTIC_PKAS = null;
    //  public static final Optic       OPTIC_IRNV                  = null;
    //  public static final Optic       OPTIC_FLIR                  = null;
    public static final Optic OPTIC_M145 = null;
    public static final Optic OPTIC_PRISMA = null;
    public static final Optic OPTIC_PKA = null;
    public static final Optic OPTIC_ACOG = null;
    public static final Optic OPTIC_JGM4 = null;
    public static final Optic OPTIC_PSO1 = null;
    public static final Optic OPTIC_CL6X = null;
    public static final Optic OPTIC_PKS07 = null;
    public static final Optic OPTIC_RIFLE = null;
    public static final Optic OPTIC_HUNTER = null;
    public static final Optic OPTIC_BALLISTIC = null;
    
    public static final Accessory ACCESSORY_VARIABLE_ZOOM = null;
    public static final Accessory ACCESSORY_MAGNIFIER = null;
    // ACCESSORY_FLASH_LIGHT
    // ACCESSORY_TACTICAL LIGHT
    public static final Accessory ACCESSORY_LASER_SIGHT = null;
    public static final Accessory ACCESSORY_TRI_BEAM_LASER = null;
    public static final Accessory ACCESSORY_GREEN_LASER_SIGHT = null;
    // ACCESSORY_LASER_LIGHT_COMBO
    public static final Accessory ACCESSORY_RANGE_FINDER = null;
    
    public static final Barrel BARREL_HEAVY_BARREL = null;
    public static final Barrel BARREL_SUPPRESSOR = null;
    public static final Barrel BARREL_LS06_SUPPRESSOR = null;
    public static final Barrel BARREL_PBS4_SUPPRESSOR = null;
    public static final Barrel BARREL_R2_SUPPRESSOR = null;
    public static final Barrel BARREL_FLASH_HIDER = null;
    public static final Barrel BARREL_COMPENSATOR = null;
    public static final Barrel BARREL_MUZZLE_BRAKE = null;
    public static final Barrel BARREL_DUCKBILL = null;
    public static final Barrel BARREL_MODIFIED_CHOKE = null;
    public static final Barrel BARREL_FULL_CHOKE = null;
    
    public static final Underbarrel UNDERBARREL_BIPOD = null;
    public static final Underbarrel UNDERBARREL_ERGO_GRIP = null;
    public static final Underbarrel UNDERBARREL_ANGLED_GRIP = null;
    public static final Underbarrel UNDERBARREL_STUBBY_GRIP = null;
    public static final Underbarrel UNDERBARREL_VERTICAL_GRIP = null;
    public static final Underbarrel UNDERBARREL_FOLDING_GRIP = null;
    public static final Underbarrel UNDERBARREL_POTATO_GRIP = null;
    
    public static final Auxiliary AUXILIARY_STRAIGHT_PULL = null;
    public static final Auxiliary AUXILIARY_RAPID_FIRE = null;
    
    public static final Ammo AMMO_12G_DART = null;
    public static final Ammo AMMO_12G_FRAG = null;
    public static final Ammo AMMO_12G_SLUG = null;
    public static final Ammo AMMO_7_62x51MM = null;
    
    public static final Magazine MAGAZINE_QUICK_SWITCH = null;
    public static final Magazine MAGAZINE_EXTENDED_10 = null;
    public static final Magazine MAGAZINE_EXTENDED_5 = null;
    
    public static final Paint PAINT_BLACK = null;
    public static final Paint PAINT_BLUE = null;
    public static final Paint PAINT_BROWN = null;
    public static final Paint PAINT_CYAN = null;
    public static final Paint PAINT_GRAY = null;
    public static final Paint PAINT_GREEN = null;
    public static final Paint PAINT_LIGHT_BLUE = null;
    public static final Paint PAINT_LIGHT_GRAY = null;
    public static final Paint PAINT_LIME = null;
    public static final Paint PAINT_MAGENTA = null;
    public static final Paint PAINT_ORANGE = null;
    public static final Paint PAINT_PINK = null;
    public static final Paint PAINT_PURPLE = null;
    public static final Paint PAINT_RED = null;
    public static final Paint PAINT_WHITE = null;
    public static final Paint PAINT_YELLOW = null;
    
    @SubscribeEvent
    public static void registerItems(Register<Item> event)
    {
        IForgeRegistry<Item> registry = event.getRegistry();
        
        registry.register(new BlockItem(GunCusBlocks.GUN_TABLE, new Properties().group(GunCus.ITEM_GROUP_GUN_CUS)).setRegistryName(GunCus.MOD_ID, "gun_table"));
        registry.register(new BlockItem(GunCusBlocks.GUN_MAKER, new Properties().group(GunCus.ITEM_GROUP_GUN_CUS)).setRegistryName(GunCus.MOD_ID, "gun_maker"));
        registry.register(new AttachmentMakerBlockItem(GunCusBlocks.BULLET_MAKER, new Properties().group(GunCus.ITEM_GROUP_GUN_CUS)).setRegistryName(GunCus.MOD_ID, "bullet_maker"));
        registry.register(new AttachmentMakerBlockItem(GunCusBlocks.OPTIC_MAKER, new Properties().group(GunCus.ITEM_GROUP_GUN_CUS)).setRegistryName(GunCus.MOD_ID, "optic_maker"));
        registry.register(new AttachmentMakerBlockItem(GunCusBlocks.ACCESSORY_MAKER, new Properties().group(GunCus.ITEM_GROUP_GUN_CUS)).setRegistryName(GunCus.MOD_ID, "accessory_maker"));
        registry.register(new AttachmentMakerBlockItem(GunCusBlocks.BARREL_MAKER, new Properties().group(GunCus.ITEM_GROUP_GUN_CUS)).setRegistryName(GunCus.MOD_ID, "barrel_maker"));
        registry.register(new AttachmentMakerBlockItem(GunCusBlocks.UNDERBARREL_MAKER, new Properties().group(GunCus.ITEM_GROUP_GUN_CUS)).setRegistryName(GunCus.MOD_ID, "underbarrel_maker"));
        registry.register(new AttachmentMakerBlockItem(GunCusBlocks.AUXILIARY_MAKER, new Properties().group(GunCus.ITEM_GROUP_GUN_CUS)).setRegistryName(GunCus.MOD_ID, "auxiliary_maker"));
        registry.register(new AttachmentMakerBlockItem(GunCusBlocks.AMMO_MAKER, new Properties().group(GunCus.ITEM_GROUP_GUN_CUS)).setRegistryName(GunCus.MOD_ID, "ammo_maker"));
        registry.register(new AttachmentMakerBlockItem(GunCusBlocks.MAGAZINE_MAKER, new Properties().group(GunCus.ITEM_GROUP_GUN_CUS)).setRegistryName(GunCus.MOD_ID, "magazine_maker"));
        registry.register(new AttachmentMakerBlockItem(GunCusBlocks.PAINT_MAKER, new Properties().group(GunCus.ITEM_GROUP_GUN_CUS)).setRegistryName(GunCus.MOD_ID, "paint_maker"));
        
        registry.register(new GunItem(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS).maxStackSize(1), 3, 30, 4, () -> GunCusItems.BULLET_5_56x45MM, 6, 0, 2).setAttachments(() -> GunCusItems.assaultRifleAttachments()).createGunTab("gun_m16a4").setDefaultTradeable(32, 3).setRegistryName(GunCus.MOD_ID, "gun_m16a4"));
        registry.register(new GunItem(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS).maxStackSize(1), 4, 30, 4, () -> GunCusItems.BULLET_5_45x39MM, 6, 0, 2).setAttachments(() -> GunCusItems.assaultRifleAttachments()).createGunTab("gun_ak_74m").setDefaultTradeable(32, 3).setRegistryName(GunCus.MOD_ID, "gun_ak_74m"));
        registry.register(new GunItem(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS).maxStackSize(1), 4, 30, 4, () -> GunCusItems.BULLET_5_56x45MM, 8, 2, 2).setAttachments(() -> GunCusItems.attachments(GunCusItems.assaultRifleLongAttachments(), GunCusItems.AMMO_7_62x51MM)).createGunTab("gun_scar").setDefaultTradeable(32, 5).setRegistryName(GunCus.MOD_ID, "gun_scar"));
        registry.register(new GunItem(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS).maxStackSize(1), 4, 30, 4, () -> GunCusItems.BULLET_5_56x45MM, 10, 0, 1).setAttachments(() -> GunCusItems.assaultRifleAttachments()).createGunTab("gun_m27_iar").setDefaultTradeable(32, 4).setRegistryName(GunCus.MOD_ID, "gun_m27_iar"));
        registry.register(new GunItem(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS).maxStackSize(1), 25, 10, 7, () -> GunCusItems.BULLET_7_62x54MM, 10, 1, 2).setAttachments(() -> GunCusItems.sniperRifleAttachments()).setSoundShoot(() -> GunCusSoundEvents.SHOOT_SNIPER).setBoltAction(() -> SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON).createGunTab("gun_sv98").setDefaultTradeable(32, 4).setRegistryName(GunCus.MOD_ID, "gun_sv98"));
        registry.register(new GunItem(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS).maxStackSize(1), 12, 6, 7, () -> GunCusItems.BULLET__44_MAGNUM, 4, 1, 0).setAttachments(() -> GunCusItems.defaultAttachments()).setSoundShoot(() -> GunCusSoundEvents.SHOOT_SNIPER).setSemiAuto().setDefaultTradeable(32, 2).setRegistryName(GunCus.MOD_ID, "gun_colt_anaconda"));
        registry.register(new GunItem(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS).maxStackSize(1), 12, 6, 7, () -> GunCusItems.BULLET_12G_BUCKSHOT, 6, 0, 2).setAttachments(() -> GunCusItems.shotgunAttachments()).setSoundShoot(() -> GunCusSoundEvents.SHOOT_SNIPER).setSemiAuto().createGunTab("gun_remington_870").setDefaultTradeable(32, 3).setRegistryName(GunCus.MOD_ID, "gun_remington_870"));
        registry.register(new GunItem(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS).maxStackSize(1), 3, 15, 3, () -> GunCusItems.BULLET_9MM, 4, 0, 1).setAttachments(() -> GunCusItems.attachments(GunCusItems.pistolAttachments(), GunCusItems.AUXILIARY_RAPID_FIRE)).setSemiAuto().createGunTab("gun_glock").setDefaultTradeable(32, 2).setRegistryName(GunCus.MOD_ID, "gun_glock"));
        
        registry.register(new BulletItem(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), 1, 1, 16).setDamage(4F).setDefaultTradeable(32).setRegistryName(GunCus.MOD_ID, "bullet_5_56x45mm"));
        registry.register(new BulletItem(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), 1, 1, 16).setDamage(4F).setDefaultTradeable(32).setRegistryName(GunCus.MOD_ID, "bullet_5_45x39mm"));
        registry.register(new BulletItem(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), 2, 2, 12).setDamage(6F).setDefaultTradeable(24).setRegistryName(GunCus.MOD_ID, "bullet_7_62x51mm"));
        registry.register(new BulletItem(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), 2, 2, 12).setDamage(6F).setDefaultTradeable(24).setRegistryName(GunCus.MOD_ID, "bullet_7_62x54mm"));
        registry.register(new BulletItem(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), 2, 2, 8).setDamage(2F).setDefaultTradeable(24).setProjectileAmount(6).setSpreadModifier(7.5F).setRegistryName(GunCus.MOD_ID, "bullet_12g_buckshot"));
        registry.register(new BulletItem(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), 2, 2, 8).setDamage(2F).setDefaultTradeable(24).setProjectileAmount(6).setSpreadModifier(3.75F).setRegistryName(GunCus.MOD_ID, "bullet_12g_dart"));
        registry.register(new BulletItem(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), 2, 2, 8).setDamage(2F).setOnHit((e) -> e.setFire(5)).setDefaultTradeable(24).setRegistryName(GunCus.MOD_ID, "bullet_12g_frag"));
        registry.register(new BulletItem(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), 2, 2, 8).setDamage(9F).setDefaultTradeable(24).setRegistryName(GunCus.MOD_ID, "bullet_12g_slug"));
        registry.register(new BulletItem(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), 4, 2, 4).setDamage(9F).setDefaultTradeable(8).setRegistryName(GunCus.MOD_ID, "bullet__44_magnum"));
        registry.register(new BulletItem(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), 4, 2, 4).setDamage(9F).setDefaultTradeable(8).setRegistryName(GunCus.MOD_ID, "bullet__338_magnum"));
        registry.register(new BulletItem(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), 1, 1, 24).setDamage(3F).setDefaultTradeable(48).setRegistryName(GunCus.MOD_ID, "bullet_9mm"));
        
        registry.register(new Optic(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.GLASS_PANE), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.REDSTONE)).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "optic_reflex"));
        registry.register(new Optic(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.GLASS_PANE), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.REDSTONE)).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "optic_coyote"));
        registry.register(new Optic(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.GLASS_PANE), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.REDSTONE)).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "optic_kobra"));
        registry.register(new Optic(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.GLASS_PANE), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.REDSTONE)).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "optic_holo"));
        registry.register(new Optic(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.GLASS_PANE), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.REDSTONE)).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "optic_hd33"));
        registry.register(new Optic(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.GLASS_PANE), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.REDSTONE)).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "optic_pkas"));
        // registry.register(new Optic(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS)).setDefaultTradeable().setRegistryName(MOD_ID, "optic_irnv").setOpticType(EnumOpticType.NIGHT_VISION));
        // registry.register((Optic) new Optic(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS)).setDefaultTradeable().setRegistryName(MOD_ID, "optic_flir").setZoom(2F).setOpticType(EnumOpticType.THERMAL));
        registry.register(new Optic(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.GLASS_PANE, 2), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.REDSTONE)).setExtraZoom(3.4F).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "optic_m145"));
        registry.register(new Optic(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.GLASS_PANE, 2), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.REDSTONE)).setExtraZoom(3.4F).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "optic_prisma"));
        registry.register(new Optic(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.GLASS_PANE, 2), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.REDSTONE)).setExtraZoom(3.4F).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "optic_pka"));
        registry.register(new Optic(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.GLASS_PANE, 2), new ItemStack(Items.IRON_INGOT, 2), new ItemStack(Items.REDSTONE)).setExtraZoom(4F).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "optic_acog"));
        registry.register(new Optic(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.GLASS_PANE, 2), new ItemStack(Items.IRON_INGOT, 2), new ItemStack(Items.REDSTONE)).setExtraZoom(4F).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "optic_jgm4"));
        registry.register(new Optic(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.GLASS_PANE, 2), new ItemStack(Items.IRON_INGOT, 2), new ItemStack(Items.REDSTONE)).setExtraZoom(4F).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "optic_pso1"));
        registry.register(new Optic(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.GLASS_PANE, 2), new ItemStack(Items.IRON_INGOT, 2), new ItemStack(Items.DIAMOND)).setExtraZoom(6F).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "optic_cl6x"));
        registry.register(new Optic(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.GLASS_PANE, 2), new ItemStack(Items.IRON_INGOT, 2), new ItemStack(Items.DIAMOND)).setExtraZoom(7F).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "optic_pks07"));
        registry.register(new Optic(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.GLASS_PANE, 2), new ItemStack(Items.IRON_INGOT, 2), new ItemStack(Items.DIAMOND)).setExtraZoom(8F).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "optic_rifle"));
        registry.register(new Optic(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.GLASS_PANE, 2), new ItemStack(Items.IRON_INGOT, 2), new ItemStack(Items.DIAMOND)).setExtraZoom(20F).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "optic_hunter"));
        registry.register(new Optic(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.GLASS_PANE, 2), new ItemStack(Items.IRON_INGOT, 2), new ItemStack(Items.DIAMOND)).setExtraZoom(40F).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "optic_ballistic"));
        
        registry.register(new Accessory(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.GLASS_PANE, 2), new ItemStack(Items.REDSTONE)).setExtraZoom(14F).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "accessory_variable_zoom"));
        registry.register(new Accessory(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.GLASS_PANE, 2), new ItemStack(Items.IRON_INGOT)).setZoomModifier(1.5F).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "accessory_magnifier"));
        // ACCESSORY_FLASH_LIGHT
        // ACCESSORY_TACTICAL LIGHT
        registry.register(new Accessory(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.REDSTONE), new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.DIAMOND), new ItemStack(Items.IRON_INGOT)).setLaser(new Laser(1F, 0F, 0F, 80D, false, true, false)).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "accessory_laser_sight"));
        registry.register(new Accessory(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.REDSTONE, 3), new ItemStack(Items.GOLD_INGOT, 3), new ItemStack(Items.DIAMOND, 3), new ItemStack(Items.IRON_INGOT)).setLaser(new Laser(1F, 0F, 0F, 80D, true, true, false)).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "accessory_tri_beam_laser"));
        registry.register(new Accessory(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.REDSTONE), new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.EMERALD), new ItemStack(Items.IRON_INGOT)).setLaser(new Laser(0F, 1F, 0F, 80D, false, true, false)).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "accessory_green_laser_sight"));
        // ACCESSORY_LASER_LIGHT_COMBO
        registry.register(new Accessory(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.REDSTONE), new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.DIAMOND), new ItemStack(Items.EMERALD), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.QUARTZ)).setLaser(new Laser(0F, 0.5F, 1F, 125D, false, true, true)).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "accessory_range_finder"));
        
        registry.register(new Barrel(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.IRON_INGOT, 2), new ItemStack(Items.GOLD_INGOT, 2)).setExtraDamage(1.0F).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "barrel_heavy_barrel"));
        registry.register(new Barrel(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.IRON_INGOT, 2), new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.STRING, 4)).setIsSilenced(true).setIsFlashHider(true).setSpeedModifier(0.7F).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "barrel_suppressor"));
        registry.register(new Barrel(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.IRON_INGOT, 2), new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.STRING, 4)).setIsSilenced(true).setIsFlashHider(true).setSpeedModifier(0.7F).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "barrel_ls06_suppressor"));
        registry.register(new Barrel(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.IRON_INGOT, 2), new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.STRING, 4)).setIsSilenced(true).setIsFlashHider(true).setSpeedModifier(0.7F).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "barrel_pbs4_suppressor"));
        registry.register(new Barrel(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.IRON_INGOT, 2), new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.STRING, 4)).setIsSilenced(true).setIsFlashHider(true).setSpeedModifier(0.7F).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "barrel_r2_suppressor"));
        registry.register(new Barrel(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.IRON_INGOT, 2), new ItemStack(Items.GOLD_INGOT, 2)).setIsFlashHider(true).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "barrel_flash_hider"));
        registry.register(new Barrel(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.IRON_INGOT, 2), new ItemStack(Items.GOLD_INGOT, 2)).setDriftModifier(1.25F).setInaccuracyModifier(1.2F).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "barrel_compensator"));
        registry.register(new Barrel(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.IRON_INGOT, 2), new ItemStack(Items.GOLD_INGOT, 2)).setDriftModifier(1.25F).setInaccuracyModifier(1.2F).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "barrel_muzzle_brake"));
        registry.register(new Barrel(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS)).setSpreadModifierVertical(0.4F).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "barrel_duckbill"));
        registry.register(new Barrel(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS)).setSpreadModifierVertical(0.8F).setSpreadModifierHorizontal(0.8F).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "barrel_modified_choke"));
        registry.register(new Barrel(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS)).setSpreadModifierVertical(0.6F).setSpreadModifierHorizontal(0.6F).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "barrel_full_choke"));
        
        registry.register(new Underbarrel(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.IRON_INGOT, 3)).setDriftModifierShiftStill(0.25F).setInaccuracyModifierShiftStill(0.25F).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "underbarrel_bipod"));
        registry.register(new Underbarrel(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.LEATHER)).setInaccuracyModifierMoving(0.75F).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "underbarrel_ergo_grip"));
        registry.register(new Underbarrel(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.LEATHER)).setInaccuracyModifierStill(0.75F).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "underbarrel_angled_grip"));
        registry.register(new Underbarrel(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.LEATHER)).setDriftModifier(0.75F).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "underbarrel_stubby_grip"));
        registry.register(new Underbarrel(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.LEATHER)).setInaccuracyModifierMoving(0.75F).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "underbarrel_vertical_grip"));
        registry.register(new Underbarrel(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.LEATHER)).setInaccuracyModifierStill(0.75F).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "underbarrel_folding_grip"));
        registry.register(new Underbarrel(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.LEATHER)).setDriftModifier(0.75F).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "underbarrel_potato_grip"));
        
        registry.register(new Auxiliary(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.QUARTZ), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.GOLD_INGOT)).setIsAllowingReloadWhileZoomed(true).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "auxiliary_straight_pull"));
        registry.register(new Auxiliary(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.QUARTZ), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.GOLD_INGOT)).setExtraFireRate(1).setForceFullAuto(true).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "auxiliary_rapid_fire"));
        
        registry.register(new Ammo(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS)).setReplacementBullet(() -> GunCusItems.BULLET_12G_DART).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "ammo_12g_dart"));
        registry.register(new Ammo(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS)).setReplacementBullet(() -> GunCusItems.BULLET_12G_FRAG).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "ammo_12g_frag"));
        registry.register(new Ammo(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS)).setReplacementBullet(() -> GunCusItems.BULLET_12G_SLUG).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "ammo_12g_slug"));
        registry.register(new Ammo(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.GOLD_INGOT, 2), new ItemStack(Items.IRON_INGOT, 2)).setReplacementBullet(() -> GunCusItems.BULLET_7_62x51MM).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "ammo_7_62x51mm"));
        
        registry.register(new Magazine(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.IRON_INGOT, 4), new ItemStack(Items.STRING)).setReloadTimeModifier(0.8F).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "magazine_quick_switch"));
        registry.register(new Magazine(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.IRON_INGOT, 2), new ItemStack(Items.SLIME_BALL)).setExtraCapacity(10).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "magazine_extended_10"));
        registry.register(new Magazine(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.IRON_INGOT, 2), new ItemStack(Items.SLIME_BALL)).setExtraCapacity(5).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "magazine_extended_5"));
        
        registry.register(new Paint(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.BLACK_DYE)).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "paint_black"));
        registry.register(new Paint(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.BLUE_DYE)).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "paint_blue"));
        registry.register(new Paint(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.BROWN_DYE)).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "paint_brown"));
        registry.register(new Paint(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.CYAN_DYE)).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "paint_cyan"));
        registry.register(new Paint(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.GRAY_DYE)).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "paint_gray"));
        registry.register(new Paint(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.GREEN_DYE)).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "paint_green"));
        registry.register(new Paint(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.LIGHT_BLUE_DYE)).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "paint_light_blue"));
        registry.register(new Paint(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.LIGHT_GRAY_DYE)).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "paint_light_gray"));
        registry.register(new Paint(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.LIME_DYE)).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "paint_lime"));
        registry.register(new Paint(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.MAGENTA_DYE)).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "paint_magenta"));
        registry.register(new Paint(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.ORANGE_DYE)).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "paint_orange"));
        registry.register(new Paint(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.PINK_DYE)).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "paint_pink"));
        registry.register(new Paint(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.PURPLE_DYE)).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "paint_purple"));
        registry.register(new Paint(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.RED_DYE)).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "paint_red"));
        registry.register(new Paint(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.WHITE_DYE)).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "paint_white"));
        registry.register(new Paint(new Properties().group(GunCus.ITEM_GROUP_GUN_CUS), new ItemStack(Items.YELLOW_DYE)).setDefaultTradeable().setRegistryName(GunCus.MOD_ID, "paint_yellow"));
    }
    
    public static AttachmentItem[][] attachments(AttachmentItem[][] base, AttachmentItem... attachmentItems)
    {
        AttachmentItem[] row;
        AttachmentItem[] newRow;
        
        for(AttachmentItem a : attachmentItems)
        {
            row = base[a.getType().getSlot()];
            newRow = new AttachmentItem[row.length + 1];
            System.arraycopy(row, 0, newRow, 0, row.length);
            newRow[row.length] = a;
            base[a.getType().getSlot()] = newRow;
        }
        
        return base;
    }
    
    public static AttachmentItem[][] assaultRifleAttachments()
    {
        return new AttachmentItem[][] {
            {
                EnumAttachmentType.OPTIC.getDefault(),
                GunCusItems.OPTIC_REFLEX,
                GunCusItems.OPTIC_COYOTE,
                GunCusItems.OPTIC_KOBRA,
                GunCusItems.OPTIC_HOLO,
                GunCusItems.OPTIC_HD33,
                GunCusItems.OPTIC_PKAS,
                GunCusItems.OPTIC_M145,
                GunCusItems.OPTIC_PRISMA,
                GunCusItems.OPTIC_PKA,
                GunCusItems.OPTIC_ACOG,
                GunCusItems.OPTIC_JGM4,
                GunCusItems.OPTIC_PSO1,
            },
            {
                EnumAttachmentType.ACCESSORY.getDefault(),
                GunCusItems.ACCESSORY_MAGNIFIER,
                GunCusItems.ACCESSORY_LASER_SIGHT,
                GunCusItems.ACCESSORY_TRI_BEAM_LASER,
                GunCusItems.ACCESSORY_GREEN_LASER_SIGHT,
            },
            {
                EnumAttachmentType.BARREL.getDefault(),
                GunCusItems.BARREL_HEAVY_BARREL,
                GunCusItems.BARREL_SUPPRESSOR,
                GunCusItems.BARREL_LS06_SUPPRESSOR,
                GunCusItems.BARREL_PBS4_SUPPRESSOR,
                GunCusItems.BARREL_R2_SUPPRESSOR,
                GunCusItems.BARREL_FLASH_HIDER,
                GunCusItems.BARREL_COMPENSATOR,
                GunCusItems.BARREL_MUZZLE_BRAKE,
            },
            {
                EnumAttachmentType.UNDERBARREL.getDefault(),
                GunCusItems.UNDERBARREL_ERGO_GRIP,
                GunCusItems.UNDERBARREL_ANGLED_GRIP,
                GunCusItems.UNDERBARREL_STUBBY_GRIP,
                GunCusItems.UNDERBARREL_VERTICAL_GRIP,
                GunCusItems.UNDERBARREL_FOLDING_GRIP,
                GunCusItems.UNDERBARREL_POTATO_GRIP,
            },
            {
                EnumAttachmentType.AUXILIARY.getDefault(),
            },
            {
                EnumAttachmentType.AMMO.getDefault(),
            },
            {
                EnumAttachmentType.MAGAZINE.getDefault(),
                GunCusItems.MAGAZINE_QUICK_SWITCH,
                GunCusItems.MAGAZINE_EXTENDED_10,
            },
            {
                EnumAttachmentType.PAINT.getDefault(),
                GunCusItems.PAINT_BLACK,
                GunCusItems.PAINT_BLUE,
                GunCusItems.PAINT_BROWN,
                GunCusItems.PAINT_CYAN,
                GunCusItems.PAINT_GRAY,
                GunCusItems.PAINT_GREEN,
                GunCusItems.PAINT_LIGHT_BLUE,
                GunCusItems.PAINT_LIGHT_GRAY,
                GunCusItems.PAINT_LIME,
                GunCusItems.PAINT_MAGENTA,
                GunCusItems.PAINT_ORANGE,
                GunCusItems.PAINT_PINK,
                GunCusItems.PAINT_PURPLE,
                GunCusItems.PAINT_RED,
                GunCusItems.PAINT_WHITE,
                GunCusItems.PAINT_YELLOW,
            }
        };
    }
    
    public static AttachmentItem[][] assaultRifleLongAttachments()
    {
        return new AttachmentItem[][] {
            {
                EnumAttachmentType.OPTIC.getDefault(),
                GunCusItems.OPTIC_REFLEX,
                GunCusItems.OPTIC_COYOTE,
                GunCusItems.OPTIC_KOBRA,
                GunCusItems.OPTIC_HOLO,
                GunCusItems.OPTIC_HD33,
                GunCusItems.OPTIC_PKAS,
                GunCusItems.OPTIC_M145,
                GunCusItems.OPTIC_PRISMA,
                GunCusItems.OPTIC_PKA,
                GunCusItems.OPTIC_ACOG,
                GunCusItems.OPTIC_JGM4,
                GunCusItems.OPTIC_PSO1,
                GunCusItems.OPTIC_CL6X,
                GunCusItems.OPTIC_PKS07,
                GunCusItems.OPTIC_RIFLE,
                GunCusItems.OPTIC_HUNTER,
                GunCusItems.OPTIC_BALLISTIC,
            },
            {
                EnumAttachmentType.ACCESSORY.getDefault(),
                GunCusItems.ACCESSORY_VARIABLE_ZOOM,
                GunCusItems.ACCESSORY_MAGNIFIER,
                GunCusItems.ACCESSORY_LASER_SIGHT,
                GunCusItems.ACCESSORY_TRI_BEAM_LASER,
                GunCusItems.ACCESSORY_GREEN_LASER_SIGHT,
                GunCusItems.ACCESSORY_RANGE_FINDER,
            },
            {
                EnumAttachmentType.BARREL.getDefault(),
                GunCusItems.BARREL_HEAVY_BARREL,
                GunCusItems.BARREL_SUPPRESSOR,
                GunCusItems.BARREL_LS06_SUPPRESSOR,
                GunCusItems.BARREL_PBS4_SUPPRESSOR,
                GunCusItems.BARREL_R2_SUPPRESSOR,
                GunCusItems.BARREL_FLASH_HIDER,
                GunCusItems.BARREL_COMPENSATOR,
                GunCusItems.BARREL_MUZZLE_BRAKE,
            },
            {
                EnumAttachmentType.UNDERBARREL.getDefault(),
                GunCusItems.UNDERBARREL_BIPOD,
                GunCusItems.UNDERBARREL_ERGO_GRIP,
                GunCusItems.UNDERBARREL_ANGLED_GRIP,
                GunCusItems.UNDERBARREL_STUBBY_GRIP,
                GunCusItems.UNDERBARREL_VERTICAL_GRIP,
                GunCusItems.UNDERBARREL_FOLDING_GRIP,
                GunCusItems.UNDERBARREL_POTATO_GRIP,
            },
            {
                EnumAttachmentType.AUXILIARY.getDefault(),
            },
            {
                EnumAttachmentType.AMMO.getDefault(),
            },
            {
                EnumAttachmentType.MAGAZINE.getDefault(),
                GunCusItems.MAGAZINE_QUICK_SWITCH,
                GunCusItems.MAGAZINE_EXTENDED_5,
            },
            {
                EnumAttachmentType.PAINT.getDefault(),
                GunCusItems.PAINT_BLACK,
                GunCusItems.PAINT_BLUE,
                GunCusItems.PAINT_BROWN,
                GunCusItems.PAINT_CYAN,
                GunCusItems.PAINT_GRAY,
                GunCusItems.PAINT_GREEN,
                GunCusItems.PAINT_LIGHT_BLUE,
                GunCusItems.PAINT_LIGHT_GRAY,
                GunCusItems.PAINT_LIME,
                GunCusItems.PAINT_MAGENTA,
                GunCusItems.PAINT_ORANGE,
                GunCusItems.PAINT_PINK,
                GunCusItems.PAINT_PURPLE,
                GunCusItems.PAINT_RED,
                GunCusItems.PAINT_WHITE,
                GunCusItems.PAINT_YELLOW,
            }
        };
    }
    
    public static AttachmentItem[][] sniperRifleAttachments()
    {
        return new AttachmentItem[][] {
            {
                EnumAttachmentType.OPTIC.getDefault(),
                GunCusItems.OPTIC_REFLEX,
                GunCusItems.OPTIC_COYOTE,
                GunCusItems.OPTIC_KOBRA,
                GunCusItems.OPTIC_HOLO,
                GunCusItems.OPTIC_HD33,
                GunCusItems.OPTIC_PKAS,
                GunCusItems.OPTIC_M145,
                GunCusItems.OPTIC_PRISMA,
                GunCusItems.OPTIC_PKA,
                GunCusItems.OPTIC_ACOG,
                GunCusItems.OPTIC_JGM4,
                GunCusItems.OPTIC_PSO1,
                GunCusItems.OPTIC_CL6X,
                GunCusItems.OPTIC_PKS07,
                GunCusItems.OPTIC_RIFLE,
                GunCusItems.OPTIC_HUNTER,
                GunCusItems.OPTIC_BALLISTIC,
            },
            {
                EnumAttachmentType.ACCESSORY.getDefault(),
                GunCusItems.ACCESSORY_VARIABLE_ZOOM,
                GunCusItems.ACCESSORY_MAGNIFIER,
                GunCusItems.ACCESSORY_LASER_SIGHT,
                GunCusItems.ACCESSORY_TRI_BEAM_LASER,
                GunCusItems.ACCESSORY_GREEN_LASER_SIGHT,
                GunCusItems.ACCESSORY_RANGE_FINDER,
            },
            {
                EnumAttachmentType.BARREL.getDefault(),
                GunCusItems.BARREL_HEAVY_BARREL,
                GunCusItems.BARREL_SUPPRESSOR,
                GunCusItems.BARREL_LS06_SUPPRESSOR,
                GunCusItems.BARREL_PBS4_SUPPRESSOR,
                GunCusItems.BARREL_R2_SUPPRESSOR,
                GunCusItems.BARREL_FLASH_HIDER,
                GunCusItems.BARREL_COMPENSATOR,
                GunCusItems.BARREL_MUZZLE_BRAKE,
            },
            {
                EnumAttachmentType.UNDERBARREL.getDefault(),
                GunCusItems.UNDERBARREL_BIPOD,
            },
            {
                EnumAttachmentType.AUXILIARY.getDefault(),
                GunCusItems.AUXILIARY_STRAIGHT_PULL,
            
            },
            {
                EnumAttachmentType.AMMO.getDefault(),
            },
            {
                EnumAttachmentType.MAGAZINE.getDefault(),
                GunCusItems.MAGAZINE_QUICK_SWITCH,
                GunCusItems.MAGAZINE_EXTENDED_5,
            },
            {
                EnumAttachmentType.PAINT.getDefault(),
                GunCusItems.PAINT_BLACK,
                GunCusItems.PAINT_BLUE,
                GunCusItems.PAINT_BROWN,
                GunCusItems.PAINT_CYAN,
                GunCusItems.PAINT_GRAY,
                GunCusItems.PAINT_GREEN,
                GunCusItems.PAINT_LIGHT_BLUE,
                GunCusItems.PAINT_LIGHT_GRAY,
                GunCusItems.PAINT_LIME,
                GunCusItems.PAINT_MAGENTA,
                GunCusItems.PAINT_ORANGE,
                GunCusItems.PAINT_PINK,
                GunCusItems.PAINT_PURPLE,
                GunCusItems.PAINT_RED,
                GunCusItems.PAINT_WHITE,
                GunCusItems.PAINT_YELLOW,
            }
        };
    }
    
    public static AttachmentItem[][] shotgunAttachments()
    {
        return new AttachmentItem[][] {
            {
                EnumAttachmentType.OPTIC.getDefault(),
                GunCusItems.OPTIC_REFLEX,
                GunCusItems.OPTIC_COYOTE,
                GunCusItems.OPTIC_KOBRA,
                GunCusItems.OPTIC_HOLO,
                GunCusItems.OPTIC_HD33,
                GunCusItems.OPTIC_PKAS,
                GunCusItems.OPTIC_M145,
                GunCusItems.OPTIC_PRISMA,
                GunCusItems.OPTIC_PKA,
                GunCusItems.OPTIC_ACOG,
                GunCusItems.OPTIC_JGM4,
                GunCusItems.OPTIC_PSO1,
            },
            {
                EnumAttachmentType.ACCESSORY.getDefault(),
                GunCusItems.ACCESSORY_MAGNIFIER,
                GunCusItems.ACCESSORY_LASER_SIGHT,
                GunCusItems.ACCESSORY_TRI_BEAM_LASER,
                GunCusItems.ACCESSORY_GREEN_LASER_SIGHT,
            },
            {
                EnumAttachmentType.BARREL.getDefault(),
                GunCusItems.BARREL_DUCKBILL,
                GunCusItems.BARREL_MODIFIED_CHOKE,
                GunCusItems.BARREL_FULL_CHOKE,
            },
            {
                EnumAttachmentType.UNDERBARREL.getDefault(),
            },
            {
                EnumAttachmentType.AUXILIARY.getDefault(),
            },
            {
                EnumAttachmentType.AMMO.getDefault(),
                GunCusItems.AMMO_12G_DART,
                GunCusItems.AMMO_12G_FRAG,
                GunCusItems.AMMO_12G_SLUG
            },
            {
                EnumAttachmentType.MAGAZINE.getDefault(),
            },
            {
                EnumAttachmentType.PAINT.getDefault(),
                GunCusItems.PAINT_BLACK,
                GunCusItems.PAINT_BLUE,
                GunCusItems.PAINT_BROWN,
                GunCusItems.PAINT_CYAN,
                GunCusItems.PAINT_GRAY,
                GunCusItems.PAINT_GREEN,
                GunCusItems.PAINT_LIGHT_BLUE,
                GunCusItems.PAINT_LIGHT_GRAY,
                GunCusItems.PAINT_LIME,
                GunCusItems.PAINT_MAGENTA,
                GunCusItems.PAINT_ORANGE,
                GunCusItems.PAINT_PINK,
                GunCusItems.PAINT_PURPLE,
                GunCusItems.PAINT_RED,
                GunCusItems.PAINT_WHITE,
                GunCusItems.PAINT_YELLOW,
            }
        };
    }
    
    public static AttachmentItem[][] pistolAttachments()
    {
        return new AttachmentItem[][] {
            {
                EnumAttachmentType.OPTIC.getDefault(),
                GunCusItems.OPTIC_REFLEX,
                GunCusItems.OPTIC_COYOTE,
                GunCusItems.OPTIC_KOBRA,
                GunCusItems.OPTIC_HOLO,
                GunCusItems.OPTIC_HD33,
                GunCusItems.OPTIC_PKAS,
            },
            {
                EnumAttachmentType.ACCESSORY.getDefault(),
                GunCusItems.ACCESSORY_LASER_SIGHT,
                GunCusItems.ACCESSORY_TRI_BEAM_LASER,
                GunCusItems.ACCESSORY_GREEN_LASER_SIGHT,
            },
            {
                EnumAttachmentType.BARREL.getDefault(),
                GunCusItems.BARREL_HEAVY_BARREL,
                GunCusItems.BARREL_SUPPRESSOR,
                GunCusItems.BARREL_LS06_SUPPRESSOR,
                GunCusItems.BARREL_PBS4_SUPPRESSOR,
                GunCusItems.BARREL_R2_SUPPRESSOR,
                GunCusItems.BARREL_FLASH_HIDER,
                GunCusItems.BARREL_COMPENSATOR,
                GunCusItems.BARREL_MUZZLE_BRAKE,
            },
            {
                EnumAttachmentType.UNDERBARREL.getDefault(),
            },
            {
                EnumAttachmentType.AUXILIARY.getDefault(),
            },
            {
                EnumAttachmentType.AMMO.getDefault(),
            },
            {
                EnumAttachmentType.MAGAZINE.getDefault(),
                GunCusItems.MAGAZINE_EXTENDED_10
            },
            {
                EnumAttachmentType.PAINT.getDefault(),
                GunCusItems.PAINT_BLACK,
                GunCusItems.PAINT_BLUE,
                GunCusItems.PAINT_BROWN,
                GunCusItems.PAINT_CYAN,
                GunCusItems.PAINT_GRAY,
                GunCusItems.PAINT_GREEN,
                GunCusItems.PAINT_LIGHT_BLUE,
                GunCusItems.PAINT_LIGHT_GRAY,
                GunCusItems.PAINT_LIME,
                GunCusItems.PAINT_MAGENTA,
                GunCusItems.PAINT_ORANGE,
                GunCusItems.PAINT_PINK,
                GunCusItems.PAINT_PURPLE,
                GunCusItems.PAINT_RED,
                GunCusItems.PAINT_WHITE,
                GunCusItems.PAINT_YELLOW,
            }
        };
    }
    
    public static AttachmentItem[][] colorAttachments()
    {
        return new AttachmentItem[][] {
            {
                EnumAttachmentType.OPTIC.getDefault(),
            },
            {
                EnumAttachmentType.ACCESSORY.getDefault(),
            },
            {
                EnumAttachmentType.BARREL.getDefault(),
            },
            {
                EnumAttachmentType.UNDERBARREL.getDefault(),
            },
            {
                EnumAttachmentType.AUXILIARY.getDefault(),
            },
            {
                EnumAttachmentType.AMMO.getDefault(),
            },
            {
                EnumAttachmentType.MAGAZINE.getDefault(),
            },
            {
                EnumAttachmentType.PAINT.getDefault(),
                GunCusItems.PAINT_BLACK,
                GunCusItems.PAINT_BLUE,
                GunCusItems.PAINT_BROWN,
                GunCusItems.PAINT_CYAN,
                GunCusItems.PAINT_GRAY,
                GunCusItems.PAINT_GREEN,
                GunCusItems.PAINT_LIGHT_BLUE,
                GunCusItems.PAINT_LIGHT_GRAY,
                GunCusItems.PAINT_LIME,
                GunCusItems.PAINT_MAGENTA,
                GunCusItems.PAINT_ORANGE,
                GunCusItems.PAINT_PINK,
                GunCusItems.PAINT_PURPLE,
                GunCusItems.PAINT_RED,
                GunCusItems.PAINT_WHITE,
                GunCusItems.PAINT_YELLOW,
            }
        };
    }
    
    public static AttachmentItem[][] defaultAttachments()
    {
        return new AttachmentItem[][] {
            {
                EnumAttachmentType.OPTIC.getDefault(),
            },
            {
                EnumAttachmentType.ACCESSORY.getDefault(),
            },
            {
                EnumAttachmentType.BARREL.getDefault(),
            },
            {
                EnumAttachmentType.UNDERBARREL.getDefault(),
            },
            {
                EnumAttachmentType.AUXILIARY.getDefault(),
            },
            {
                EnumAttachmentType.AMMO.getDefault(),
            },
            {
                EnumAttachmentType.MAGAZINE.getDefault(),
            },
            {
                EnumAttachmentType.PAINT.getDefault(),
            }
        };
    }
}
