package de.cas_ual_ty.guncus.item;

import java.util.ArrayList;
import java.util.List;

import de.cas_ual_ty.guncus.GunCus;
import de.cas_ual_ty.guncus.util.RandomTradeBuilder;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class BulletItem extends MakerItem
{
    public static final List<MakerItem> BULLETS_LIST = new ArrayList<>();
    
    protected float extraDamage;
    protected float gravity;
    protected float bulletSpeedModifier;
    protected float spreadModifier;
    protected int projectileAmount;
    
    public BulletItem(Properties properties, int craftAmount, ItemStack... materials)
    {
        super(properties, craftAmount, materials);
        
        this.extraDamage = 0F;
        this.gravity = 1F;
        this.bulletSpeedModifier = 1F;
        this.spreadModifier = 1F;
        this.projectileAmount = 1;
        
        if(craftAmount > 0 && materials.length > 0)
        {
            BulletItem.BULLETS_LIST.add(this);
        }
    }
    
    public BulletItem(Properties properties, int iron, int gunpowder, int craftAmt)
    {
        this(properties, craftAmt, new ItemStack(Items.IRON_INGOT, iron), new ItemStack(Items.GUNPOWDER, gunpowder));
    }
    
    public BulletItem setDefaultTradeable(int amt)
    {
        new RandomTradeBuilder(3, 25, 0.05F).setEmeraldPriceFor(1, this, amt).registerLevel(1);
        return this;
    }
    
    public float getExtraDamage()
    {
        return this.extraDamage;
    }
    
    public float getGravity()
    {
        return this.gravity;
    }
    
    public float getBulletSpeedModifier()
    {
        return this.bulletSpeedModifier;
    }
    
    public float getSpreadModifier()
    {
        return this.spreadModifier;
    }
    
    public int getProjectileAmount()
    {
        return this.projectileAmount;
    }
    
    public BulletItem setDamage(float damage)
    {
        this.extraDamage = damage;
        return this;
    }
    
    public BulletItem setGravity(float gravity)
    {
        this.gravity = gravity;
        return this;
    }
    
    public BulletItem setBulletSpeedModifier(float bulletSpeedModifier)
    {
        this.bulletSpeedModifier = bulletSpeedModifier;
        return this;
    }
    
    public BulletItem setSpreadModifier(float spreadModifier)
    {
        this.spreadModifier = spreadModifier;
        return this;
    }
    
    public BulletItem setProjectileAmount(int projectileAmount)
    {
        this.projectileAmount = projectileAmount;
        return this;
    }
    
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("local." + GunCus.MOD_ID + ".bullet").setStyle(Style.EMPTY.applyFormatting(TextFormatting.YELLOW)));
    }
}
