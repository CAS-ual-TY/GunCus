package de.cas_ual_ty.guncus.datagen;

import java.util.function.Consumer;

import de.cas_ual_ty.guncus.registries.GunCusItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;

public class GunCusCraftingRecipes extends RecipeProvider
{
    private final String modid;
    
    public GunCusCraftingRecipes(DataGenerator generatorIn, String modid)
    {
        super(generatorIn);
        this.modid = modid;
    }
    
    @Override
    public String getName()
    {
        return "Crafting Recipes: " + this.modid;
    }
    
    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer)
    {
        ShapedRecipeBuilder.shapedRecipe(GunCusItems.GUN_TABLE)
            .patternLine(" X ")
            .patternLine(" Y ")
            .patternLine("XZX")
            .key('X', Items.IRON_INGOT)
            .key('Y', Items.CRAFTING_TABLE)
            .key('Z', Items.IRON_BLOCK)
            .setGroup(GunCusItems.GUN_TABLE.getRegistryName().toString())
            .addCriterion(this.modid + ":has_iron", RecipeProvider.hasItem(Items.IRON_INGOT))
            .build(consumer);
        
        ShapedRecipeBuilder.shapedRecipe(GunCusItems.GUN_MAKER)
            .patternLine("YZY")
            .patternLine("YXY")
            .patternLine("YWY")
            .key('X', GunCusItems.GUN_TABLE)
            .key('Y', Items.IRON_INGOT)
            .key('Z', Items.GOLD_INGOT)
            .key('W', Items.IRON_BLOCK)
            .setGroup(GunCusItems.GUN_MAKER.getRegistryName().toString())
            .addCriterion(this.modid + ":has_gun_table", RecipeProvider.hasItem(GunCusItems.GUN_TABLE))
            .build(consumer);
        
        ShapedRecipeBuilder.shapedRecipe(GunCusItems.BULLET_MAKER)
            .patternLine("YZY")
            .patternLine("YXY")
            .patternLine("YWY")
            .key('X', GunCusItems.GUN_TABLE)
            .key('Y', Items.IRON_INGOT)
            .key('Z', Items.GUNPOWDER)
            .key('W', Items.IRON_BLOCK)
            .setGroup(GunCusItems.BULLET_MAKER.getRegistryName().toString())
            .addCriterion(this.modid + ":has_gun_table", RecipeProvider.hasItem(GunCusItems.GUN_TABLE))
            .build(consumer);
        
        ShapedRecipeBuilder.shapedRecipe(GunCusItems.OPTIC_MAKER)
            .patternLine("YZY")
            .patternLine("YXY")
            .patternLine("YYY")
            .key('X', GunCusItems.GUN_TABLE)
            .key('Y', Items.IRON_INGOT)
            .key('Z', Items.GLASS_PANE)
            .setGroup(GunCusItems.OPTIC_MAKER.getRegistryName().toString())
            .addCriterion(this.modid + ":has_gun_table", RecipeProvider.hasItem(GunCusItems.GUN_TABLE))
            .build(consumer);
        
        ShapedRecipeBuilder.shapedRecipe(GunCusItems.ACCESSORY_MAKER)
            .patternLine("YYY")
            .patternLine("YXY")
            .patternLine("YYZ")
            .key('X', GunCusItems.GUN_TABLE)
            .key('Y', Items.IRON_INGOT)
            .key('Z', Items.REDSTONE)
            .setGroup(GunCusItems.ACCESSORY_MAKER.getRegistryName().toString())
            .addCriterion(this.modid + ":has_gun_table", RecipeProvider.hasItem(GunCusItems.GUN_TABLE))
            .build(consumer);
        
        ShapedRecipeBuilder.shapedRecipe(GunCusItems.BARREL_MAKER)
            .patternLine("ZYY")
            .patternLine("YXY")
            .patternLine("YYY")
            .key('X', GunCusItems.GUN_TABLE)
            .key('Y', Items.IRON_INGOT)
            .key('Z', Items.STRING)
            .setGroup(GunCusItems.BARREL_MAKER.getRegistryName().toString())
            .addCriterion(this.modid + ":has_gun_table", RecipeProvider.hasItem(GunCusItems.GUN_TABLE))
            .build(consumer);
        
        ShapedRecipeBuilder.shapedRecipe(GunCusItems.UNDERBARREL_MAKER)
            .patternLine("YYY")
            .patternLine("ZXY")
            .patternLine("YYY")
            .key('X', GunCusItems.GUN_TABLE)
            .key('Y', Items.IRON_INGOT)
            .key('Z', Items.LEATHER)
            .setGroup(GunCusItems.UNDERBARREL_MAKER.getRegistryName().toString())
            .addCriterion(this.modid + ":has_gun_table", RecipeProvider.hasItem(GunCusItems.GUN_TABLE))
            .build(consumer);
        
        ShapedRecipeBuilder.shapedRecipe(GunCusItems.AUXILIARY_MAKER)
            .patternLine("YYY")
            .patternLine("YXY")
            .patternLine("ZYY")
            .key('X', GunCusItems.GUN_TABLE)
            .key('Y', Items.IRON_INGOT)
            .key('Z', Items.QUARTZ)
            .setGroup(GunCusItems.AUXILIARY_MAKER.getRegistryName().toString())
            .addCriterion(this.modid + ":has_gun_table", RecipeProvider.hasItem(GunCusItems.GUN_TABLE))
            .build(consumer);
        
        ShapedRecipeBuilder.shapedRecipe(GunCusItems.AMMO_MAKER)
            .patternLine("YYZ")
            .patternLine("YXY")
            .patternLine("YYY")
            .key('X', GunCusItems.GUN_TABLE)
            .key('Y', Items.IRON_INGOT)
            .key('Z', Items.GOLD_INGOT)
            .setGroup(GunCusItems.AMMO_MAKER.getRegistryName().toString())
            .addCriterion(this.modid + ":has_gun_table", RecipeProvider.hasItem(GunCusItems.GUN_TABLE))
            .build(consumer);
        
        ShapedRecipeBuilder.shapedRecipe(GunCusItems.MAGAZINE_MAKER)
            .patternLine("YYY")
            .patternLine("YXY")
            .patternLine("YZY")
            .key('X', GunCusItems.GUN_TABLE)
            .key('Y', Items.IRON_INGOT)
            .key('Z', Items.SLIME_BALL)
            .setGroup(GunCusItems.MAGAZINE_MAKER.getRegistryName().toString())
            .addCriterion(this.modid + ":has_gun_table", RecipeProvider.hasItem(GunCusItems.GUN_TABLE))
            .build(consumer);
        
        ShapedRecipeBuilder.shapedRecipe(GunCusItems.PAINT_MAKER)
            .patternLine("YYY")
            .patternLine("YXZ")
            .patternLine("YYY")
            .key('X', GunCusItems.GUN_TABLE)
            .key('Y', Items.IRON_INGOT)
            .key('Z', Items.LAPIS_LAZULI)
            .setGroup(GunCusItems.PAINT_MAKER.getRegistryName().toString())
            .addCriterion(this.modid + ":has_gun_table", RecipeProvider.hasItem(GunCusItems.GUN_TABLE))
            .build(consumer);
    }
}