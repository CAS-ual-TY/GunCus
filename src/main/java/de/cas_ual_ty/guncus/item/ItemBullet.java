package de.cas_ual_ty.guncus.item;

import java.util.ArrayList;
import java.util.List;

import de.cas_ual_ty.guncus.GunCus;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ItemBullet extends Item
{
    public static final ArrayList<ItemBullet> BULLETS_LIST = new ArrayList<ItemBullet>();
    
    protected float extraDamage;
    protected float gravity;
    protected float bulletSpeedModifier;
    protected float spreadModifier;
    protected int projectileAmount;
    
    public ItemBullet(Properties properties)
    {
        super(properties);
        
        this.extraDamage = 0F;
        this.gravity = 1F;
        this.bulletSpeedModifier = 1F;
        this.spreadModifier = 1F;
        this.projectileAmount = 1;
        
        ItemBullet.BULLETS_LIST.add(this);
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
        tooltip.add(new TranslationTextComponent("local." + GunCus.MOD_ID + ".bullet").setStyle(new Style().setColor(TextFormatting.YELLOW)));
    }
}
