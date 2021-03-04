package de.cas_ual_ty.guncus.datagen;

import de.cas_ual_ty.guncus.registries.GunCusBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.Direction;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile.ExistingModelFile;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

public class GunCusBlockStates extends BlockStateProvider
{
    public GunCusBlockStates(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper)
    {
        super(generator, modid, existingFileHelper);
    }
    
    @Override
    protected void registerStatesAndModels()
    {
        this.defaultState(GunCusBlocks.GUN_TABLE);
        this.defaultHorizontalState(GunCusBlocks.GUN_MAKER);
        this.defaultHorizontalState(GunCusBlocks.BULLET_MAKER);
        this.defaultHorizontalState(GunCusBlocks.OPTIC_MAKER);
        this.defaultHorizontalState(GunCusBlocks.ACCESSORY_MAKER);
        this.defaultHorizontalState(GunCusBlocks.BARREL_MAKER);
        this.defaultHorizontalState(GunCusBlocks.UNDERBARREL_MAKER);
        this.defaultHorizontalState(GunCusBlocks.AUXILIARY_MAKER);
        this.defaultHorizontalState(GunCusBlocks.AMMO_MAKER);
        this.defaultHorizontalState(GunCusBlocks.MAGAZINE_MAKER);
        this.defaultHorizontalState(GunCusBlocks.PAINT_MAKER);
    }
    
    public void defaultState(Block block)
    {
        this.getVariantBuilder(block)
            .forAllStates(state -> ConfiguredModel.builder()
                .modelFile(this.models().getExistingFile(this.modLoc("block/" + block.getRegistryName().getPath())))
                .build());
    }
    
    public void defaultHorizontalState(HorizontalBlock block)
    {
        ExistingModelFile model = this.models().getExistingFile(this.modLoc("block/" + block.getRegistryName().getPath()));
        VariantBlockStateBuilder builder = this.getVariantBuilder(block);
        
        for(Direction d : HorizontalBlock.HORIZONTAL_FACING.getAllowedValues())
        {
            int angle = (int)d.getOpposite().getHorizontalAngle();
            
            builder.partialState().with(HorizontalBlock.HORIZONTAL_FACING, d)
                .modelForState().modelFile(model).rotationY(angle).addModel();
        }
    }
}