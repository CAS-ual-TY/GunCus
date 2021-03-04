package de.cas_ual_ty.guncus.datagen;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;

import de.cas_ual_ty.guncus.item.AttachmentItem;
import de.cas_ual_ty.guncus.item.GunItem;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class GunCusGunModels extends ModelProvider<ItemModelBuilder>
{
    public GunCusGunModels(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper)
    {
        super(generator, modid, ITEM_FOLDER, GunCusItemModelBuilder::new, existingFileHelper);
    }
    
    @Nonnull
    @Override
    public String getName()
    {
        return "Item Models: " + this.modid;
    }
    
    public static class GunCusItemModelBuilder extends ItemModelBuilder
    {
        public GunCusItemModelBuilder(ResourceLocation outputLocation, ExistingFileHelper existingFileHelper)
        {
            super(outputLocation, existingFileHelper);
        }
        
        @Override
        public GunCusItemModelBuilder texture(String key, ResourceLocation texture)
        {
            // Overriding this because apparently gun attachment textures cant be found
            // so this method would throw an Exception if not overridden
            Preconditions.checkNotNull(key, "Key must not be null");
            Preconditions.checkNotNull(texture, "Texture must not be null");
            this.textures.put(key, texture.toString());
            return this;
        }
    }
    
    @Override
    protected void registerModels()
    {
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
                    
                    this.getBuilder(this.modid + ":item/" + path)
                        .parent(new UncheckedModelFile("item/generated"))
                        .texture("layer0", this.modLoc("item/" + path));
                }
            }
        }
    }
}