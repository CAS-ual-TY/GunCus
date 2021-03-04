package de.cas_ual_ty.guncus.datagen;

import de.cas_ual_ty.guncus.registries.GunCusBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class GunCusBlockModels extends BlockModelProvider
{
    public GunCusBlockModels(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper)
    {
        super(generator, modid, existingFileHelper);
    }
    
    @Override
    protected void registerModels()
    {
        this.defaultModel(GunCusBlocks.GUN_TABLE);
        this.defaultModel(GunCusBlocks.GUN_MAKER);
        this.defaultModel(GunCusBlocks.BULLET_MAKER);
        this.defaultModel(GunCusBlocks.OPTIC_MAKER);
        this.defaultModel(GunCusBlocks.ACCESSORY_MAKER);
        this.defaultModel(GunCusBlocks.BARREL_MAKER);
        this.defaultModel(GunCusBlocks.UNDERBARREL_MAKER);
        this.defaultModel(GunCusBlocks.AUXILIARY_MAKER);
        this.defaultModel(GunCusBlocks.AMMO_MAKER);
        this.defaultModel(GunCusBlocks.MAGAZINE_MAKER);
        this.defaultModel(GunCusBlocks.PAINT_MAKER);
    }
    
    public void defaultModel(Block block)
    {
        this.getBuilder(block.getRegistryName().getPath())
            .parent(new UncheckedModelFile("block/cube"))
            .texture("particle", this.modLoc("block/side"))
            .texture("down", this.modLoc("block/bottom"))
            .texture("up", this.modLoc("block/" + block.getRegistryName().getPath()))
            .texture("north", this.modLoc("block/side"))
            .texture("east", this.modLoc("block/side"))
            .texture("south", this.modLoc("block/side"))
            .texture("west", this.modLoc("block/side"));
    }
}