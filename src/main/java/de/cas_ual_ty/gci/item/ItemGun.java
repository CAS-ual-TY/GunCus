package de.cas_ual_ty.gci.item;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import de.cas_ual_ty.gci.CreativeTabsGCIGun;
import de.cas_ual_ty.gci.EntityBullet;
import de.cas_ual_ty.gci.GunCus;
import de.cas_ual_ty.gci.SoundEventGCI;
import de.cas_ual_ty.gci.item.attachment.Accessory;
import de.cas_ual_ty.gci.item.attachment.Ammo;
import de.cas_ual_ty.gci.item.attachment.Attachment;
import de.cas_ual_ty.gci.item.attachment.Auxiliary;
import de.cas_ual_ty.gci.item.attachment.Barrel;
import de.cas_ual_ty.gci.item.attachment.EnumAttachmentType;
import de.cas_ual_ty.gci.item.attachment.Underbarrel;
import de.cas_ual_ty.gci.network.MessageRecoil;
import de.cas_ual_ty.gci.network.MessageSound;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class ItemGun extends ItemGCI
{
	public static final ArrayList<ItemGun> GUNS_LIST = new ArrayList<ItemGun>();
	
	public static final String NBT_SHOOT_TIME = "ShootTime";
	public static final String NBT_AMMO = "Ammo";
	public static final String NBT_RELOADING = "Reloading";
	public static final String NBT_INACCURACY = "Inaccuracy";
	public static final String NBT_ATTACHMENT_PREFIX = "Attachment";
	public static final String NBT_ACCESSORY_SWITCH = "AccessorySwitch";
	
	protected int fireRate;
	protected int maxAmmo;
	protected float damage;
	protected ItemCartridge cartridge;
	
	protected CreativeTabs gunTab;
	
	protected boolean[][] attachments;
	
	protected SoundEventGCI soundShoot;
	protected SoundEventGCI soundShootSilenced;
	protected SoundEventGCI soundReload;
	
	public ItemGun(String rl, int fireRate, int maxAmmo, float damage, ItemCartridge cartridge)
	{
		super(rl);
		
		this.fireRate = fireRate;
		this.maxAmmo = maxAmmo;
		this.damage = damage;
		this.cartridge = cartridge;
		
		this.gunTab = new CreativeTabsGCIGun(rl, this);
		
		this.attachments = new boolean[EnumAttachmentType.values().length][];
		
		for(int i = 0; i < this.attachments.length; ++i)
		{
			this.attachments[i] = new boolean[Attachment.getAmmountForSlot(i)];
		}
		
		this.soundShoot = GunCus.SOUND_SHOOT;
		this.soundShootSilenced = GunCus.SOUND_SHOOT_SILENCED;
		this.soundReload = GunCus.SOUND_RELOAD;
		
		ItemGun.GUNS_LIST.add(this);
	}
	
	public ItemGun addAttachment(Attachment attachment)
	{
		return this.addAttachment(attachment.getSlot(), attachment.getID());
	}
	
	public ItemGun addAttachment(int slot, int id)
	{
		this.attachments[slot][id] = true;
		
		if(!this.isSlotAvailable(slot))
		{
			this.setSlotAvailable(slot);
		}
		
		return this;
	}
	
	public boolean isSlotAvailable(int slot)
	{
		return this.attachments[slot][0];
	}
	
	public void setSlotAvailable(int slot)
	{
		this.attachments[slot][0] = true;
	}
	
	public Item getCurrentlyUsedCardridge(ItemStack itemStack)
	{
		return this.<Ammo>getAttachmentCalled(itemStack, EnumAttachmentType.AMMO.getSlot()).getUsedCartrige(itemStack, this);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer entityPlayer, EnumHand hand)
	{
		return new ActionResult(EnumActionResult.PASS, entityPlayer.getHeldItem(hand));
	}
	
	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity entity, int idk, boolean flag)
	{
		int time = this.getShootTime(itemStack);
		
		if(time > 0)
		{
			this.setShootTime(itemStack, --time);
		}
		
		if(time == 0)
		{
			if(this.getIsReloading(itemStack))
			{
				this.setIsReloading(itemStack, false);
			}
			else
			{
				time = this.getInaccuracy(itemStack);
				
				if(time > 0)
				{
					this.setInaccuracy(itemStack, --time);
				}
			}
		}
	}
	
	public EnumActionResult tryShootOrReload(EntityPlayer entityPlayer, ItemStack itemStack, boolean aiming, boolean moving)
	{
		if(this.getShootTime(itemStack) <= 0)
		{
			if(this.getAmmo(itemStack) > 0 || entityPlayer.capabilities.isCreativeMode)
			{
				return this.doShoot(entityPlayer, itemStack, aiming, moving);
			}
			else
			{
				return this.tryReload(entityPlayer, itemStack);
			}
		}
		
		return EnumActionResult.PASS;
	}
	
	public EnumActionResult doShoot(EntityPlayer entityPlayer, ItemStack itemStack, boolean aiming, boolean moving)
	{
		if(!entityPlayer.world.isRemote)
		{
			EntityBullet bullet = new EntityBullet(entityPlayer.world, entityPlayer);
			bullet.setDamage(this.getDamage());
			
			int inaccuracyInt = this.getInaccuracy(itemStack);
			float inaccuracy = inaccuracyInt;
			float pitch = entityPlayer.rotationPitch;
			float yaw = entityPlayer.rotationYaw;
			float pitchRecoil = -GunCus.RANDOM.nextFloat() * 5F;
			float yawRecoil = (GunCus.RANDOM.nextFloat() - 0.5F) * 5F;
			
			if(inaccuracy > 10)
			{
				inaccuracy = 10F;
			}
			
			if(aiming)
			{
				inaccuracy *= 0.75F;
			}
			else
			{
				inaccuracy *= 1.5F * this.<Accessory>getAttachmentCalled(itemStack, EnumAttachmentType.ACCESSORY.getSlot()).getHipFireInccuracyModifier();
			}
			
			if(moving)
			{
				inaccuracy *= 1.5F * this.<Underbarrel>getAttachmentCalled(itemStack, EnumAttachmentType.UNDERBARREL.getSlot()).getInaccuracyModifierMoving();
			}
			else
			{
				inaccuracy *= 0.8F * this.<Underbarrel>getAttachmentCalled(itemStack, EnumAttachmentType.UNDERBARREL.getSlot()).getInaccuracyModifierStill();
				
				if(entityPlayer.isSneaking())
				{
					inaccuracy *= 0.8F * this.<Auxiliary>getAttachmentCalled(itemStack, EnumAttachmentType.AUXILIARY.getSlot()).getInaccuracyModifierWhenShiftAndStill();
					
					pitchRecoil *= this.<Auxiliary>getAttachmentCalled(itemStack, EnumAttachmentType.AUXILIARY.getSlot()).getDriftModifierWhenShiftAndStill();
					yawRecoil *= this.<Auxiliary>getAttachmentCalled(itemStack, EnumAttachmentType.AUXILIARY.getSlot()).getDriftModifierWhenShiftAndStill();
				}
			}
			
			pitchRecoil *= this.<Underbarrel>getAttachmentCalled(itemStack, EnumAttachmentType.UNDERBARREL.getSlot()).getDriftModifier();
			yawRecoil *= this.<Underbarrel>getAttachmentCalled(itemStack, EnumAttachmentType.UNDERBARREL.getSlot()).getDriftModifier();
			
			inaccuracy *= this.<Barrel>getAttachmentCalled(itemStack, EnumAttachmentType.BARREL.getSlot()).getInaccuracyModifier();
			
			pitch += (inaccuracy * this.<Barrel>getAttachmentCalled(itemStack, EnumAttachmentType.BARREL.getSlot()).getVerticalSpreadModifier() / 10F) * (2F * GunCus.RANDOM.nextFloat() - 1F) * 15F;
			yaw += (inaccuracy * this.<Barrel>getAttachmentCalled(itemStack, EnumAttachmentType.BARREL.getSlot()).getHorizontalSpreadModifier() / 10F) * (2F * GunCus.RANDOM.nextFloat() - 1F) * 15F;
			
			bullet.shoot(entityPlayer, pitch, yaw, 0F, this.getBulletVelocity(itemStack, entityPlayer), 1.0F);
			entityPlayer.world.spawnEntity(bullet);
			
			this.setShootTime(itemStack, this.fireRate);
			
			if(!entityPlayer.capabilities.isCreativeMode)
			{
				this.setAmmo(itemStack, this.getAmmo(itemStack) - 1);
			}
			
			this.setInaccuracy(itemStack, inaccuracyInt + 2);
			
			GunCus.channel.sendTo(new MessageRecoil(pitchRecoil, yawRecoil), (EntityPlayerMP) entityPlayer);
		}
		
		if(this.<Barrel>getAttachmentCalled(itemStack, EnumAttachmentType.BARREL.getSlot()) != null && this.<Barrel>getAttachmentCalled(itemStack, EnumAttachmentType.BARREL.getSlot()).getIsSilenced())
		{
			GunCus.channel.sendTo(new MessageSound(entityPlayer, this.soundShootSilenced, 1F, 1F), (EntityPlayerMP) entityPlayer);
		}
		else
		{
			GunCus.channel.sendTo(new MessageSound(entityPlayer, this.soundShoot, 10F, 1F), (EntityPlayerMP) entityPlayer);
		}
		
		return EnumActionResult.PASS;
	}
	
	public EnumActionResult tryReload(EntityPlayer entityPlayer, ItemStack itemStack)
	{
		int ammount = 0;
		int needed;
		
		NonNullList<ItemStack> list = NonNullList.<ItemStack>withSize(entityPlayer.inventory.mainInventory.size() + entityPlayer.inventory.offHandInventory.size(), ItemStack.EMPTY);
		
		int index = 0;
		
		for(ItemStack i : entityPlayer.inventory.mainInventory)
		{
			list.set(index, i);
			index++;
		}
		
		for(ItemStack i : entityPlayer.inventory.offHandInventory)
		{
			list.set(index, i);
			index++;
		}
		
		for(ItemStack i : list)
		{
			if(i.getItem() == this.getCurrentlyUsedCardridge(itemStack))
			{
				needed = this.maxAmmo - ammount;
				
				if(i.getCount() > needed)
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
			
			if(ammount >= this.maxAmmo)
			{
				break;
			}
		}
		
		if(ammount > 0)
		{
			return this.doReload(entityPlayer, itemStack, ammount);
		}
		
		return EnumActionResult.PASS;
	}
	
	public EnumActionResult doReload(EntityPlayer entityPlayer, ItemStack itemStack, int ammount)
	{
		if(!entityPlayer.world.isRemote)
		{
			this.setAmmo(itemStack, ammount);
			this.setShootTime(itemStack, 100);
			this.setIsReloading(itemStack, true);
		}
		
		GunCus.channel.sendTo(new MessageSound(entityPlayer, this.soundReload, 1F, 1F), (EntityPlayerMP) entityPlayer);
		
		return EnumActionResult.SUCCESS;
	}
	
	public float getBulletVelocity(ItemStack itemStack, EntityPlayer entityPlayer)
	{
		return 30F * this.getCartridge().getBulletSpeedModifier() * this.<Barrel>getAttachmentCalled(itemStack, EnumAttachmentType.BARREL.getSlot()).getBulletSpeedModifier();
	}
	
	public NBTTagCompound getNBT(ItemStack itemStack)
	{
		if(!itemStack.hasTagCompound())
		{
			itemStack.setTagCompound(new NBTTagCompound());
		}
		
		return itemStack.getTagCompound();
	}
	
	public boolean isAccessoryTurnedOn(ItemStack itemStack)
	{
		return true;//return this.getNBT(itemStack).getBoolean(NBT_ACCESSORY_SWITCH);
	}
	
	public void setAccessoryTurnedOn(ItemStack itemStack, boolean b)
	{
		this.getNBT(itemStack).setBoolean(ItemGun.NBT_ACCESSORY_SWITCH, b);
	}
	
	public int getShootTime(ItemStack itemStack)
	{
		return this.getNBT(itemStack).getInteger(ItemGun.NBT_SHOOT_TIME);
	}
	
	public void setShootTime(ItemStack itemStack, int i)
	{
		this.getNBT(itemStack).setInteger(ItemGun.NBT_SHOOT_TIME, i);
	}
	
	public boolean getCanAim(ItemStack itemStack)
	{
		return !this.getIsReloading(itemStack);
	}
	
	public boolean getIsReloading(ItemStack itemStack)
	{
		return this.getNBT(itemStack).getBoolean(ItemGun.NBT_RELOADING);
	}
	
	public void setIsReloading(ItemStack itemStack, boolean isReloading)
	{
		this.getNBT(itemStack).setBoolean(ItemGun.NBT_RELOADING, isReloading);
	}
	
	public int getAmmo(ItemStack itemStack)
	{
		return this.getNBT(itemStack).getInteger(ItemGun.NBT_AMMO);
	}
	
	public void setAmmo(ItemStack itemStack, int i)
	{
		this.getNBT(itemStack).setInteger(ItemGun.NBT_AMMO, i);
	}
	
	public int getInaccuracy(ItemStack itemStack)
	{
		return this.getNBT(itemStack).getInteger(ItemGun.NBT_INACCURACY);
	}
	
	public void setInaccuracy(ItemStack itemStack, int i)
	{
		this.getNBT(itemStack).setInteger(ItemGun.NBT_INACCURACY, i);
	}
	
	public @Nullable Attachment getAttachment(ItemStack itemStack, int slot)
	{
		return Attachment.getAttachment(slot, this.getAttachmentID(itemStack, slot));
	}
	
	public int getAttachmentID(ItemStack itemStack, int slot)
	{
		return this.getNBT(itemStack).getInteger(ItemGun.getAttachmentNBTKey(slot));
	}
	
	public void setAttachment(ItemStack itemStack, Attachment attachment)
	{
		this.setAttachment(itemStack, attachment.getSlot(), attachment.getID());
	}
	
	public void setAttachment(ItemStack itemStack, int slot, int id)
	{
		this.getNBT(itemStack).setInteger(ItemGun.getAttachmentNBTKey(slot), id);
	}
	
	public void removeAttachment(ItemStack itemStack, int slot)
	{
		this.setAttachment(itemStack, slot, 0);
	}
	
	public boolean canSetAttachment(Attachment attachment)
	{
		return this.canSetAttachment(attachment.getSlot(), attachment.getID());
	}
	
	public boolean canSetAttachment(int slot, int id)
	{
		return id == 0 || this.attachments[slot][id];
	}
	
	public <A extends Attachment> A getAttachmentCalled(ItemStack itemStack, int slot)
	{
		return (A) this.getAttachment(itemStack, slot);
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list)
	{
		super.getSubItems(tab, list);
		
		if(tab == this.gunTab)
		{
			list.add(new ItemStack(this));
			
			int[] array = new int[Attachment.ATTACHMENTS_LIST.length];
			
			int slot;
			int j;
			boolean include = true;
			ItemStack itemStack;
			boolean finalBreak = false;
			
			ArrayList<Integer> availableSlots = new ArrayList<Integer>();
			
			for(j = 0; j < array.length; ++j)
			{
				if(this.isSlotAvailable(j))
				{
					availableSlots.add(j);
				}
			}
			
			while(!finalBreak)
			{
				for(j = 0; j < availableSlots.size(); ++j)
				{
					slot = availableSlots.get(j);
					
					array[slot]++;
					
					if(array[slot] < Attachment.getAmmountForSlot(slot))
					{
						include = true;
						
						for(slot = 0; slot < array.length; ++slot)
						{
							if(!this.canSetAttachment(slot, array[slot]))
							{
								include = false;
								break;
							}
						}
						
						if(include)
						{
							itemStack = new ItemStack(this);
							
							for(slot = 0; slot < array.length; ++slot)
							{
								this.setAttachment(itemStack, slot, array[slot]);
							}
							
							list.add(itemStack);
						}
						
						break;
					}
					else
					{
						array[slot] = 0;
						
						if(slot == (availableSlots.get(availableSlots.size() - 1)))
						{
							finalBreak = true;
						}
					}
				}
			}
		}
	}
	
	@Override
	public void addInformation(ItemStack itemStack, World world, List<String> list, ITooltipFlag flag)
	{
		super.addInformation(itemStack, world, list, flag);
		
		list.add("§e" + this.getAmmo(itemStack) + "§f/" + this.maxAmmo + " §e" + ItemGun.getCardridgeTranslated(this.getCurrentlyUsedCardridge(itemStack)));
		
		Attachment attachment;
		int ammount = 0;;
		int i;
		
		for(i = 0; i < this.attachments.length; ++i)
		{
			if(this.attachments[i][0])
			{
				++ammount;
			}
		}
		
		if(ammount > 0)
		{
			list.add("§8" + Attachment.getAttachmentTranslated(ammount > 1) + ":");
			for(i = 0; i < EnumAttachmentType.values().length; ++i)
			{
				if(this.isSlotAvailable(i))
				{
					attachment = this.getAttachment(itemStack, i);
					
					if(attachment != null)
					{
						list.add("§e" + attachment.getInformationString(this, itemStack) + " §8(" + Attachment.getSlotTranslated(i) + ")");
					}
					else
					{
						list.add("--" + " §8(" + Attachment.getSlotTranslated(i) + ")");
					}
				}
			}
		}
	}
	
	public static String getAttachmentNBTKey(int slot)
	{
		return ItemGun.NBT_ATTACHMENT_PREFIX + slot;
	}
	
	public static String getCardridgeTranslated(Item cardridge)
	{
		return I18n.translateToLocal(cardridge.getUnlocalizedName() + ".name").trim();
	}
	
	public int getFireRate() {
		return this.fireRate;
	}
	
	public int getMaxAmmo() {
		return this.maxAmmo;
	}
	
	public float getDamage() {
		return this.damage + this.getCartridge().getDamage();
	}
	
	public ItemCartridge getCartridge() {
		return this.cartridge;
	}
	
	public int getVariants()
	{
		int ammount = 0;
		
		int[] array = new int[Attachment.ATTACHMENTS_LIST.length];
		
		int slot;
		int j;
		boolean include = true;
		boolean finalBreak = false;
		
		ArrayList<Integer> availableSlots = new ArrayList<Integer>();
		
		for(j = 0; j < array.length; ++j)
		{
			if(this.isSlotAvailable(j))
			{
				availableSlots.add(j);
			}
		}
		
		while(!finalBreak)
		{
			for(j = 0; j < availableSlots.size(); ++j)
			{
				slot = availableSlots.get(j);
				
				array[slot]++;
				
				if(array[slot] < Attachment.getAmmountForSlot(slot))
				{
					include = true;
					
					for(slot = 0; slot < array.length; ++slot)
					{
						if(!this.canSetAttachment(slot, array[slot]))
						{
							include = false;
							break;
						}
					}
					
					if(include)
					{
						++ammount;
					}
					
					break;
				}
				else
				{
					array[slot] = 0;
					
					if(slot == (availableSlots.get(availableSlots.size() - 1)))
					{
						finalBreak = true;
					}
				}
			}
		}
		
		return ammount;
	}
	
	@Override
	public boolean onEntitySwing(EntityLivingBase entityLivingBase, ItemStack itemStack)
	{
		return true;
	}
}
