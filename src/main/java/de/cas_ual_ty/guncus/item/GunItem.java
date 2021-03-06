package de.cas_ual_ty.guncus.item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import de.cas_ual_ty.guncus.GunCus;
import de.cas_ual_ty.guncus.entity.BulletEntity;
import de.cas_ual_ty.guncus.item.attachments.Ammo;
import de.cas_ual_ty.guncus.item.attachments.Auxiliary;
import de.cas_ual_ty.guncus.item.attachments.Barrel;
import de.cas_ual_ty.guncus.item.attachments.EnumAttachmentType;
import de.cas_ual_ty.guncus.item.attachments.Magazine;
import de.cas_ual_ty.guncus.item.attachments.Underbarrel;
import de.cas_ual_ty.guncus.itemgroup.GunItemGroup;
import de.cas_ual_ty.guncus.registries.GunCusEntityTypes;
import de.cas_ual_ty.guncus.registries.GunCusSoundEvents;
import de.cas_ual_ty.guncus.util.GunCusUtility;
import de.cas_ual_ty.guncus.util.RandomTradeBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class GunItem extends MakerItem
{
    public static final List<GunItem> ALL_GUNS_LIST = new ArrayList<>();
    public static final List<MakerItem> MAKER_GUNS_LIST = new ArrayList<>();
    
    public static final String NBT_RELOAD_TIME = "ReloadTime";
    public static final String NBT_RELOAD_TYPE = "ReloadType";
    public static final String NBT_AMMO = "Ammo";
    public static final String NBT_ACCESSORY_SWITCH = "AccessorySwitch";
    
    protected int fireRate;
    protected int maxAmmo;
    protected float baseDamage;
    protected Supplier<BulletItem> bullet;
    protected Supplier<Integer> switchTime;
    protected int reloadTime;
    protected EnumFireMode fireMode;
    protected EnumReloadType fullReloadType;
    
    protected Supplier<SoundEvent> soundShoot;
    protected Supplier<SoundEvent> soundShootSilenced;
    protected Supplier<SoundEvent> soundReload;
    protected Supplier<SoundEvent> soundReloadBolt;
    
    public Supplier<AttachmentItem[][]> supplierAttachments;
    protected AttachmentItem[][] attachments;
    protected int variantsAmt;
    
    public GunItemGroup gunTab;
    
    public GunItem(Properties properties, int fireRate, int maxAmmo, float baseDamage, Supplier<BulletItem> bullet, int craftAmount, ItemStack... materials)
    {
        super(properties, craftAmount, materials);
        
        this.fireRate = fireRate;
        this.maxAmmo = maxAmmo;
        this.baseDamage = baseDamage;
        this.bullet = bullet;
        this.switchTime = () -> this.getBaseFireRate() * 3;
        this.reloadTime = 100;
        this.fireMode = EnumFireMode.FULL_AUTO;
        this.fullReloadType = EnumReloadType.MAGAZINE;
        
        this.supplierAttachments = AttachmentItem::buildDefaultArray;
        this.attachments = null;
        this.variantsAmt = -1;
        
        this.soundShoot = () -> GunCusSoundEvents.SHOOT;
        this.soundShootSilenced = () -> GunCusSoundEvents.SHOOT_SILENCED;
        this.soundReload = () -> GunCusSoundEvents.RELOAD;
        
        this.gunTab = null;
        
        if(craftAmount > 0 && materials.length > 0)
        {
            GunItem.MAKER_GUNS_LIST.add(this);
        }
        
        GunItem.ALL_GUNS_LIST.add(this);
    }
    
    public GunItem(Properties properties, int fireRate, int maxAmmo, float baseDamage, Supplier<BulletItem> bullet, int iron, int gold, int redstone)
    {
        this(properties, fireRate, maxAmmo, baseDamage, bullet, 1, GunItem.makeMaterialsParam(iron, gold, redstone));
    }
    
    public GunItem setDefaultTradeable(int price, int level)
    {
        new RandomTradeBuilder(1, 25 * level, 0.1F).setEmeraldPriceFor(price, this, 1).registerLevel(level);
        return this;
    }
    
    public GunItem createGunTab(String label)
    {
        this.gunTab = new GunItemGroup(label, this);
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
    
    public BulletItem getBaseBullet()
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
    
    public EnumFireMode getBaseFireMode()
    {
        return this.fireMode;
    }
    
    public EnumReloadType getBaseFullReloadType()
    {
        return this.fullReloadType;
    }
    
    public BulletItem calcCurrentBullet(AttachmentItem[] attachments)
    {
        return ((Ammo)attachments[EnumAttachmentType.AMMO.getSlot()]).getUsedBullet(this);
    }
    
    public int calcCurrentMaxAmmo(AttachmentItem[] attachments)
    {
        return ((Magazine)attachments[EnumAttachmentType.MAGAZINE.getSlot()]).getExtraCapacity() + this.getBaseMaxAmmo();
    }
    
    public int calcCurrentFireRate(AttachmentItem[] attachments)
    {
        return Math.max(1, this.getBaseFireRate() - ((Auxiliary)attachments[EnumAttachmentType.AUXILIARY.getSlot()]).getExtraFireRare());
    }
    
    public int calcCurrentReloadTime(AttachmentItem[] attachments)
    {
        return (int)(((Magazine)attachments[EnumAttachmentType.MAGAZINE.getSlot()]).getReloadTimeModifier() * this.getBaseReloadTime());
    }
    
    public boolean calcIsAllowingReloadWhileZoomed(AttachmentItem[] attachments)
    {
        return ((Auxiliary)attachments[EnumAttachmentType.AUXILIARY.getSlot()]).getIsAllowingReloadWhileZoomed();
    }
    
    public int calcCurrentSwitchTime(AttachmentItem[] attachments)
    {
        return this.getBaseSwitchTime();
    }
    
    public EnumFireMode calcCurrentFireMode(AttachmentItem[] attachments)
    {
        return ((Auxiliary)attachments[EnumAttachmentType.AUXILIARY.getSlot()]).getForceFullAuto() ? EnumFireMode.FULL_AUTO : this.getBaseFireMode();
    }
    
    public EnumFireMode getCurrentFireMode(ItemStack itemStack)
    {
        return this.calcCurrentFireMode(this.getCurrentAttachments(itemStack));
    }
    
    public GunItem setSoundShoot(Supplier<SoundEvent> soundShoot)
    {
        this.soundShoot = soundShoot;
        return this;
    }
    
    public GunItem setSoundShootSilenced(Supplier<SoundEvent> soundShootSilenced)
    {
        this.soundShootSilenced = soundShootSilenced;
        return this;
    }
    
    public GunItem setSoundReload(Supplier<SoundEvent> soundReload)
    {
        this.soundReload = soundReload;
        return this;
    }
    
    public GunItem setBoltAction(Supplier<SoundEvent> soundReloadBolt)
    {
        this.soundReloadBolt = soundReloadBolt;
        return this.setFireMode(EnumFireMode.BOLT_ACTION);
    }
    
    public GunItem setSemiAuto()
    {
        return this.setFireMode(EnumFireMode.SEMI_AUTO);
    }
    
    protected GunItem setFireMode(EnumFireMode fireMode)
    {
        this.fireMode = fireMode;
        return this;
    }
    
    public GunItem setAttachments(Supplier<AttachmentItem[][]> attachments)
    {
        this.supplierAttachments = attachments;
        this.attachments = null;
        return this;
    }
    
    public AttachmentItem[][] getAttachments()
    {
        if(this.attachments == null)
        {
            this.attachments = this.supplierAttachments.get();
        }
        
        return this.attachments;
    }
    
    public int getVariantsAmt()
    {
        if(this.variantsAmt == -1)
        {
            AttachmentItem[][] attachments = this.getAttachments();
            this.variantsAmt = 0;
            for(AttachmentItem[] row : attachments)
            {
                this.variantsAmt += row.length;
            }
        }
        
        return this.variantsAmt;
    }
    
    public AttachmentItem getAttachment(EnumAttachmentType type, int index)
    {
        return this.getAttachments()[type.getSlot()][index];
    }
    
    public int getIndexForAttachment(AttachmentItem attachment)
    {
        for(int i = 0; i < this.getAttachments()[attachment.getSlot()].length; ++i)
        {
            if(this.getAttachments()[attachment.getSlot()][i] == attachment)
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
        AttachmentItem attachment = this.getAttachment(itemStack, type);
        return attachment.isDefault() || attachment == this.getAttachments()[type.getSlot()][0] ? ItemStack.EMPTY : new ItemStack(attachment);
    }
    
    public AttachmentItem getAttachment(ItemStack itemStack, EnumAttachmentType type)
    {
        return this.getAttachment(type, this.getNBT(itemStack).getInt(type.getKey()));
    }
    
    public ItemStack getAttachmentItemStack(ItemStack itemStack, EnumAttachmentType type)
    {
        AttachmentItem attachment = this.getAttachment(itemStack, type);
        return attachment.isDefault() ? ItemStack.EMPTY : new ItemStack(attachment);
    }
    
    public ItemStack setAttachment(ItemStack itemStack, AttachmentItem attachment)
    {
        this.getNBT(itemStack).putInt(attachment.getType().getKey(), this.getIndexForAttachment(attachment));
        return itemStack;
    }
    
    public ItemStack setAttachments(ItemStack itemStack, AttachmentItem[] attachments)
    {
        for(AttachmentItem attachment : attachments)
        {
            this.setAttachment(itemStack, attachment);
        }
        return itemStack;
    }
    
    public ItemStack createVariant(AttachmentItem[] attachments)
    {
        return this.setAttachments(new ItemStack(this), attachments);
    }
    
    public boolean canSetAttachment(AttachmentItem attachment)
    {
        return this.getIndexForAttachment(attachment) >= 0;
    }
    
    @SuppressWarnings("unchecked")
    public <A extends AttachmentItem> A getAttachmentCalled(ItemStack itemStack, EnumAttachmentType type)
    {
        return (A)this.getAttachment(itemStack, type);
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        return new ActionResult<>(ActionResultType.PASS, playerIn.getHeldItem(handIn));
    }
    
    @Override
    public void inventoryTick(ItemStack itemStack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        if(entity instanceof PlayerEntity)
        {
            PlayerEntity entityPlayer = (PlayerEntity)entity;
            boolean isInAnyHand = false;
            
            for(Hand hand : GunCusUtility.HANDS)
            {
                if(entityPlayer.getHeldItem(hand) == itemStack)
                {
                    isInAnyHand = true;
                    break;
                }
            }
            
            int value = this.getNBTCurrentReloadTime(itemStack);
            
            if(value > 0)
            {
                EnumReloadType type = this.getNBTCurrentReloadType(itemStack);
                AttachmentItem[] attachments = this.getCurrentAttachments(itemStack);
                boolean hasAmmo = this.getHasAmmo(entityPlayer, attachments, itemStack);
                
                if(type == EnumReloadType.MAGAZINE)
                {
                    if(!isInAnyHand || !hasAmmo)
                    {
                        value = 0;
                    }
                    else if(--value == 0)
                    {
                        this.tryFinishReload(entityPlayer, attachments, itemStack);
                    }
                }
                else if(type == EnumReloadType.BOLT)
                {
                    if(value == this.getBaseFireRate() && isInAnyHand)
                    {
                        --value;
                        entityPlayer.playSound(this.soundReloadBolt.get(), 1F, 1F);
                    }
                    else if(--value > 0 && !isInAnyHand)
                    {
                        value = this.getBaseFireRate();
                    }
                }
                
                this.setNBTCurrentReloadTime(itemStack, value);
            }
        }
    }
    
    public ActionResultType tryShootOrReload(PlayerEntity entityPlayer, Hand hand, ItemStack itemStack, boolean aiming, boolean moving, int inaccuracy)
    {
        AttachmentItem[] attachments = this.getCurrentAttachments(itemStack);
        
        if(this.getNBTCurrentReloadTime(itemStack) <= 0)
        {
            if(this.canShoot(entityPlayer, hand, attachments, itemStack, aiming, moving, inaccuracy))
            {
                return this.doShoot(entityPlayer, hand, attachments, itemStack, aiming, moving, inaccuracy);
            }
            else if(this.canReload(entityPlayer, hand, attachments, itemStack, aiming, moving, inaccuracy))
            {
                return this.tryStartReload(entityPlayer, attachments, itemStack);
            }
        }
        
        return ActionResultType.PASS;
    }
    
    public boolean canShoot(PlayerEntity entityPlayer, Hand hand, AttachmentItem[] attachments, ItemStack itemStack, boolean aiming, boolean moving, int inaccuracy)
    {
        return this.getNBTCurrentAmmo(itemStack) > 0 || entityPlayer.isCreative();
    }
    
    public boolean canReload(PlayerEntity entityPlayer, Hand hand, AttachmentItem[] attachments, ItemStack itemStack, boolean aiming, boolean moving, int inaccuracy)
    {
        return this.getHasAmmo(entityPlayer, attachments, itemStack) && (this.getBaseFireMode() != EnumFireMode.BOLT_ACTION || !aiming || this.calcIsAllowingReloadWhileZoomed(attachments));
    }
    
    public boolean getHasAmmo(PlayerEntity entityPlayer, AttachmentItem[] attachments, ItemStack itemStack)
    {
        BulletItem bullet = this.calcCurrentBullet(attachments);
        
        for(int index = 0; index < entityPlayer.inventory.getSizeInventory(); ++index)
        {
            if(entityPlayer.inventory.getStackInSlot(index).getItem() == bullet)
            {
                return true;
            }
        }
        
        return false;
    }
    
    public ActionResultType doShoot(PlayerEntity entityPlayer, Hand hand, AttachmentItem[] attachments, ItemStack itemStack, boolean aiming, boolean moving, int inaccuracyInt)
    {
        float extraDamage = 0F;
        float driftModifier = 1F;
        float speedModifier = 1F;
        float spreadModifierVertical = 1F;
        float spreadModifierHorizontal = 1F;
        float inaccuracyModifier = 1F;
        float inaccuracyModifierMoving = 1F;
        float inaccuracyModifierStill = 1F;
        
        for(AttachmentItem attachment : attachments)
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
        
        BulletItem bullet = this.calcCurrentBullet(attachments);
        
        //limit max unmodified inaccuracy to +- 7.5 degree
        float speed = BulletEntity.BASE_SPEED * speedModifier;
        float damage = this.getBaseDamage() + extraDamage + bullet.getExtraDamage();
        
        float inaccuracy;
        
        if(bullet.getProjectileAmount() == 1)
        {
            inaccuracy = Math.min(inaccuracyInt, 7.5F) * inaccuracyModifier;
            
            if(aiming)
            {
                inaccuracy *= 0.5F;
            }
            
            if(moving)
            {
                inaccuracy *= inaccuracyModifierMoving;
            }
            else
            {
                inaccuracy *= inaccuracyModifierStill;
                
                if(entityPlayer.isSteppingCarefully())
                {
                    inaccuracy *= ((Underbarrel)attachments[EnumAttachmentType.UNDERBARREL.getSlot()]).getInaccuracyModifierShiftStill();
                    driftModifier *= ((Underbarrel)attachments[EnumAttachmentType.UNDERBARREL.getSlot()]).getDriftModifierShiftStill();
                }
            }
        }
        else
        {
            inaccuracy = 1F;
        }
        
        if(!entityPlayer.world.isRemote)
        {
            float rotationPitch;
            float rotationYaw;
            BulletEntity bulletEntity;
            
            for(int i = 0; i < bullet.getProjectileAmount(); ++i)
            {
                float randomPitch = (Item.random.nextFloat() * 2 - 1) * spreadModifierVertical * inaccuracy * bullet.getSpreadModifier();
                float randomYaw = (Item.random.nextFloat() * 2 - 1) * spreadModifierHorizontal * inaccuracy * bullet.getSpreadModifier();
                rotationPitch = entityPlayer.rotationPitch + randomPitch;
                rotationYaw = entityPlayer.rotationYaw + randomYaw;
                
                bulletEntity = new BulletEntity(GunCusEntityTypes.BULLET, entityPlayer, entityPlayer.world, bullet);
                bulletEntity.setPosition(entityPlayer.getPosX(), entityPlayer.getPosY() + entityPlayer.getEyeHeight(), entityPlayer.getPosZ());
                bulletEntity.func_234612_a_(entityPlayer, rotationPitch, rotationYaw, 0, speed, 0);
                bulletEntity.setGravity(0);//bullet.getGravity()); // TODO temporary until there is a proper bullet renderer
                entityPlayer.world.addEntity(bulletEntity.setDamage(damage));
            }
            
            if(!entityPlayer.isCreative())
            {
                this.setNBTCurrentAmmo(itemStack, this.getNBTCurrentAmmo(itemStack) - 1);
            }
            
            if(this.getBaseFireMode() == EnumFireMode.BOLT_ACTION)
            {
                this.setNBTCurrentReloadTime(itemStack, this.getBaseFireRate());
                this.setNBTCurrentReloadType(itemStack, EnumReloadType.BOLT);
            }
        }
        else
        {
            entityPlayer.rotationPitch -= Item.random.nextFloat() * driftModifier;
            entityPlayer.rotationYaw += (Item.random.nextFloat() * 2 - 1) * driftModifier;
        }
        
        if(((Barrel)attachments[EnumAttachmentType.BARREL.getSlot()]).getIsSilenced())
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
    
    public ActionResultType tryStartReload(PlayerEntity entityPlayer, AttachmentItem[] attachments, ItemStack itemStack)
    {
        if(this.getHasAmmo(entityPlayer, attachments, itemStack))
        {
            return this.doStartReload(entityPlayer, attachments, itemStack);
        }
        
        return ActionResultType.PASS;
    }
    
    public ActionResultType doStartReload(PlayerEntity entityPlayer, AttachmentItem[] attachments, ItemStack itemStack)
    {
        if(!entityPlayer.world.isRemote)
        {
            this.setNBTCurrentReloadTime(itemStack, this.calcCurrentReloadTime(attachments));
            
            if(this.getBaseFireMode() == EnumFireMode.BOLT_ACTION)
            {
                this.setNBTCurrentReloadType(itemStack, this.getBaseFullReloadType());
            }
        }
        
        entityPlayer.playSound(this.soundReload.get(), 1F, 1F);
        
        return ActionResultType.SUCCESS;
    }
    
    public ActionResultType tryFinishReload(PlayerEntity entityPlayer, AttachmentItem[] attachments, ItemStack itemStack)
    {
        BulletItem bullet = this.calcCurrentBullet(attachments);
        
        int maxAmmo = this.calcCurrentMaxAmmo(attachments);
        int amount = this.getNBTCurrentAmmo(itemStack);
        int needed;
        
        ItemStack i;
        for(int index = 0; index < entityPlayer.inventory.getSizeInventory(); ++index)
        {
            i = entityPlayer.inventory.getStackInSlot(index);
            
            if(i.getItem() == bullet)
            {
                needed = maxAmmo - amount;
                
                if(i.getCount() > needed)
                {
                    i.setCount(i.getCount() - needed);
                    amount += needed;
                }
                else
                {
                    amount += i.getCount();
                    i.setCount(0);
                }
            }
            
            if(amount >= maxAmmo)
            {
                break;
            }
        }
        
        if(amount > 0)
        {
            return this.doFinishReload(entityPlayer, attachments, itemStack, amount);
        }
        
        return ActionResultType.PASS;
    }
    
    public ActionResultType doFinishReload(PlayerEntity entityPlayer, AttachmentItem[] attachments, ItemStack itemStack, int ammount)
    {
        if(!entityPlayer.world.isRemote)
        {
            this.setNBTCurrentAmmo(itemStack, ammount);
        }
        
        return ActionResultType.SUCCESS;
    }
    
    public boolean isNBTAccessoryTurnedOn(ItemStack itemStack)
    {
        if(!this.getNBT(itemStack).contains(GunItem.NBT_ACCESSORY_SWITCH))
        {
            this.setNBTAccessoryTurnedOn(itemStack, true);
        }
        
        return this.getNBT(itemStack).getBoolean(GunItem.NBT_ACCESSORY_SWITCH);
    }
    
    public void setNBTAccessoryTurnedOn(ItemStack itemStack, boolean b)
    {
        this.getNBT(itemStack).putBoolean(GunItem.NBT_ACCESSORY_SWITCH, b);
    }
    
    public boolean getNBTCanAimGun(ItemStack itemStack)
    {
        return !this.getNBTIsReloading(itemStack) || (this.getBaseFireMode() == EnumFireMode.BOLT_ACTION && this.calcIsAllowingReloadWhileZoomed(this.getCurrentAttachments(itemStack)));
    }
    
    public int getNBTCurrentReloadTime(ItemStack itemStack)
    {
        return this.getNBT(itemStack).getInt(GunItem.NBT_RELOAD_TIME);
    }
    
    public EnumReloadType getNBTCurrentReloadType(ItemStack itemStack)
    {
        return this.getBaseFireMode() == EnumFireMode.BOLT_ACTION ? EnumReloadType.get(this.getNBT(itemStack).getInt(GunItem.NBT_RELOAD_TYPE)) : EnumReloadType.MAGAZINE;
    }
    
    public void setNBTCurrentReloadTime(ItemStack itemStack, int i)
    {
        this.getNBT(itemStack).putInt(GunItem.NBT_RELOAD_TIME, i);
    }
    
    public void setNBTCurrentReloadType(ItemStack itemStack, EnumReloadType type)
    {
        // 0 Magazine, 1 Bolt
        this.getNBT(itemStack).putInt(GunItem.NBT_RELOAD_TYPE, type.index);
    }
    
    public boolean getNBTIsReloading(ItemStack itemStack)
    {
        return this.getNBTCurrentReloadTime(itemStack) > 0;
    }
    
    public int getNBTCurrentAmmo(ItemStack itemStack)
    {
        return this.getNBT(itemStack).getInt(GunItem.NBT_AMMO);
    }
    
    public void setNBTCurrentAmmo(ItemStack itemStack, int i)
    {
        this.getNBT(itemStack).putInt(GunItem.NBT_AMMO, i);
    }
    
    public CompoundNBT getNBT(ItemStack itemStack)
    {
        return itemStack.getOrCreateTag();
    }
    
    public AttachmentItem[] getCurrentAttachments(ItemStack itemStack)
    {
        AttachmentItem[] attachments = new AttachmentItem[EnumAttachmentType.LENGTH];
        
        for(EnumAttachmentType type : EnumAttachmentType.VALUES)
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
        
        AttachmentItem[] attachments = this.getCurrentAttachments(itemStack);
        
        int maxAmmo = this.calcCurrentMaxAmmo(attachments);
        BulletItem bullet = this.calcCurrentBullet(attachments);
        
        list.add(
            new StringTextComponent(this.getNBTCurrentAmmo(itemStack) + "").setStyle(Style.EMPTY.applyFormatting(TextFormatting.YELLOW))
                .append(new StringTextComponent("/" + maxAmmo).setStyle(Style.EMPTY.applyFormatting(TextFormatting.WHITE)))
                .appendString(" ")
                .append(new TranslationTextComponent(bullet.getTranslationKey()).setStyle(Style.EMPTY.applyFormatting(TextFormatting.YELLOW))));
        
        AttachmentItem attachment;
        int ammount = 0;
        int i;
        
        for(i = 0; i < attachments.length; ++i)
        {
            if(!attachments[i].isDefault())
            {
                ++ammount;
            }
        }
        
        if(ammount > 0)
        {
            list.add(AttachmentItem.getAttachmentTranslated(ammount != 1).appendString(":").setStyle(Style.EMPTY.applyFormatting(TextFormatting.DARK_GRAY)));
            
            for(EnumAttachmentType type : EnumAttachmentType.VALUES)
            {
                if(this.isSlotAvailable(type))
                {
                    attachment = this.getAttachment(itemStack, type);
                    
                    if(attachment != null)
                    {
                        list.add(attachment.getInformationString().append(GunItem.getSlotDisplaySuffix(type)));
                    }
                    else
                    {
                        list.add(new StringTextComponent("--").setStyle(Style.EMPTY.applyFormatting(TextFormatting.YELLOW)).append(GunItem.getSlotDisplaySuffix(type)));
                    }
                }
            }
        }
    }
    
    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return this.getNBTIsReloading(stack) || this.getNBTCurrentAmmo(stack) > 0;
    }
    
    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
        if(this.getNBTIsReloading(stack))
        {
            EnumReloadType type = this.getNBTCurrentReloadType(stack);
            
            if(type == EnumReloadType.BOLT)
            {
                return (double)this.getNBTCurrentReloadTime(stack) / this.getBaseFireRate();
            }
            
            return (double)this.getNBTCurrentReloadTime(stack) / this.calcCurrentReloadTime(this.getCurrentAttachments(stack));
        }
        else
        {
            return 1D - (double)this.getNBTCurrentAmmo(stack) / this.calcCurrentMaxAmmo(this.getCurrentAttachments(stack));
        }
    }
    
    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack)
    {
        return this.getNBTIsReloading(stack) ? 0x00FF0000 : 0x0000FF00;
    }
    
    public static void tryShoot(PlayerEntity player, boolean aiming, int inaccuracy, Hand[] hands)
    {
        ItemStack item;
        
        for(Hand hand : hands)
        {
            item = player.getHeldItem(hand);
            
            if(item.getItem() instanceof GunItem)
            {
                ((GunItem)item.getItem()).tryShootOrReload(player, hand, item, aiming, player.getMotion().lengthSquared() > 0, inaccuracy);
            }
        }
    }
    
    public static IFormattableTextComponent getSlotDisplaySuffix(EnumAttachmentType type)
    {
        return new StringTextComponent(" (").append(type.getDisplayName()).appendString(")").setStyle(Style.EMPTY.applyFormatting(TextFormatting.DARK_GRAY));
    }
    
    private static ItemStack[] makeMaterialsParam(int iron, int gold, int redstone)
    {
        if(iron > 0)
        {
            if(gold > 0)
            {
                if(redstone > 0)
                {
                    // Iron, Gold, Redstone
                    return new ItemStack[] { new ItemStack(Items.IRON_INGOT, iron), new ItemStack(Items.GOLD_INGOT, gold), new ItemStack(Items.REDSTONE, redstone) };
                }
                else
                {
                    // Iron, Gold
                    return new ItemStack[] { new ItemStack(Items.IRON_INGOT, iron), new ItemStack(Items.GOLD_INGOT, gold) };
                }
            }
            else
            {
                if(redstone > 0)
                {
                    // Iron Redstone
                    return new ItemStack[] { new ItemStack(Items.IRON_INGOT, iron), new ItemStack(Items.REDSTONE, redstone) };
                }
                else
                {
                    // Iron
                    return new ItemStack[] { new ItemStack(Items.IRON_INGOT, iron) };
                }
            }
        }
        else
        {
            if(gold > 0)
            {
                if(redstone > 0)
                {
                    // Gold, Redstone
                    return new ItemStack[] { new ItemStack(Items.GOLD_INGOT, gold), new ItemStack(Items.REDSTONE, redstone) };
                }
                else
                {
                    // Gold
                    return new ItemStack[] { new ItemStack(Items.GOLD_INGOT, gold) };
                }
            }
            else
            {
                if(redstone > 0)
                {
                    // Redstone
                    return new ItemStack[] { new ItemStack(Items.REDSTONE, redstone) };
                }
                else
                {
                    // Nothing
                    
                    return new ItemStack[] {};
                }
            }
        }
    }
    
    public static enum EnumFireMode
    {
        BOLT_ACTION("bolt_action"), SEMI_AUTO("semi_auto"), FULL_AUTO("full_auto");
        
        public final String key;
        
        private EnumFireMode(String key)
        {
            this.key = key;
        }
    }
    
    public static enum EnumReloadType
    {
        MAGAZINE(0), BOLT(1);
        
        public static final EnumReloadType[] VALUES = EnumReloadType.values();
        
        public final int index;
        
        private EnumReloadType(int index)
        {
            this.index = index;
        }
        
        public static EnumReloadType get(int index)
        {
            return EnumReloadType.VALUES[index];
        }
    }
}
