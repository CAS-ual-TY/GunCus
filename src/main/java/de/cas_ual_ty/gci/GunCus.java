package de.cas_ual_ty.gci;

import java.util.Random;

import de.cas_ual_ty.gci.block.BlockGCI;
import de.cas_ual_ty.gci.block.BlockGunTable;
import de.cas_ual_ty.gci.item.ItemBlockGCI;
import de.cas_ual_ty.gci.item.ItemCartridge;
import de.cas_ual_ty.gci.item.ItemGun;
import de.cas_ual_ty.gci.item.attachment.Accessory;
import de.cas_ual_ty.gci.item.attachment.Accessory.Laser;
import de.cas_ual_ty.gci.item.attachment.Ammo;
import de.cas_ual_ty.gci.item.attachment.Attachment;
import de.cas_ual_ty.gci.item.attachment.Auxiliary;
import de.cas_ual_ty.gci.item.attachment.Barrel;
import de.cas_ual_ty.gci.item.attachment.Magazine;
import de.cas_ual_ty.gci.item.attachment.Optic;
import de.cas_ual_ty.gci.item.attachment.Paint;
import de.cas_ual_ty.gci.item.attachment.Underbarrel;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.registries.IForgeRegistry;

@Mod(modid = GunCus.MOD_ID, version = GunCus.MOD_VERSION, name = GunCus.MOD_NAME)
public class GunCus
{
	public static final String MOD_ID = "gci";
	public static final String MOD_NAME = "Gun Customization: Infinity";
	public static final String MOD_VERSION = "0.5-1.12.2";
	
	public static final Random RANDOM = new Random();
	
	public static final CreativeTabsGCI TAB_GUNCUS = new CreativeTabsGCI(GunCus.MOD_ID);
	
	public static final SoundEventGCI SOUND_SHOOT 			= new SoundEventGCI(0, "shoot");
	public static final SoundEventGCI SOUND_SHOOT_SILENCED 	= new SoundEventGCI(1, "shoot_silenced");
	public static final SoundEventGCI SOUND_SHOOT_SNIPER 	= new SoundEventGCI(2, "shoot_sniper");
	public static final SoundEventGCI SOUND_RELOAD 			= new SoundEventGCI(3, "reload");
	
	public static final BlockGCI BLOCK_GUN_TABLE = new BlockGunTable("gun_table");
	
	public static final ItemCartridge CARTRIDGE_5_56x45mm 		= new ItemCartridge("cartridge_5_56x45mm").setDamage(5F);
	public static final ItemCartridge CARTRIDGE_5_45x39mm 		= new ItemCartridge("cartridge_5_45x39mm").setDamage(5F);
	public static final ItemCartridge CARTRIDGE_12G_BUCKSHOT 	= new ItemCartridge("cartridge_12g_buckshot").setDamage(1.5F).setProjectileAmmount(6);
	public static final ItemCartridge CARTRIDGE_12G_DART 		= new ItemCartridge("cartridge_12g_dart").setDamage(1.5F).setProjectileAmmount(6);
	public static final ItemCartridge CARTRIDGE_12G_FRAG 		= new ItemCartridge("cartridge_12g_frag").setDamage(5F);
	public static final ItemCartridge CARTRIDGE_12G_SLUG 		= new ItemCartridge("cartridge_12g_slug").setDamage(5F);
	public static final ItemCartridge CARTRIDGE__44_magnum 		= new ItemCartridge("cartridge__44_magnum").setDamage(8F);
	public static final ItemCartridge CARTRIDGE__338_magnum 	= new ItemCartridge("cartridge__338_magnum").setDamage(8F);
	
