package de.cas_ual_ty.guncus.datagen;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.cas_ual_ty.guncus.registries.GunCusBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.LootTableProvider;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.DynamicLootEntry;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;
import net.minecraft.loot.functions.CopyName;
import net.minecraft.loot.functions.CopyNbt;
import net.minecraft.loot.functions.SetContents;
import net.minecraft.util.ResourceLocation;

public class GunCusLootTables extends LootTableProvider
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    
    // Filled by subclasses
    protected final Map<Block, LootTable.Builder> lootTables = new HashMap<>();
    
    private final DataGenerator generator;
    private final String modid;
    
    public GunCusLootTables(DataGenerator dataGeneratorIn, String modid)
    {
        super(dataGeneratorIn);
        this.generator = dataGeneratorIn;
        this.modid = modid;
    }
    
    @Override
    public String getName()
    {
        return "Loot Tables: " + this.modid;
    }
    
    public void addTables()
    {
        this.defaultLootTable(GunCusBlocks.GUN_TABLE);
        this.defaultLootTable(GunCusBlocks.GUN_MAKER);
        this.defaultLootTable(GunCusBlocks.BULLET_MAKER);
        this.defaultLootTable(GunCusBlocks.OPTIC_MAKER);
        this.defaultLootTable(GunCusBlocks.ACCESSORY_MAKER);
        this.defaultLootTable(GunCusBlocks.BARREL_MAKER);
        this.defaultLootTable(GunCusBlocks.UNDERBARREL_MAKER);
        this.defaultLootTable(GunCusBlocks.AUXILIARY_MAKER);
        this.defaultLootTable(GunCusBlocks.AMMO_MAKER);
        this.defaultLootTable(GunCusBlocks.MAGAZINE_MAKER);
        this.defaultLootTable(GunCusBlocks.PAINT_MAKER);
    }
    
    public void defaultLootTable(Block block)
    {
        this.lootTables.put(block, this.createStandardTable(block.getRegistryName().getPath(), block));
    }
    
    // Subclasses can call this if they want a standard loot table. Modify this for your own needs
    protected LootTable.Builder createStandardTable(String name, Block block)
    {
        LootPool.Builder builder = LootPool.builder()
            .name(name)
            .rolls(ConstantRange.of(1))
            .addEntry(ItemLootEntry.builder(block)
                .acceptFunction(CopyName.builder(CopyName.Source.BLOCK_ENTITY))
                .acceptFunction(CopyNbt.builder(CopyNbt.Source.BLOCK_ENTITY)
                    .addOperation("inv", "BlockEntityTag.inv", CopyNbt.Action.REPLACE)
                    .addOperation("energy", "BlockEntityTag.energy", CopyNbt.Action.REPLACE))
                .acceptFunction(SetContents.builderIn()
                    .addLootEntry(DynamicLootEntry.func_216162_a(new ResourceLocation("minecraft", "contents")))));
        return LootTable.builder().addLootPool(builder);
    }
    
    @Override
    // Entry point
    public void act(DirectoryCache cache)
    {
        this.addTables();
        
        Map<ResourceLocation, LootTable> tables = new HashMap<>();
        for(Map.Entry<Block, LootTable.Builder> entry : this.lootTables.entrySet())
        {
            tables.put(entry.getKey().getLootTable(), entry.getValue().setParameterSet(LootParameterSets.BLOCK).build());
        }
        this.writeTables(cache, tables);
    }
    
    // Actually write out the tables in the output folder
    private void writeTables(DirectoryCache cache, Map<ResourceLocation, LootTable> tables)
    {
        Path outputFolder = this.generator.getOutputFolder();
        tables.forEach((key, lootTable) ->
        {
            Path path = outputFolder.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
            try
            {
                IDataProvider.save(GunCusLootTables.GSON, cache, LootTableManager.toJson(lootTable), path);
            }
            catch (IOException e)
            {
                GunCusLootTables.LOGGER.error("Couldn't write loot table {}", path, e);
            }
        });
    }
}