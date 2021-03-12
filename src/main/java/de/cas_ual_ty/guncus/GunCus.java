package de.cas_ual_ty.guncus;

import java.util.List;

import de.cas_ual_ty.guncus.command.GunCusCommand;
import de.cas_ual_ty.guncus.item.GunItem;
import de.cas_ual_ty.guncus.itemgroup.GunCusItemGroup;
import de.cas_ual_ty.guncus.network.HitmarkerMessage;
import de.cas_ual_ty.guncus.network.MakerMessages;
import de.cas_ual_ty.guncus.network.ShootMessage;
import de.cas_ual_ty.guncus.registries.GunCusItems;
import de.cas_ual_ty.guncus.registries.GunCusPointOfInterestTypes;
import de.cas_ual_ty.guncus.registries.GunCusVillagerProfessions;
import de.cas_ual_ty.guncus.util.GunCusUtility;
import de.cas_ual_ty.guncus.util.RandomTradeBuilder;
import net.minecraft.entity.merchant.villager.VillagerTrades.ITrade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

@Mod(GunCus.MOD_ID)
public class GunCus
{
    public static final String MOD_ID = "guncus";
    public static final String PROTOCOL_VERSION = "2";
    
    public static GunCus instance;
    public static IProxy proxy;
    public static SimpleChannel channel;
    
    public static boolean FULL_CREATIVE_TABS = false;
    
    public static final GunCusItemGroup ITEM_GROUP_GUN_CUS = new GunCusItemGroup(GunCus.MOD_ID);
    
    public GunCus()
    {
        GunCus.instance = this;
        GunCus.proxy = (IProxy)DistExecutor.safeRunForDist(() -> de.cas_ual_ty.guncus.client.ClientProxy::new, () -> de.cas_ual_ty.guncus.server.ServerProxy::new);
        
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::init);
        GunCus.proxy.registerModEventListeners(bus);
        
        bus = MinecraftForge.EVENT_BUS;
        bus.addListener(this::registerCommands);
        bus.addListener(this::villagerTrades);
        bus.addListener(this::wandererTrades);
        GunCus.proxy.registerForgeEventListeners(bus);
    }
    
    public void init(FMLCommonSetupEvent event)
    {
        GunCus.channel = NetworkRegistry.newSimpleChannel(new ResourceLocation(GunCus.MOD_ID, "main"),
            () -> GunCus.PROTOCOL_VERSION,
            GunCus.PROTOCOL_VERSION::equals,
            GunCus.PROTOCOL_VERSION::equals);
        GunCus.channel.registerMessage(0, ShootMessage.class, ShootMessage::encode, ShootMessage::decode, ShootMessage::handle);
        GunCus.channel.registerMessage(1, HitmarkerMessage.class, HitmarkerMessage::encode, HitmarkerMessage::decode, HitmarkerMessage::handle);
        GunCus.channel.registerMessage(4, MakerMessages.Next.class, MakerMessages.Next::encode, MakerMessages.Next::decode, MakerMessages.Next::handle);
        GunCus.channel.registerMessage(5, MakerMessages.Prev.class, MakerMessages.Prev::encode, MakerMessages.Prev::decode, MakerMessages.Prev::handle);
        GunCus.channel.registerMessage(6, MakerMessages.Create.class, MakerMessages.Create::encode, MakerMessages.Create::decode, MakerMessages.Create::handle);
        
        GunCusUtility.fixPOITypeBlockStates(GunCusPointOfInterestTypes.ARMS_DEALER);
        
        GunCus.proxy.init();
        
        for(GunItem gun : GunItem.ALL_GUNS_LIST)
        {
            if(gun.gunTab != null)
            {
                gun.gunTab.init();
            }
        }
    }
    
    public void registerCommands(RegisterCommandsEvent event)
    {
        GunCusCommand.register(event.getDispatcher());
    }
    
    public void villagerTrades(VillagerTradesEvent event)
    {
        if(event.getType() == GunCusVillagerProfessions.ARMS_DEALER)
        {
            event.getTrades().get(1).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 16), new ItemStack(GunCusItems.GUN_TABLE), 8, 10, 0F));
            RandomTradeBuilder.forEachLevel((level, tradeBuild) -> event.getTrades().get(level.intValue()).add(tradeBuild.build()));
        }
    }
    
    public void wandererTrades(WandererTradesEvent event)
    {
        List<ITrade> genericList = event.getGenericTrades();
        RandomTradeBuilder.forEachWanderer((tradeBuild) -> genericList.add(tradeBuild.build()));
        
        List<ITrade> rareList = event.getRareTrades();
        RandomTradeBuilder.forEachWandererRare((tradeBuild) -> rareList.add(tradeBuild.build()));
    }
    
    public static void debug(String s)
    {
        System.out.println(GunCus.MOD_ID + " ----- " + s);
    }
}