	public static final Optic 		OPTIC_DEFAULT 				= new Optic(0, "optic_default");
	public static final Accessory 	ACCESSORY_DEFAULT 			= new Accessory(0, "accessory_default");
	public static final Barrel 		BARREL_DEFAULT 				= new Barrel(0, "barrel_default");
	public static final Underbarrel UNDERBARREL_DEFAULT 		= new Underbarrel(0, "underbarrel_default");
	public static final Auxiliary 	AUXILIARY_DEFAULT 			= new Auxiliary(0, "auxiliary_default");
	public static final Ammo 		AMMO_DEFAULT 				= new Ammo(0, "ammo_default");
	public static final Magazine 	MAGAZINE_DEFAULT 			= new Magazine(0, "magazine_default");
	public static final Paint 		PAINT_DEFAULT 				= new Paint(0, "paint_default");
	
	public static final Optic 		OPTIC_REFLEX 				= new Optic(1, "optic_reflex");
	public static final Optic 		OPTIC_COYOTE 				= new Optic(2, "optic_coyote");
	public static final Optic 		OPTIC_KOBRA 				= new Optic(3, "optic_kobra");
	public static final Optic 		OPTIC_HOLO 					= new Optic(4, "optic_holo");
	public static final Optic 		OPTIC_HD33 					= new Optic(5, "optic_hd33");
	public static final Optic 		OPTIC_PKAS 					= new Optic(6, "optic_pkas");
	//	public static final Optic 		OPTIC_IRNV 					= new Optic(7, "optic_irnv").setOpticType(EnumOpticType.NIGHT_VISION);
	//	public static final Optic 		OPTIC_FLIR 					= new Optic(8, "optic_flir").setZoom(2F).setOpticType(EnumOpticType.THERMAL);
	public static final Optic 		OPTIC_M145 					= new Optic(9, "optic_m145").setZoom(3.4F);
	public static final Optic 		OPTIC_PRISMA 				= new Optic(10, "optic_prisma").setZoom(3.4F);
	public static final Optic 		OPTIC_PKA 					= new Optic(11, "optic_pka").setZoom(3.4F);
	public static final Optic 		OPTIC_ACOG 					= new Optic(12, "optic_acog").setZoom(4F);
	public static final Optic 		OPTIC_JGM4 					= new Optic(13, "optic_jgm4").setZoom(4F);
	public static final Optic 		OPTIC_PSO1 					= new Optic(14, "optic_pso1").setZoom(4F);
	public static final Optic 		OPTIC_CL6X 					= new Optic(15, "optic_cl6x").setZoom(6F);
	public static final Optic 		OPTIC_PKS07 				= new Optic(16, "optic_pks07").setZoom(7F);
	public static final Optic 		OPTIC_RIFLE 				= new Optic(17, "optic_rifle").setZoom(8F);
	public static final Optic 		OPTIC_HUNTER 				= new Optic(18, "optic_hunter").setZoom(20F);
	public static final Optic 		OPTIC_BALLISTIC 			= new Optic(19, "optic_ballistic").setZoom(40F);
	
	public static final Accessory 	ACCESSORY_VARIABLE_ZOOM 	= new Accessory(1, "accessory_variable_zoom").setExtraZoom(14F);
	public static final Accessory 	ACCESSORY_MAGNIFIER 		= new Accessory(2, "accessory_magnifier").setZoomModifier(1.5F);
	// ACCESSORY_FLASH_LIGHT
	// ACCESSORY_TACTICAL LIGHT
	public static final Accessory 	ACCESSORY_LASER_SIGHT 		= new Accessory(5, "accessory_laser_sight").setLaser(new Laser(1F, 0F, 0F, 50D, true, true, false));
	public static final Accessory 	ACCESSORY_TRI_BEAM_LASER 	= new Accessory(6, "accessory_tri_beam_laser").setLaser(new Laser(1F, 0F, 0F, 80D, true, true, false));
	public static final Accessory 	ACCESSORY_GREEN_LASER_SIGHT = new Accessory(7, "accessory_green_laser_sight").setLaser(new Laser(0F, 1F, 0F, 50D, true, true, false));
	// ACCESSORY_LASER_LIGHT_COMBO
	public static final Accessory 	ACCESSORY_RANGE_FINDER 		= new Accessory(9, "accessory_range_finder").setLaser(new Laser(0F, 0.5F, 1F, 125D, false, true, true));
	
