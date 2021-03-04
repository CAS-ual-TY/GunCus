package de.cas_ual_ty.guncus.item;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MakerItem extends Item
{
    protected int craftAmount;
    protected List<ItemStack> materials;
    
    public MakerItem(Properties properties, int craftAmount, List<ItemStack> materials)
    {
        super(properties);
        this.craftAmount = craftAmount;
        this.materials = materials;
    }
    
    public MakerItem(Properties properties, int craftAmount, ItemStack... materials)
    {
        this(properties, craftAmount, Lists.newArrayList(materials));
    }
    
    public int getMakerOutputAmount()
    {
        return this.craftAmount;
    }
    
    public List<ItemStack> getMakerMaterials()
    {
        return this.materials;
    }
}
