package de.cas_ual_ty.gci.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.registries.IForgeRegistry;
import de.cas_ual_ty.gci.GunCus;
import de.cas_ual_ty.gci.Proxy;
import de.cas_ual_ty.gci.item.ItemGCI;
import de.cas_ual_ty.gci.item.ItemGun;
import de.cas_ual_ty.gci.item.attachment.Attachment;
import de.cas_ual_ty.gci.item.attachment.EnumAttachmentType;

public class ProxyClient extends Proxy
{
	public ProxyClient()
	{
		try //TODO
		{
			generateAttachmentModelsForGun(GunCus.GUN_M16A4);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static ArrayList<ItemGun> registeredGuns = new ArrayList<ItemGun>();
	
	public void registerItemRenderer(ItemGCI item)
	{
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(GunCus.MOD_ID + ":" + item.getModelRL(), "inventory"));
	}
	
	@Override
	public void registerItem(IForgeRegistry<Item> registry, ItemGCI item)
	{
		super.registerItem(registry, item);
		this.registerItemRenderer(item);
	}
	
	@Override
	public void registerGun(IForgeRegistry<Item> registry, ItemGun gun)
	{
		super.registerGun(registry, gun);
		
		registeredGuns.add(gun);
		
		ModelResourceLocation main = new ModelResourceLocation(GunCus.MOD_ID + ":" + gun.getModelRL() + "/gun", "inventory");
		ModelLoader.setCustomModelResourceLocation(gun, 0, main);
		
		ArrayList<ModelResourceLocation> list = new ArrayList<ModelResourceLocation>();
		list.add(main);
		
		int i;
		int j;
		Attachment attachment;
		
		for(i = 0; i < EnumAttachmentType.values().length; ++i) //All layers
		{
			for(j = 0; j < Attachment.getAmmountForSlot(i); ++j) //All attachments per layer
			{
				if(gun.canSetAttachment(i, j)) //Check if attachment is compatible
				{
					attachment = Attachment.getAttachment(i, j);
					
					if(attachment != null && attachment.shouldLoadModel()) //null-attachment exists, as well as some which are not visible
					{
						list.add(new ModelResourceLocation(GunCus.MOD_ID + ":" + gun.getModelRL() + "/" + attachment.getModelRL(), "inventory")); //Add MRL to the list
					}
				}
			}
		}
		
		ModelBakery.registerItemVariants(gun, list.toArray(new ModelResourceLocation[list.size()])); //Register all attachment MRLs found so that they will be loaded
	}
	
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
		
		MinecraftForge.EVENT_BUS.register(new BakeHandler());
	}
	
	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);
		
		MinecraftForge.EVENT_BUS.register(new EventHandlerClient());
	}
	
	@Override
	public EntityPlayer getClientPlayer(MessageContext ctx)
	{
		return Minecraft.getMinecraft().player;
	}
	
	public static void generateAttachmentModelsForGun(String path, ItemGun gun) throws IOException
	{
		String gunName = gun.getModelRL();
		
		File baseAttachmentFile = new File(path + "/models/item/base/attachment_name.json");
		File baseGunFile = new File(path + "/models/item/base/gun_name.json");
		
		ArrayList<String> lines = new ArrayList<String>();
		
		String line;
		
		BufferedReader reader = new BufferedReader(new FileReader(baseAttachmentFile));
		
		while ((line = reader.readLine()) != null)
		{
			lines.add(line.replaceAll("gun_name", gunName));
		}
		
		reader.close();
		
		String itemDirPath = path + "/models/item/" + gun.getModelRL();
		File itemDir = new File(itemDirPath);
		
		if(!itemDir.exists())
			itemDir.mkdirs();
		
		int j;
		Attachment attachment;
		BufferedWriter writer;
		
		for(int i = 0; i < EnumAttachmentType.values().length; ++i)
		{
			for(j = 0; j < Attachment.getAmmountForSlot(i); ++j)
			{
				if(gun.canSetAttachment(i, j))
				{
					attachment = Attachment.getAttachment(i, j);
					
					if(attachment != null && attachment.shouldLoadModel())
					{
						String attachmentModelPath = itemDirPath + "/" + attachment.getModelRL() + ".json";
						File attachmentModel = new File(attachmentModelPath);
						
						if(!attachmentModel.exists())
						{
							attachmentModel.createNewFile();
							
							writer = new BufferedWriter(new FileWriter(attachmentModel));
							
							for(String line1 : lines)
							{
								writer.write(line1.replaceAll("attachment_name", attachment.getModelRL()));
								writer.newLine();
							}
							
							writer.close();
						}
					}
				}
			}
		}
		
		File gunModel = new File(itemDirPath + "/gun.json");
		
		if(!gunModel.exists())
		{
			gunModel.createNewFile();
			
			lines.clear();
			
			reader = new BufferedReader(new FileReader(baseGunFile));
			
			while ((line = reader.readLine()) != null)
			{
				lines.add(line.replaceAll("gun_name", gunName));
			}
			
			reader.close();
			
			writer = new BufferedWriter(new FileWriter(gunModel));
			
			for(String line1 : lines)
			{
				writer.write(line1);
				writer.newLine();
			}
			
			writer.close();
		}
	}
	
	public static void generateAttachmentModelsForGun(ItemGun gun) throws IOException
	{
		generateAttachmentModelsForGun("C:/Minecraft Coding 1.12/src/main/resources/assets/gci", gun);
	}
}
