package de.cas_ual_ty.gci;

import java.util.Random;

import de.cas_ual_ty.gci.item.ItemCartridge;
import de.cas_ual_ty.gci.item.ItemGun;
import de.cas_ual_ty.gci.item.attachment.Accessory;
import de.cas_ual_ty.gci.item.attachment.Ammo;
import de.cas_ual_ty.gci.item.attachment.Attachment;
import de.cas_ual_ty.gci.item.attachment.Auxiliary;
import de.cas_ual_ty.gci.item.attachment.Barrel;
import de.cas_ual_ty.gci.item.attachment.Magazine;
import de.cas_ual_ty.gci.item.attachment.Optic;
import de.cas_ual_ty.gci.item.attachment.Accessory.Laser;
import de.cas_ual_ty.gci.item.attachment.Paint;
import de.cas_ual_ty.gci.item.attachment.Underbarrel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
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
	public static final String MOD_VERSION = "0.1-1.12.2";
	
	public static final CreativeTabs TAB_BF4 = new CreativeTabsGCI(MOD_ID);
	
	public static final ItemCartridge BULLET = new ItemCartridge("bullet");
	
	public static final ItemCartridge CARTRIDGE_5_56x45mm = new ItemCartridge("cartridge_5_56x45mm").setDamage(5F);
	public static final ItemCartridge CARTRIDGE_5_45x39mm = new ItemCartridge("cartridge_5_45x39mm").setDamage(5F);
	public static final ItemCartridge CARTRIDGE_12G_BUCKSHOT = new ItemCartridge("cartridge_12g_buckshot").setDamage(1.5F).setProjectileAmmount(6);
	public static final ItemCartridge CARTRIDGE_12G_DART = new ItemCartridge("cartridge_12g_dart").setDamage(1.5F).setProjectileAmmount(6);
	public static final ItemCartridge CARTRIDGE_12G_FRAG = new ItemCartridge("cartridge_12g_frag").setDamage(5F);
	public static final ItemCartridge CARTRIDGE_12G_SLUG = new ItemCartridge("cartridge_12g_slug").setDamage(5F);
	
	public static final Optic OPTIC_DEFAULT = new Optic(0, "default");
	public static final Accessory ACCESSORY_DEFAULT = new Accessory(0, "default");
	public static final Barrel BARREL_DEFAULT = new Barrel(0, "default");
	public static final Underbarrel UNDERBARREL_DEFAULT = new Underbarrel(0, "default");
	public static final Auxiliary AUXILIARY_DEFAULT = new Auxiliary(0, "default");
	public static final Ammo AMMO_DEFAULT = new Ammo(0, "default");
	public static final Magazine MAGAZINE_DEFAULT = new Magazine(0, "default");
	public static final Paint PAINT_DEFAULT = new Paint(0, "default");
	
	public static final Optic OPTIC_REFLEX = new Optic(1, "optic_reflex");
	public static final Optic OPTIC_COYOTE = new Optic(2, "optic_coyote");
	public static final Optic OPTIC_KOBRA = new Optic(3, "optic_kobra");
	public static final Optic OPTIC_HOLO = new Optic(4, "optic_holo");
	public static final Optic OPTIC_HD33 = new Optic(5, "optic_hd33");
	public static final Optic OPTIC_PKAS = new Optic(6, "optic_pkas");