	public static final Barrel 		BARREL_HEAVY_BARREL 		= new Barrel(1, "barrel_heavy_barrel").setExtraDamage(1.0F);
	public static final Barrel 		BARREL_SUPPRESSOR 			= new Barrel(2, "barrel_suppressor").setIsSilenced(true).setIsFlashHider(true).setBulletSpeedModifier(0.7F);
	public static final Barrel 		BARREL_LS06_SUPPRESSOR 		= new Barrel(3, "barrel_ls06_suppressor").setIsSilenced(true).setIsFlashHider(true).setBulletSpeedModifier(0.7F);
	public static final Barrel 		BARREL_PBS4_SUPPRESSOR 		= new Barrel(4, "barrel_pbs4_suppressor").setIsSilenced(true).setIsFlashHider(true).setBulletSpeedModifier(0.7F);
	public static final Barrel 		BARREL_R2_SUPPRESSOR 		= new Barrel(5, "barrel_r2_suppressor").setIsSilenced(true).setIsFlashHider(true).setBulletSpeedModifier(0.7F);
	public static final Barrel 		BARREL_FLASH_HIDER 			= new Barrel(6, "barrel_flash_hider").setIsFlashHider(true);
	public static final Barrel 		BARREL_COMPENSATOR 			= new Barrel(7, "barrel_compensator").setDriftModifier(1.25F).setInaccuracyModifier(1.2F);
	public static final Barrel 		BARREL_MUZZLE_BRAKE 		= new Barrel(8, "barrel_muzzle_brake").setDriftModifier(1.25F).setInaccuracyModifier(1.2F);
	public static final Barrel 		BARREL_DUCKBILL 			= new Barrel(9, "barrel_duckbill").setVerticalSpreadModifier(0.4F);
	public static final Barrel 		BARREL_MODIFIED_CHOKE 		= new Barrel(10, "barrel_modified_choke").setVerticalSpreadModifier(0.8F).setHorizontalSpreadModifier(0.8F);
	public static final Barrel 		BARREL_FULL_CHOKE 			= new Barrel(11, "barrel_full_choke").setVerticalSpreadModifier(0.6F).setHorizontalSpreadModifier(0.6F);
	
	public static final Underbarrel UNDERBARREL_ERGO_GRIP 		= new Underbarrel(1, "underbarrel_ergo_grip").setInaccuracyModifierMoving(0.75F);
	public static final Underbarrel UNDERBARREL_ANGLED_GRIP 	= new Underbarrel(2, "underbarrel_angled_grip").setInaccuracyModifierStill(0.75F);
	public static final Underbarrel UNDERBARREL_STUBBY_GRIP 	= new Underbarrel(3, "underbarrel_stubby_grip").setDriftModifier(0.75F);
	public static final Underbarrel UNDERBARREL_VERTICAL_GRIP 	= new Underbarrel(4, "underbarrel_vertical_grip").setInaccuracyModifierMoving(0.75F);
	public static final Underbarrel UNDERBARREL_FOLDING_GRIP 	= new Underbarrel(5, "underbarrel_folding_grip").setInaccuracyModifierStill(0.75F);
	public static final Underbarrel UNDERBARREL_POTATO_GRIP 	= new Underbarrel(6, "underbarrel_potato_grip").setDriftModifier(0.75F);
	
	public static final Auxiliary 	AUXILIARY_BIPOD 			= new Auxiliary(1, "auxiliary_bipod").setDriftModifierWhenShiftAndStill(0.25F).setInaccuracyModifierWhenShiftAndStill(0.25F);
	public static final Auxiliary 	AUXILIARY_STRAIGHT_PULL 	= new Auxiliary(2, "auxiliary_straight_pull").setIsAllowingReloadWhileZoomed(true);
	
