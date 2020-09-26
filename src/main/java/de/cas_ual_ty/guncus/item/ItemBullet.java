package de.cas_ual_ty.guncus.item;

import java.util.ArrayList;
import java.util.List;

import de.cas_ual_ty.guncus.GunCus;
import de.cas_ual_ty.guncus.util.RandomTradeBuilder;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ItemBullet extends Item
{
    public static final ArrayList<ItemBullet> BULLETS_LIST = new ArrayList<>();
    
    protected float extraDamage;
    protected float gravity;
    protected float bulletSpeedModifier;
    protected float spreadModifier;
    protected int projectileAmount;
    
    public final int ironAmt;
    public final int gunpowderAmt;
    public final int craftAmt;
    
    public ItemBullet(Properties properties, int iron, int gunpowder, int craftAmt)
    {
        super(properties);
        
        this.extraDamage = 0F;
        this.gravity = 1F;
        this.bulletSpeedModifier = 1F;
        this.spreadModifier = 1F;
        this.projectileAmount = 1;
        
        this.ironAmt = iron;
        this.gunpowderAmt = gunpowder;
        this.craftAmt = craftAmt;
        
        ItemBullet.BULLETS_LIST.add(this);
    }
    
    public ItemBullet setDefaultTradeable(int amt)
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
    
    public ItemBullet setDamage(float damage)
    {
        this.extraDamage = damage;
        return this;
    }
    
    public ItemBullet setGravity(float gravity)
    {
        this.gravity = gravity;
        return this;
    }
    
    public ItemBullet setBulletSpeedModifier(float bulletSpeedModifier)
    {
        this.bulletSpeedModifier = bulletSpeedModifier;
        return this;
    }
    
    public ItemBullet setSpreadModifier(float spreadModifier)
    {
        this.spreadModifier = spreadModifier;
        return this;
    }
    
    public ItemBullet setProjectileAmount(int projectileAmount)
    {
        this.projectileAmount = projectileAmount;
        return this;
    }
    
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        ITextComponent t = new TranslationTextComponent("local." + GunCus.MOD_ID + ".bullet");
        t.getStyle().applyFormatting(TextFormatting.YELLOW);
        tooltip.add(t);
    }
}
