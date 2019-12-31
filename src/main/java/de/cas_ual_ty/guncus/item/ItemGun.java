package de.cas_ual_ty.guncus.item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import de.cas_ual_ty.guncus.GunCus;
import de.cas_ual_ty.guncus.entity.EntityBullet;
import de.cas_ual_ty.guncus.item.attachments.Ammo;
import de.cas_ual_ty.guncus.item.attachments.Auxiliary;
import de.cas_ual_ty.guncus.item.attachments.Barrel;
import de.cas_ual_ty.guncus.item.attachments.EnumAttachmentType;
import de.cas_ual_ty.guncus.item.attachments.Magazine;
import de.cas_ual_ty.guncus.item.attachments.Underbarrel;
import de.cas_ual_ty.guncus.itemgroup.ItemGroupGun;
import de.cas_ual_ty.guncus.registries.GunCusEntityTypes;
import de.cas_ual_ty.guncus.registries.GunCusSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ItemGun extends Item
{
    public static final ArrayList<ItemGun> GUNS_LIST = new ArrayList<ItemGun>();
    
    public static final String NBT_RELOAD_TIME = "ReloadTime";
    public static final String NBT_AMMO = "Ammo";
    public static final String NBT_ACCESSORY_SWITCH = "AccessorySwitch";
    
    protected int fireRate;
    protected int maxAmmo;
    protected float baseDamage;
    protected Supplier<ItemBullet> bullet;
    protected Supplier<Integer> switchTime;
    protected int reloadTime;
    
    protected Supplier<SoundEvent> soundShoot;
    protected Supplier<SoundEvent> soundShootSilenced;
    protected Supplier<SoundEvent> soundReload;
    
    public Supplier<ItemAttachment[][]> supplierAttachments;
    protected ItemAttachment[][] attachments;
    
    public ItemGroupGun gunTab;
    
    public ItemGun(Properties properties, int fireRate, int maxAmmo, float baseDamage, Supplier<ItemBullet> bullet)
    {
        super(properties);
        
        this.fireRate = fireRate;
        this.maxAmmo = maxAmmo;
        this.baseDamage = baseDamage;
        this.bullet = bullet;
        this.switchTime = () -> this.getBaseFireRate() * 3;
        this.reloadTime = 100;
        
        this.supplierAttachments = ItemAttachment::buildDefaultArray;
        this.attachments = null;
        
        this.soundShoot = () -> GunCusSoundEvents.SHOOT;
        this.soundShootSilenced = () -> GunCusSoundEvents.SHOOT_SILENCED;
        this.soundReload = () -> GunCusSoundEvents.RELOAD;
        
        this.gunTab = null;
        
        ItemGun.GUNS_LIST.add(this);
    }
    
    public ItemGun createGunTab(String label)
    {
        this.gunTab = new ItemGroupGun(label, this);
        return this;
    }
    
    public int getBaseFireRate()
    {
        return this.fireRate;
    }
    
    public int getBaseMaxAmmo()
    {
        return this.maxAmmo;
    }
    
    public float getBaseDamage()
    {
        return this.baseDamage;
    }
    
    public ItemBullet getBaseBullet()
    {
        return this.bullet.get();
    }
    
    public int getBaseSwitchTime()
    {
        return this.switchTime.get().intValue();
    }
    
    public int getBaseReloadTime()
    {
        return this.reloadTime;
    }
    
    public ItemBullet calcCurrentBullet(ItemAttachment[] attachments)
    {
        return ((Ammo) attachments[EnumAttachmentType.AMMO.getSlot()]).getUsedBullet(this);
    }
    
    public int calcCurrentMaxAmmo(ItemAttachment[] attachments)
    {
        return ((Magazine) attachments[EnumAttachmentType.MAGAZINE.getSlot()]).getExtraCapacity() + this.getBaseMaxAmmo();
    }
    
    public int calcCurrentFireRate(ItemAttachment[] attachments)
    {
        return Math.max(1, this.getBaseFireRate() - ((Auxiliary) attachments[EnumAttachmentType.AUXILIARY.getSlot()]).getExtraFireRare());
    }
    
    public int calcCurrentReloadTime(ItemAttachment[] attachments)
    {
        return (int) (((Magazine) attachments[EnumAttachmentType.MAGAZINE.getSlot()]).getReloadTimeModifier() * this.getBaseReloadTime());
    }
    
    public int calcCurrentSwitchTime(ItemAttachment[] attachments)
    {
        return this.getBaseSwitchTime();
    }
    
    public ItemGun setAttachments(Supplier<ItemAttachment[][]> attachments)
    {
        this.supplierAttachments = attachments;
        this.attachments = null;
        return this;
    }
    
    public ItemAttachment[][] getAttachments()
    {
        if (this.attachments == null)
        {
            this.attachments = this.supplierAttachments.get();
        }
        
        return this.attachments;
    }
    
    public ItemAttachment getAttachment(EnumAttachmentType type, int index)
    {
        return this.getAttachments()[type.getSlot()][index];
    }
    
    public int getIndexForAttachment(ItemAttachment attachment)
    {
        for (int i = 0; i < this.getAttachments()[attachment.getSlot()].length; ++i)
        {
            if (this.getAttachments()[attachment.getSlot()][i] == attachment)
            {
                return i;
            }
        }
        
        return -1;
    }
    
    public int getAmmountForSlot(EnumAttachmentType type)
    {
        return this.getAttachments()[type.getSlot()].length;
    }
    
    public boolean isSlotAvailable(EnumAttachmentType type)
    {
        return this.getAttachments()[type.getSlot()].length > 1;
    }
    
    public ItemStack getAttachmentStack(ItemStack itemStack, EnumAttachmentType type)
    {
        ItemAttachment attachment = this.getAttachment(itemStack, type);
        return attachment.isDefault() || attachment == this.getAttachments()[type.getSlot()][0] ? ItemStack.EMPTY : new ItemStack(attachment);
    }
    
    public ItemAttachment getAttachment(ItemStack itemStack, EnumAttachmentType type)
    {
        return this.getAttachment(type, this.getNBT(itemStack).getInt(type.getKey()));
    }
    
    public ItemStack getAttachmentItemStack(ItemStack itemStack, EnumAttachmentType type)
    {
        ItemAttachment attachment = this.getAttachment(itemStack, type);
        return attachment.isDefault() ? ItemStack.EMPTY : new ItemStack(attachment);
    }
    
    public ItemStack setAttachment(ItemStack itemStack, ItemAttachment attachment)
    {
        this.getNBT(itemStack).putInt(attachment.getType().getKey(), this.getIndexForAttachment(attachment));
        return itemStack;
    }
    
    public ItemStack setAttachments(ItemStack itemStack, ItemAttachment[] attachments)
    {
        for (ItemAttachment attachment : attachments)
        {
            this.setAttachment(itemStack, attachment);
        }
        return itemStack;
    }
    
    public ItemStack createVariant(ItemAttachment[] attachments)
    {
        return this.setAttachments(new ItemStack(this), attachments);
    }
    
    public boolean canSetAttachment(ItemAttachment attachment)
    {
        return this.getIndexForAttachment(attachment) >= 0;
    }
    
    @SuppressWarnings("unchecked")
    public <A extends ItemAttachment> A getAttachmentCalled(ItemStack itemStack, EnumAttachmentType type)
    {
        return (A) this.getAttachment(itemStack, type);
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        return new ActionResult<ItemStack>(ActionResultType.PASS, playerIn.getHeldItem(handIn));
    }
    
    @Override
    public void inventoryTick(ItemStack itemStack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        if (entity instanceof PlayerEntity)
        {
            PlayerEntity entityPlayer = (PlayerEntity) entity;
            
            int value = this.getNBTCurrentReloadTime(itemStack);
            
            if (value > 0)
            {
                ItemAttachment[] attachments = this.getCurrentAttachments(itemStack);
                
                if (isSelected)
                {
                    if (value == this.calcCurrentReloadTime(attachments))
                    {
                        entityPlayer.playSound(this.soundReload.get(), 1F, 1F);
                    }
                    
                    this.setNBTCurrentReloadTime(itemStack, --value);
                }
                else
                {
                    // Refresh reloading in case the player did not finish it
                    this.setNBTCurrentReloadTime(itemStack, this.calcCurrentReloadTime(attachments));
                }
            }
        }
    }
    
    public ActionResultType tryShootOrReload(PlayerEntity entityPlayer, Hand hand, ItemStack itemStack, boolean aiming, boolean moving, int inaccuracy)
    {
        ItemAttachment[] attachments = this.getCurrentAttachments(itemStack);
        
        if (this.getNBTCurrentReloadTime(itemStack) <= 0)
        {
            if (this.canShoot(entityPlayer, hand, attachments, itemStack, aiming, moving, inaccuracy))
            {
                return this.doShoot(entityPlayer, hand, attachments, itemStack, aiming, moving, inaccuracy);
            }
            else if (this.canReload(entityPlayer, hand, attachments, itemStack, aiming, moving, inaccuracy))
            {
                return this.tryReload(entityPlayer, attachments, itemStack);
            }
        }
        
        return ActionResultType.PASS;
    }
    
    public boolean canShoot(PlayerEntity entityPlayer, Hand hand, ItemAttachment[] attachments, ItemStack itemStack, boolean aiming, boolean moving, int inaccuracy)
    {
        return this.getNBTCurrentAmmo(itemStack) > 0 || entityPlayer.isCreative();
    }
    
    public boolean canReload(PlayerEntity entityPlayer, Hand hand, ItemAttachment[] attachments, ItemStack itemStack, boolean aiming, boolean moving, int inaccuracy)
    {
        
        return true;
    }
    
    public ActionResultType doShoot(PlayerEntity entityPlayer, Hand hand, ItemAttachment[] attachments, ItemStack itemStack, boolean aiming, boolean moving, int inaccuracyInt)
    {
        float extraDamage = 0F;
        float driftModifier = 1F;
        float speedModifier = 1F;
        float spreadModifierVertical = 1F;
        float spreadModifierHorizontal = 1F;
        float inaccuracyModifier = 1F;
        float inaccuracyModifierMoving = 1F;
        float inaccuracyModifierStill = 1F;
        
        for (ItemAttachment attachment : attachments)
        {
            extraDamage += attachment.getExtraDamage();
            driftModifier *= attachment.getDriftModifier();
            speedModifier *= attachment.getSpeedModifier();
            spreadModifierVertical *= attachment.getSpreadModifierVertical();
            spreadModifierHorizontal *= attachment.getSpreadModifierHorizontal();
            inaccuracyModifier *= attachment.getInaccuracyModifier();
            inaccuracyModifierMoving *= attachment.getInaccuracyModifierMoving();
            inaccuracyModifierStill *= attachment.getInaccuracyModifierStill();
        }
        
        ItemBullet bullet = this.calcCurrentBullet(attachments);
        
        float inaccuracy = inaccuracyInt * inaccuracyModifier;
        float speed = 30F * speedModifier;
        float damage = this.getBaseDamage() + extraDamage + bullet.getExtraDamage();
        
        if (aiming)
        {
            inaccuracy *= 0.5F;
        }
        
        if (moving)
        {
            inaccuracy *= inaccuracyModifierMoving;
        }
        else
        {
            inaccuracy *= inaccuracyModifierStill;
            
            if (entityPlayer.isSneaking())
            {
                inaccuracy *= ((Underbarrel) attachments[EnumAttachmentType.UNDERBARREL.getSlot()]).getInaccuracyModifierShiftStill();
                driftModifier *= ((Underbarrel) attachments[EnumAttachmentType.UNDERBARREL.getSlot()]).getDriftModifierShiftStill();
            }
        }
        
        if (!entityPlayer.world.isRemote)
        {
            float rotationPitch;
            float rotationYaw;
            EntityBullet bulletEntity;
            
            for (int i = 0; i < bullet.getProjectileAmount(); ++i)
            {
                rotationPitch = entityPlayer.rotationPitch + (Item.random.nextFloat() * 2 - 1) * spreadModifierVertical * inaccuracy * bullet.getSpreadModifier();
                rotationYaw = entityPlayer.rotationYaw + (Item.random.nextFloat() * 2 - 1) * spreadModifierHorizontal * inaccuracy * bullet.getSpreadModifier();
                
                bulletEntity = new EntityBullet(GunCusEntityTypes.TYPE_BULLET, entityPlayer, entityPlayer.world);
                bulletEntity.setPosition(entityPlayer.posX, entityPlayer.posY + entityPlayer.getEyeHeight(), entityPlayer.posZ);
                bulletEntity.shoot(entityPlayer, rotationPitch, rotationYaw, 0, speed, 0);
                bulletEntity.setGravity(bullet.getGravity());
                entityPlayer.world.addEntity(bulletEntity.setDamage(damage));
            }
            
            if (!entityPlayer.isCreative())
            {
                this.setNBTCurrentAmmo(itemStack, this.getNBTCurrentAmmo(itemStack) - 1);
            }
        }
        else
        {
            entityPlayer.rotationPitch -= Item.random.nextFloat() * driftModifier;
            entityPlayer.rotationYaw += (Item.random.nextFloat() * 2 - 1) * driftModifier;
        }
        
        if (((Barrel) attachments[EnumAttachmentType.BARREL.getSlot()]).getIsSilenced())
        {
            entityPlayer.playSound(this.soundShootSilenced.get(), 3.0F, 0.9F + Item.random.nextFloat() * 0.2F);
        }
        else
        {
            entityPlayer.playSound(this.soundShoot.get(), 3.0F, 0.9F + Item.random.nextFloat() * 0.2F);
        }
        
        GunCus.proxy.shot(itemStack, this, entityPlayer, hand);
        
        return ActionResultType.SUCCESS;
    }
    
    public ActionResultType tryReload(PlayerEntity entityPlayer, ItemAttachment[] attachments, ItemStack itemStack)
    {
        ItemBullet bullet = this.calcCurrentBullet(attachments);
        
        int maxAmmo = this.calcCurrentMaxAmmo(attachments);
        int ammount = 0;
        int needed;
        
        ItemStack i;
        for (int index = 0; index < entityPlayer.inventory.getSizeInventory(); ++index)
        {
            i = entityPlayer.inventory.getStackInSlot(index);
            
            if (i.getItem() == bullet)
            {
                needed = maxAmmo - ammount;
                
                if (i.getCount() > needed)
                {
                    i.setCount(i.getCount() - needed);
                    ammount += needed;
                }
                else
                {
                    ammount += i.getCount();
                    i.setCount(0);
                }
            }
            
            if (ammount >= maxAmmo)
            {
                break;
            }
        }
        
        if (ammount > 0)
        {
            return this.doReload(entityPlayer, attachments, itemStack, ammount);
        }
        
        return ActionResultType.PASS;
    }
    
    public ActionResultType doReload(PlayerEntity entityPlayer, ItemAttachment[] attachments, ItemStack itemStack, int ammount)
    {
        if (!entityPlayer.world.isRemote)
        {
            this.setNBTCurrentAmmo(itemStack, ammount);
            this.setNBTCurrentReloadTime(itemStack, this.calcCurrentReloadTime(attachments));
        }
        
        return ActionResultType.SUCCESS;
    }
    
    public boolean isNBTAccessoryTurnedOn(ItemStack itemStack)
    {
        if (!this.getNBT(itemStack).contains(ItemGun.NBT_ACCESSORY_SWITCH))
        {
            this.setNBTAccessoryTurnedOn(itemStack, true);
        }
        
        return this.getNBT(itemStack).getBoolean(ItemGun.NBT_ACCESSORY_SWITCH);
    }
    
    public void setNBTAccessoryTurnedOn(ItemStack itemStack, boolean b)
    {
        this.getNBT(itemStack).putBoolean(ItemGun.NBT_ACCESSORY_SWITCH, b);
    }
    
    public int getNBTCurrentReloadTime(ItemStack itemStack)
    {
        return this.getNBT(itemStack).getInt(ItemGun.NBT_RELOAD_TIME);
    }
    
    public void setNBTCurrentReloadTime(ItemStack itemStack, int i)
    {
        this.getNBT(itemStack).putInt(ItemGun.NBT_RELOAD_TIME, i);
    }
    
    public boolean getNBTCanAim(ItemStack itemStack)
    {
        return !this.getNBTIsReloading(itemStack);
    }
    
    public boolean getNBTIsReloading(ItemStack itemStack)
    {
        return this.getNBTCurrentReloadTime(itemStack) > 0;
    }
    
    public int getNBTCurrentAmmo(ItemStack itemStack)
    {
        return this.getNBT(itemStack).getInt(ItemGun.NBT_AMMO);
    }
    
    public void setNBTCurrentAmmo(ItemStack itemStack, int i)
    {
        this.getNBT(itemStack).putInt(ItemGun.NBT_AMMO, i);
    }
    
    public CompoundNBT getNBT(ItemStack itemStack)
    {
        return itemStack.getOrCreateTag();
    }
    
    public ItemAttachment[] getCurrentAttachments(ItemStack itemStack)
    {
        ItemAttachment[] attachments = new ItemAttachment[EnumAttachmentType.LENGTH];
        
        for (EnumAttachmentType type : EnumAttachmentType.VALUES)
        {
            attachments[type.getSlot()] = this.getAttachment(itemStack, type);
        }
        
        return attachments;
    }
    
    @Override
    public boolean onEntitySwing(ItemStack itemStack, LivingEntity entityLivingBase)
    {
        return true;
    }
    
    @Override
    public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player)
    {
        return !player.isCreative();
    }
    
    @Override
    public void addInformation(ItemStack itemStack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(itemStack, world, list, flag);
        
        ItemAttachment[] attachments = this.getCurrentAttachments(itemStack);
        
        int maxAmmo = this.calcCurrentMaxAmmo(attachments);
        ItemBullet bullet = this.calcCurrentBullet(attachments);
        
        ITextComponent tAmmo = new StringTextComponent(this.getNBTCurrentAmmo(itemStack) + "").setStyle(new Style().setColor(TextFormatting.YELLOW));
        ITextComponent tMaxAmmo = new StringTextComponent("/" + maxAmmo);
        ITextComponent tBullet = new TranslationTextComponent(bullet.getTranslationKey()).setStyle(new Style().setColor(TextFormatting.YELLOW));
        
        list.add(new StringTextComponent(tAmmo.getFormattedText() + tMaxAmmo.getFormattedText() + " " + tBullet.getFormattedText()));
        
        ItemAttachment attachment;
        int ammount = 0;
        int i;
        
        for (i = 0; i < attachments.length; ++i)
        {
            if (!attachments[i].isDefault())
            {
                ++ammount;
            }
        }
        
        if (ammount > 0)
        {
            list.add(ItemAttachment.getAttachmentTranslated(ammount > 1).setStyle(new Style().setColor(TextFormatting.DARK_GRAY)).appendText(":"));
            for (EnumAttachmentType type : EnumAttachmentType.VALUES)
            {
                if (this.isSlotAvailable(type))
                {
                    attachment = this.getAttachment(itemStack, type);
                    
                    if (attachment != null)
                    {
                        list.add(new StringTextComponent(attachment.getInformationString().getFormattedText() + ItemGun.getSlotDisplaySuffix(type)));
                    }
                    else
                    {
                        list.add(new StringTextComponent("--" + ItemGun.getSlotDisplaySuffix(type)));
                    }
                }
            }
        }
    }
    
    public static void tryShoot(PlayerEntity player, boolean aiming, int inaccuracy, Hand[] hands)
    {
        ItemStack item;
        
        for (Hand hand : hands)
        {
            item = player.getHeldItem(hand);
            
            if (item.getItem() instanceof ItemGun)
            {
                ((ItemGun) item.getItem()).tryShootOrReload(player, hand, item, aiming, player.getMotion().lengthSquared() > 0, inaccuracy);
            }
        }
    }
    
    public static String getSlotDisplaySuffix(EnumAttachmentType type)
    {
        return new StringTextComponent(" (" + type.getDisplayName().getFormattedText() + ")").setStyle(new Style().setColor(TextFormatting.DARK_GRAY)).getFormattedText();
    }
}
