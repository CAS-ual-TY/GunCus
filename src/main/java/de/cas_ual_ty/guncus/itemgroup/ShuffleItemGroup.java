package de.cas_ual_ty.guncus.itemgroup;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public abstract class ShuffleItemGroup extends ItemGroup
{
    public static final ArrayList<ShuffleItemGroup> GROUPS_LIST = new ArrayList<>();
    
    public final NonNullList<ItemStack> items;
    protected Random random;
    protected ItemStack icon;
    
    protected int interval;
    protected int tick;
    
    public ShuffleItemGroup(String label)
    {
        this(label, new Random());
    }
    
    public ShuffleItemGroup(String label, Random random)
    {
        super(label);
        this.items = NonNullList.create();
        this.random = random;
        this.icon = ItemStack.EMPTY;
        
        this.interval = this.getInterval();
        this.tick = 0;
        
        ShuffleItemGroup.GROUPS_LIST.add(this);
    }
    
    @Override
    public ItemStack createIcon()
    {
        return this.shuffle();
    }
    
    @Override
    public ItemStack getIcon()
    {
        return this.icon;
    }
    
    public void fillShuffleList(NonNullList<ItemStack> list)
    {
        this.fill(list);
    }
    
    public ItemStack shuffle()
    {
        if(!this.items.isEmpty())
        {
            this.icon = this.items.get(this.random.nextInt(this.items.size()));
        }
        else
        {
            this.fillShuffleList(this.items);
        }
        
        return this.getIcon();
    }
    
    public int getInterval()
    {
        return this.random.nextInt(10) + 10;
    }
    
    public void tick()
    {
        if(++this.tick >= this.interval)
        {
            this.interval = this.getInterval();
            this.tick = 0;
            this.shuffle();
        }
    }
}
