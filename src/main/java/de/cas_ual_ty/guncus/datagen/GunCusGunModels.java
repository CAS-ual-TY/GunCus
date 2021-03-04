package de.cas_ual_ty.guncus.datagen;

import de.cas_ual_ty.guncus.item.AttachmentItem;
import de.cas_ual_ty.guncus.item.GunItem;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class GunCusGunModels extends ItemModelProvider
{
    public GunCusGunModels(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper)
    {
        super(generator, modid, existingFileHelper);
    }
    
    @Override
    public String getName()
    {
        return "GunCus Gun Item Models";
    }
    
    @Override
    protected void registerModels()
    {
        String path;
        
        for(GunItem gun : GunItem.ALL_GUNS_LIST)
        {
            for(AttachmentItem[] attachments : gun.getAttachments())
            {
                for(AttachmentItem attachment : attachments)
                {
                    path = gun.getRegistryName().getPath() + "/" + attachment.getRegistryName().getPath();
                    
                    this.getBuilder(this.modid + ":" + path)
                        .parent(new UncheckedModelFile("item/generated"))
                        .texture("layer0", this.modLoc("item/" + path));
                }
            }
        }
    }
}