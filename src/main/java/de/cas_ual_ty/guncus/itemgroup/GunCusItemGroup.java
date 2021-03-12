package de.cas_ual_ty.guncus.itemgroup;

import de.cas_ual_ty.guncus.registries.GunCusItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class GunCusItemGroup extends ItemGroup
{
    public GunCusItemGroup(String label)
    {
        super(label);
    }
    
    @Override
    public ItemStack createIcon()
    {
        return new ItemStack(GunCusItems.GUN_TABLE);
    }
}