	public static final Ammo 		AMMO_12G_BUCKSHOT 			= new Ammo(1, "ammo_12g_buckshot").setReplacementCartridge(GunCus.CARTRIDGE_12G_BUCKSHOT);
	public static final Ammo 		AMMO_12G_DART 				= new Ammo(2, "ammo_12g_dart").setReplacementCartridge(GunCus.CARTRIDGE_12G_DART);
	public static final Ammo 		AMMO_12G_FRAG 				= new Ammo(3, "ammo_12g_frag").setReplacementCartridge(GunCus.CARTRIDGE_12G_FRAG);
	public static final Ammo 		AMMO_12G_SLUG 				= new Ammo(4, "ammo_12g_slug").setReplacementCartridge(GunCus.CARTRIDGE_12G_SLUG);
	
	public static final Magazine 	MAGAZINE_QUICK_SWITCH 		= new Magazine(1, "magazine_quick_switch").setReloadTimeModifier(0.8F);
	public static final Magazine 	MAGAZINE_EXTENDED_10 		= new Magazine(2, "magazine_extended_10").setExtraCapacity(10);
	
	public static final Paint 		PAINT_BLACK 				= new Paint(1, "paint_black");
	public static final Paint 		PAINT_BLUE 					= new Paint(2, "paint_blue");
	public static final Paint 		PAINT_GREEN 				= new Paint(3, "paint_green");
	public static final Paint 		PAINT_ORANGE 				= new Paint(4, "paint_orange");
	public static final Paint 		PAINT_PINK 					= new Paint(5, "paint_pink");
	public static final Paint 		PAINT_RED 					= new Paint(6, "paint_red");
	public static final Paint 		PAINT_TURQUOISE 			= new Paint(7, "paint_turquoise");
	public static final Paint 		PAINT_WHITE 				= new Paint(8, "paint_white");
	public static final Paint 		PAINT_YELLOW 				= new Paint(9, "paint_yellow");
	
	public static final ItemGun GUN_AK_74M 		= GunCus.createAssaultRifle("gun_ak_74m", 3, 30, 5F, GunCus.CARTRIDGE_5_45x39mm);
	public static final ItemGun GUN_M16A4 		= GunCus.createAssaultRifle("gun_m16a4", 3, 30, 5F, GunCus.CARTRIDGE_5_56x45mm);
	
	@SidedProxy(clientSide = "de.cas_ual_ty.gci.client.ProxyClient", serverSide = "de.cas_ual_ty.gci.Proxy")
	public static Proxy proxy;
	
	public static SimpleNetworkWrapper channel;
	
	@Instance
	public static GunCus instance;
	
	public static boolean fullCreativeTabs = false;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		GunCus.proxy.preInit(event);
		
