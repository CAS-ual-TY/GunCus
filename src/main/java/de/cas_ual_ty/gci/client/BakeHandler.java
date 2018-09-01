package de.cas_ual_ty.gci.client;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import de.cas_ual_ty.gci.GunCus;
import de.cas_ual_ty.gci.item.ItemGun;
import de.cas_ual_ty.gci.item.attachment.Attachment;
import de.cas_ual_ty.gci.item.attachment.EnumAttachmentType;

public class BakeHandler
{
	@SubscribeEvent
	public void modelBake(ModelBakeEvent event)
	{
		int i;
		int j;
		Attachment attachment;
		ModelResourceLocation mrl;
		IBakedModel main;
		
		IBakedModel[][] models; //These are the attachment models which will be passed onto the gun model for use
		
		for(ItemGun gun : ProxyClient.registeredGuns) //Cycle through all guns
		{
			models = new IBakedModel[EnumAttachmentType.values().length][];
			
			for(i = 0; i < models.length; ++i) //This represents the layers
			{
				models[i] = new IBakedModel[Attachment.getAmmountForSlot(i)];
				
				for(j = 0; j < Attachment.getAmmountForSlot(i); ++j) //Ammount of attachments for each layer
				{
					if(gun.canSetAttachment(i, j)) //Check if compatible
					{
						attachment = Attachment.getAttachment(i, j);
						
						if(attachment != null && attachment.shouldLoadModel()) //Make sure its not null-attachment and the model is needed
						{
							models[i][j] = event.getModelRegistry().getObject(new ModelResourceLocation(GunCus.MOD_ID + ":" + gun.getModelRL() + "/" + attachment.getModelRL(), "inventory")); //Add attachment model to the array
						}
					}
				}
			}
			
			mrl = new ModelResourceLocation(GunCus.MOD_ID + ":" + gun.getModelRL() + "/gun", "inventory"); //This is the MRL of the main item (gun)
			
			main = event.getModelRegistry().getObject(mrl); //Get the model of the gun
			
			event.getModelRegistry().putObject(mrl, new BakedModelGun(main, models)); //Replace model of the gun with custom IBakedModel and pass all the attachment models to it
		}
	}
}
