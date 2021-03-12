package de.cas_ual_ty.guncus.datagen;

import de.cas_ual_ty.guncus.item.AttachmentItem;
import de.cas_ual_ty.guncus.item.GunItem;
import de.cas_ual_ty.guncus.item.attachments.EnumAttachmentType;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelBuilder.Perspective;
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
        return super.getName() + " (guns)";
    }
    
    @Override
    protected void registerModels()
    {
        // --- GUNS + THEIR ATTACHMENTS ---
        
        String path;
        
        for(GunItem gun : GunItem.ALL_GUNS_LIST)
        {
            this.getBuilder(gun.getRegistryName().toString())
                .parent(new UncheckedModelFile("item/generated"))
                .texture("layer0", this.modLoc("item/dummy"));
            
            for(AttachmentItem[] attachments : gun.getAttachments())
            {
                for(AttachmentItem attachment : attachments)
                {
                    path = gun.getRegistryName().getPath() + "/" + attachment.getRegistryName().getPath();
                    
                    if(!attachment.isDefault() && attachment.getType() == EnumAttachmentType.PAINT)
                    {
                        this.getBuilder(this.modid + ":item/" + path)
                            .parent(new UncheckedModelFile(this.modid + ":item/" + gun.getRegistryName().getPath() + "/paint_default"))
                            .texture("layer0", this.modLoc("item/" + path));
                    }
                    else if(!attachment.isDefault() && attachment.getType() == EnumAttachmentType.OPTIC)
                    {
                        this.getBuilder(this.modid + ":item/" + path)
                            .parent(new UncheckedModelFile("item/handheld"))
                            .texture("layer0", this.modLoc("item/" + path))
                            .transforms().transform(Perspective.FIRSTPERSON_RIGHT).scale(0);
                    }
                    else
                    {
                        this.getBuilder(this.modid + ":item/" + path)
                            .parent(new UncheckedModelFile("item/handheld"))
                            .texture("layer0", this.modLoc("item/" + path));
                    }
                }
            }
        }
    }
}