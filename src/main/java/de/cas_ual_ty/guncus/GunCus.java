package de.cas_ual_ty.guncus;

import de.cas_ual_ty.guncus.command.CommandGunCus;
import de.cas_ual_ty.guncus.itemgroup.ItemGroupGunCus;
import de.cas_ual_ty.guncus.network.MessageHitmarker;
import de.cas_ual_ty.guncus.network.MessageShoot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

@Mod(GunCus.MOD_ID)
public class GunCus
{
    public static final String MOD_ID = "guncus";
    public static final String PROTOCOL_VERSION = "1";
    
    public static GunCus instance;
    public static IProxy proxy;
    public static SimpleChannel channel;
    
    public static boolean fullCreativeTabs = true;
    
    public static final ItemGroupGunCus ITEM_GROUP_GUN_CUS = new ItemGroupGunCus();
    
    public GunCus()
    {
        GunCus.instance = this;
        GunCus.proxy = (IProxy) DistExecutor.runForDist(() -> de.cas_ual_ty.guncus.client.ProxyClient::new, () -> de.cas_ual_ty.guncus.server.ProxyServer::new);
        
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::init);
        GunCus.proxy.registerModEventListeners(bus);
        
        bus = MinecraftForge.EVENT_BUS;
        bus.addListener(this::serverStarting);
        GunCus.proxy.registerForgeEventListeners(bus);
    }
    
    public void init(FMLCommonSetupEvent event)
    {
        GunCus.channel = NetworkRegistry.newSimpleChannel(new ResourceLocation(GunCus.MOD_ID, "main"),
                        () -> GunCus.PROTOCOL_VERSION,
                        GunCus.PROTOCOL_VERSION::equals,
                        GunCus.PROTOCOL_VERSION::equals);
        GunCus.channel.registerMessage(0, MessageShoot.class, MessageShoot::encode, MessageShoot::decode, MessageShoot::handle);
        GunCus.channel.registerMessage(1, MessageHitmarker.class, MessageHitmarker::encode, MessageHitmarker::decode, MessageHitmarker::handle);
        
        GunCus.proxy.init();
    }
    
    public void serverStarting(FMLServerStartingEvent event)
    {
        CommandGunCus.register(event.getCommandDispatcher());
    }
    
    public static void debug(String s)
    {
        System.out.println(GunCus.MOD_ID + " ----- " + s);
    }
}