		Configuration config = new Configuration(event.getModConfigurationDirectory());
		GunCus.fullCreativeTabs = config.getBoolean("fullCreativeTabs", "general", false, "If true, all guns come with their own creative tab containing all possible variants. Keep this off if you have JEI or similar mods installed.");
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		GunCus.proxy.init(event);
	}
	
	public static ItemGun createAssaultRifle(String rl, int fireRate, int maxAmmo, float damage, ItemCartridge cartridge)
	{
		return new ItemGun(rl, fireRate, maxAmmo, damage, cartridge)
				
				.addAttachment(GunCus.OPTIC_REFLEX)
				.addAttachment(GunCus.OPTIC_COYOTE)
				.addAttachment(GunCus.OPTIC_KOBRA)
				.addAttachment(GunCus.OPTIC_HOLO)
				.addAttachment(GunCus.OPTIC_HD33)
				.addAttachment(GunCus.OPTIC_PKAS)
				//		.addAttachment(GunCus.OPTIC_IRNV)
				//		.addAttachment(GunCus.OPTIC_FLIR)
				.addAttachment(GunCus.OPTIC_M145)
				.addAttachment(GunCus.OPTIC_PRISMA)
				.addAttachment(GunCus.OPTIC_PKA)
				.addAttachment(GunCus.OPTIC_ACOG)
				.addAttachment(GunCus.OPTIC_JGM4)
				.addAttachment(GunCus.OPTIC_PSO1)
				
				.addAttachment(GunCus.ACCESSORY_MAGNIFIER)
				//		.addAttachment(GunCus.ACCESSORY_FLASH_LIGHT)
				//		.addAttachment(GunCus.ACCESSORY_TACTICAL_LIGHT)
				.addAttachment(GunCus.ACCESSORY_LASER_SIGHT)
				.addAttachment(GunCus.ACCESSORY_TRI_BEAM_LASER)
				.addAttachment(GunCus.ACCESSORY_GREEN_LASER_SIGHT)
				//		.addAttachment(GunCus.ACCESSORY_LASER_LIGHT_COMBO)
				
				.addAttachment(GunCus.BARREL_HEAVY_BARREL)
				.addAttachment(GunCus.BARREL_SUPPRESSOR)
				.addAttachment(GunCus.BARREL_LS06_SUPPRESSOR)
				.addAttachment(GunCus.BARREL_PBS4_SUPPRESSOR)
				.addAttachment(GunCus.BARREL_R2_SUPPRESSOR)
				.addAttachment(GunCus.BARREL_FLASH_HIDER)
				.addAttachment(GunCus.BARREL_COMPENSATOR)
				.addAttachment(GunCus.BARREL_MUZZLE_BRAKE)
				
				.addAttachment(GunCus.UNDERBARREL_ERGO_GRIP)
				.addAttachment(GunCus.UNDERBARREL_ANGLED_GRIP)
				.addAttachment(GunCus.UNDERBARREL_STUBBY_GRIP)
				.addAttachment(GunCus.UNDERBARREL_VERTICAL_GRIP)
				.addAttachment(GunCus.UNDERBARREL_FOLDING_GRIP)
				.addAttachment(GunCus.UNDERBARREL_POTATO_GRIP)
				
				.addAttachment(GunCus.MAGAZINE_QUICK_SWITCH)
				.addAttachment(GunCus.MAGAZINE_EXTENDED_10)
				
				.addAttachment(GunCus.PAINT_BLACK)
				.addAttachment(GunCus.PAINT_BLUE)
				.addAttachment(GunCus.PAINT_GREEN)
				.addAttachment(GunCus.PAINT_ORANGE)
				.addAttachment(GunCus.PAINT_PINK)
				.addAttachment(GunCus.PAINT_RED)
				.addAttachment(GunCus.PAINT_TURQUOISE)
				.addAttachment(GunCus.PAINT_WHITE)
				.addAttachment(GunCus.PAINT_YELLOW)
				;
	}
	
	@EventBusSubscriber
	public static class Handler
	{
		@SubscribeEvent
		public static void registerItems(RegistryEvent.Register<Item> event)
		{
			IForgeRegistry<Item> registry = event.getRegistry();
			
			for(BlockGCI block : BlockGCI.BLOCKS_LIST)
			{
				GunCus.proxy.registerItem(registry, new ItemBlockGCI(block));
			}
			
			int j;
			Attachment attachment;
			
			for(int i = 0; i < Attachment.ATTACHMENTS_LIST.length; ++i)
			{
				for(j = 1; j < Attachment.getAmmountForSlot(i); ++j)
				{
					attachment = Attachment.getAttachment(i, j);
					
					if(attachment != null && attachment.shouldRegister())
					{
						GunCus.proxy.registerItem(registry, attachment);
					}
				}
			}
			
			for(ItemCartridge cartridge : ItemCartridge.CARTRIDGES_LIST)
			{
				GunCus.proxy.registerItem(registry, cartridge);
			}
			
			for(ItemGun gun : ItemGun.GUNS_LIST)
			{
				GunCus.proxy.registerGun(registry, gun);
			}
		}
		
		@SubscribeEvent
		public static void registerBlocks(RegistryEvent.Register<Block> event)
		{
			IForgeRegistry<Block> registry = event.getRegistry();
			
			for(BlockGCI block : BlockGCI.BLOCKS_LIST)
			{
				GunCus.proxy.registerBlock(registry, block);
			}
		}
	}
}