//	public static final Optic OPTIC_IRNV = new Optic(7, "optic_irnv").setOpticType(EnumOpticType.NIGHT_VISION);
//	public static final Optic OPTIC_FLIR = new Optic(8, "optic_flir").setZoom(2F).setOpticType(EnumOpticType.THERMAL);
	public static final Optic OPTIC_M145 = new Optic(9, "optic_m145").setZoom(3.4F);
	public static final Optic OPTIC_PRISMA = new Optic(10, "optic_prisma").setZoom(3.4F);
	public static final Optic OPTIC_PKA = new Optic(11, "optic_pka").setZoom(3.4F);
	public static final Optic OPTIC_ACOG = new Optic(12, "optic_acog").setZoom(4F);
	public static final Optic OPTIC_JGM4 = new Optic(13, "optic_jgm4").setZoom(4F);
	public static final Optic OPTIC_PSO1 = new Optic(14, "optic_pso1").setZoom(4F);
	public static final Optic OPTIC_CL6X = new Optic(15, "optic_cl6x").setZoom(6F);
	public static final Optic OPTIC_PKS07 = new Optic(16, "optic_pks07").setZoom(7F);
	public static final Optic OPTIC_RIFLE_SCOPE = new Optic(17, "optic_rifle_scope").setZoom(8F);
	public static final Optic OPTIC_HUNTER = new Optic(18, "optic_hunter").setZoom(20F);
	public static final Optic OPTIC_BALLISTIC = new Optic(19, "optic_ballistic").setZoom(40F);
	
	public static final Accessory ACCESSORY_VARIABLE_ZOOM = new Accessory(1, "accessory_variable_zoom").setExtraZoom(14F);
	public static final Accessory ACCESSORY_MAGNIFIER = new Accessory(2, "accessory_magnifier").setZoomModifier(2F);
	// ACCESSORY_FLASH_LIGHT
	// ACCESSORY_TACTICAL LIGHT
	public static final Accessory ACCESSORY_LASER_SIGHT = new Accessory(5, "accessory_laser_sight").setLaser(new Laser(1F, 0F, 0F, 50D, true, true, false));
	public static final Accessory ACCESSORY_TRI_BEAM_LASER = new Accessory(6, "accessory_tri_beam_laser").setLaser(new Laser(1F, 0F, 0F, 80D, true, true, false));
	public static final Accessory ACCESSORY_GREEN_LASER_SIGHT = new Accessory(7, "accessory_green_laser_sight").setLaser(new Laser(0F, 1F, 0F, 50D, true, true, false));
	// ACCESSORY_LASER_LIGHT_COMBO
	public static final Accessory ACCESSORY_RANGE_FINDER = new Accessory(9, "accessory_range_finder").setLaser(new Laser(0F, 0.5F, 1F, 125D, false, true, true));
	
	public static final Barrel BARREL_HEAVY_BARREL = new Barrel(1, "barrel_heavy_barrel").setExtraDamage(1.0F);
	public static final Barrel BARREL_SUPPRESSOR = new Barrel(2, "barrel_suppressor").setIsSilenced(true).setIsFlashHider(true).setBulletSpeedModifier(0.7F);
	public static final Barrel BARREL_LS06_SUPPRESSOR = new Barrel(3, "barrel_ls06_suppressor").setIsSilenced(true).setIsFlashHider(true).setBulletSpeedModifier(0.7F);
	public static final Barrel BARREL_PBS4_SUPPRESSOR = new Barrel(4, "barrel_pbs4_suppressor").setIsSilenced(true).setIsFlashHider(true).setBulletSpeedModifier(0.7F);
	public static final Barrel BARREL_R2_SUPPRESSOR = new Barrel(5, "barrel_r2_suppressor").setIsSilenced(true).setIsFlashHider(true).setBulletSpeedModifier(0.7F);
	public static final Barrel BARREL_FLASH_HIDER = new Barrel(6, "barrel_flash_hider").setIsFlashHider(true);
	public static final Barrel BARREL_COMPENSATOR = new Barrel(7, "barrel_compensator").setDriftModifier(1.25F).setInaccuracyModifier(1.2F);
	public static final Barrel BARREL_MUZZLE_BRAKE = new Barrel(8, "barrel_muzzle_brake").setDriftModifier(1.25F).setInaccuracyModifier(1.2F);
	public static final Barrel BARREL_DUCKBILL = new Barrel(9, "barrel_duckbill").setVerticalSpreadModifier(0.4F);
	public static final Barrel BARREL_MODIFIED_CHOKE = new Barrel(10, "barrel_modified_choke").setVerticalSpreadModifier(0.8F).setHorizontalSpreadModifier(0.8F);
	public static final Barrel BARREL_FULL_CHOKE = new Barrel(11, "barrel_full_choke").setVerticalSpreadModifier(0.6F).setHorizontalSpreadModifier(0.6F);
	
	public static final Underbarrel UNDERBARREL_ERGO_GRIP = new Underbarrel(1, "underbarrel_ergo_grip").setInaccuracyModifierMoving(0.75F);
	public static final Underbarrel UNDERBARREL_ANGLED_GRIP = new Underbarrel(2, "underbarrel_angled_grip").setInaccuracyModifierStill(0.75F);
	public static final Underbarrel UNDERBARREL_STUBBY_GRIP = new Underbarrel(3, "underbarrel_stubby_grip").setDriftModifier(0.75F);
	public static final Underbarrel UNDERBARREL_VERTICAL_GRIP = new Underbarrel(4, "underbarrel_vertical_grip").setInaccuracyModifierMoving(0.75F);
	public static final Underbarrel UNDERBARREL_FOLDING_GRIP = new Underbarrel(5, "underbarrel_folding_grip").setInaccuracyModifierStill(0.75F);
	public static final Underbarrel UNDERBARREL_POTATO_GRIP = new Underbarrel(6, "underbarrel_potato_grip").setDriftModifier(0.75F);
	
	public static final Auxiliary AUXILIARY_BIPOD = new Auxiliary(1, "auxiliary_bipod").setDriftModifierWhenShiftAndStill(0.25F).setInaccuracyModifierWhenShiftAndStill(0.25F);
	public static final Auxiliary AUXILIARY_STRAIGHT_PULL = new Auxiliary(2, "auxiliary_straight_pull").setIsAllowingReloadWhileZoomed(true);
	
	public static final Ammo AMMO_12G_BUCKSHOT = new Ammo(1, "ammo_12g_buckshot").setReplacementCartridge(CARTRIDGE_12G_BUCKSHOT);
	public static final Ammo AMMO_12G_DART = new Ammo(2, "ammo_12g_dart").setReplacementCartridge(CARTRIDGE_12G_DART);
	public static final Ammo AMMO_12G_FRAG = new Ammo(3, "ammo_12g_frag").setReplacementCartridge(CARTRIDGE_12G_FRAG);
	public static final Ammo AMMO_12G_SLUG = new Ammo(4, "ammo_12g_slug").setReplacementCartridge(CARTRIDGE_12G_SLUG);
	
	public static final Magazine MAGAZINE_QUICK_SWITCH_MAGS = new Magazine(1, "magazine_quick_switch_mags").setReloadTimeModifier(0.8F);
	public static final Magazine MAGAZINE_EXTENDED_MAG_10 = new Magazine(2, "magazine_extended_mag_10").setExtraCapacity(10);
	
	public static final Paint PAINT_BLACK = new Paint(1, "paint_black");
	public static final Paint PAINT_BLUE = new Paint(2, "paint_blue");
	public static final Paint PAINT_GREEN = new Paint(3, "paint_green");
	public static final Paint PAINT_ORANGE = new Paint(4, "paint_orange");
	public static final Paint PAINT_PINK = new Paint(5, "paint_pink");
	public static final Paint PAINT_RED = new Paint(6, "paint_red");
	public static final Paint PAINT_TURQUOISE = new Paint(7, "paint_turquoise");
	public static final Paint PAINT_WHITE = new Paint(8, "paint_white");
	public static final Paint PAINT_YELLOW = new Paint(9, "paint_yellow");
	
	public static final ItemGun GUN = createAssaultRifle("gun", 3, 30, 5F, GunCus.BULLET);
	public static final ItemGun GUN_AK_74M = createAssaultRifle("gun_ak_74m", 3, 30, 5F, GunCus.CARTRIDGE_5_45x39mm);
	public static final ItemGun GUN_M16A4 = createAssaultRifle("gun_m16a4", 3, 30, 5F, GunCus.CARTRIDGE_5_56x45mm);
	
	public static final SoundEventGCI SOUND_SHOOT = new SoundEventGCI(0, "shoot");
	public static final SoundEventGCI SOUND_SHOOT_SILENCED = new SoundEventGCI(1, "shoot_silenced");
	public static final SoundEventGCI SOUND_SHOOT_SNIPER = new SoundEventGCI(2, "shoot_sniper");
	public static final SoundEventGCI SOUND_RELOAD = new SoundEventGCI(3, "reload");
	
	public static final Random RANDOM = new Random();
	
	@SidedProxy(clientSide = "de.cas_ual_ty.gci.client.ProxyClient", serverSide = "de.cas_ual_ty.gci.Proxy")
	public static Proxy proxy;
	public static SimpleNetworkWrapper channel;
	@Instance
	public static GunCus instance;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		proxy.preInit(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init(event);
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
		
		.addAttachment(GunCus.MAGAZINE_QUICK_SWITCH_MAGS)
		.addAttachment(GunCus.MAGAZINE_EXTENDED_MAG_10)
		
		.addAttachment(PAINT_BLACK)
		.addAttachment(PAINT_BLUE)
		.addAttachment(PAINT_GREEN)
		.addAttachment(PAINT_ORANGE)
		.addAttachment(PAINT_PINK)
		.addAttachment(PAINT_RED)
		.addAttachment(PAINT_TURQUOISE)
		.addAttachment(PAINT_WHITE)
		.addAttachment(PAINT_YELLOW)
		;
	}
	
	@EventBusSubscriber
	public static class Handler
	{
		@SubscribeEvent
		public static void registerItems(RegistryEvent.Register<Item> event)
		{
			IForgeRegistry<Item> registry = event.getRegistry();
			
			GunCus.proxy.registerItem(registry, GunCus.BULLET);
			GunCus.proxy.registerItem(registry, GunCus.CARTRIDGE_5_56x45mm);
			GunCus.proxy.registerItem(registry, GunCus.CARTRIDGE_5_45x39mm);
			GunCus.proxy.registerItem(registry, GunCus.CARTRIDGE_12G_BUCKSHOT);
			GunCus.proxy.registerItem(registry, GunCus.CARTRIDGE_12G_DART);
			GunCus.proxy.registerItem(registry, GunCus.CARTRIDGE_12G_FRAG);
			GunCus.proxy.registerItem(registry, GunCus.CARTRIDGE_12G_SLUG);
			
			int j;
			Attachment item;
			
			for(int i = 0; i < Attachment.attachmentList.length; ++i)
			{
				for(j = 0; j < Attachment.getAmmountForSlot(i); ++j)
				{
					item = Attachment.getAttachment(i, j);
					
					if(item != null && item.shouldRegister())
						GunCus.proxy.registerItem(registry, item);
				}
			}
			
			GunCus.proxy.registerGun(registry, GunCus.GUN);
			GunCus.proxy.registerGun(registry, GunCus.GUN_AK_74M);
			GunCus.proxy.registerGun(registry, GunCus.GUN_M16A4);
		}
	}
}
