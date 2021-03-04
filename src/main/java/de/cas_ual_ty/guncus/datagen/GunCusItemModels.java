package de.cas_ual_ty.guncus.datagen;

import de.cas_ual_ty.guncus.item.AttachmentItem;
import de.cas_ual_ty.guncus.item.BulletItem;
import de.cas_ual_ty.guncus.registries.GunCusItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class GunCusItemModels extends ItemModelProvider
{
    public GunCusItemModels(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper)
    {
        super(generator, modid, existingFileHelper);
    }
    
    @Override
    protected void registerModels()
    {
        // --- ATTACHMENTS ---
        
        for(AttachmentItem attachment : AttachmentItem.ALL_ATTACHMENTS_LIST)
        {
            if(!attachment.isDefault())
            {
                this.defaultModel(attachment);
            }
        }
        
        // --- BULLETS ---
        
        for(BulletItem bullet : BulletItem.ALL_BULLETS_LIST)
        {
            this.defaultModel(bullet);
        }
        
        // --- BLOCKS ---
        
        this.defaultBlockModel(GunCusItems.GUN_TABLE);
        this.defaultBlockModel(GunCusItems.GUN_MAKER);
        this.defaultBlockModel(GunCusItems.BULLET_MAKER);
        this.defaultBlockModel(GunCusItems.OPTIC_MAKER);
        this.defaultBlockModel(GunCusItems.ACCESSORY_MAKER);
        this.defaultBlockModel(GunCusItems.BARREL_MAKER);
        this.defaultBlockModel(GunCusItems.UNDERBARREL_MAKER);
        this.defaultBlockModel(GunCusItems.AUXILIARY_MAKER);
        this.defaultBlockModel(GunCusItems.AMMO_MAKER);
        this.defaultBlockModel(GunCusItems.MAGAZINE_MAKER);
        this.defaultBlockModel(GunCusItems.PAINT_MAKER);
    }
    
    public void defaultModel(Item item)
    {
        this.getBuilder(item.getRegistryName().toString())
            .parent(new UncheckedModelFile("item/generated"))
            .texture("layer0", this.modLoc("item/" + item.getRegistryName().getPath()));
    }
    
    public void defaultBlockModel(BlockItem item)
    {
        this.getBuilder(item.getRegistryName().toString())
            .parent(this.getExistingFile(this.modLoc("block/" + item.getRegistryName().getPath())));
    }
}