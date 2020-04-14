package de.cas_ual_ty.guncus.itemgroup;

import java.util.Random;

import de.cas_ual_ty.guncus.GunCus;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.NonNullList;

public class ItemGroupGunCus extends ItemGroupShuffle
{
    public ItemGroupGunCus()
    {
        super(GunCus.MOD_ID, new Random(1776));
    }
    
    //    @Override
    public void fill2(NonNullList<ItemStack> items)
    {
        // TODO info book
        
        ListNBT list = new ListNBT();
        list.add(StringNBT.valueOf("bla 1 2 3"));
        list.add(StringNBT.valueOf("Absolute test blablabla"));
        list.add(StringNBT.valueOf("blablablablablablablablabla"));
        
        ItemStack stack = new ItemStack(Items.WRITTEN_BOOK);
        CompoundNBT nbt = stack.getOrCreateTag();
        nbt.put("pages", list);
        nbt.put("title", StringNBT.valueOf("Mod Information"));
        nbt.put("author", StringNBT.valueOf("CAS_ual_TY"));
        
        items.add(stack);
        
        super.fill(items);
    }
    
    @Override
    public void fillShuffleList(NonNullList<ItemStack> list)
    {
        super.fill(list);
    }
}
