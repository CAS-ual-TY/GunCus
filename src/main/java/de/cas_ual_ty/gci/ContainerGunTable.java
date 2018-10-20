package de.cas_ual_ty.gci;

import de.cas_ual_ty.gci.item.ItemGun;
import de.cas_ual_ty.gci.item.attachment.Attachment;
import de.cas_ual_ty.gci.item.attachment.EnumAttachmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerGunTable extends Container
{
	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
	
	public EntityPlayer entityPlayer;
	public World world;
	public BlockPos pos;
	
	public Slot gunSlot;
	public SlotAttachment[] attachmentSlots;
	
	public ContainerGunTable(EntityPlayer entityPlayer, World world, BlockPos pos)
	{
		this.entityPlayer = entityPlayer;
		this.world = world;
		this.pos = pos;
		
		this.gunSlot = new Slot(this.craftMatrix, 4, 80, 35)
		{
			@Override
			public boolean isItemValid(ItemStack itemStack)
			{
				if(!this.getHasStack() && (itemStack.getItem() instanceof ItemGun))
				{
					for(EnumAttachmentType attachmentType : EnumAttachmentType.values())
					{
						if(ContainerGunTable.this.attachmentSlots[attachmentType.getSlot()].getHasStack())
						{
							return false;
						}
					}
					
					return true;
				}
				else
				{
					return false;
				}
			}
		};
		this.addSlotToContainer(this.gunSlot);
		
		this.attachmentSlots = new SlotAttachment[EnumAttachmentType.values().length];
		for(EnumAttachmentType attachmentType : EnumAttachmentType.values())
		{
			this.attachmentSlots[attachmentType.getSlot()] = new SlotAttachment(this.gunSlot, attachmentType.getSlot(), this.craftMatrix, attachmentType.getX() + attachmentType.getY() * 3, 8 + (attachmentType.getX() + 3) * 18, 17 + attachmentType.getY() * 18);
			this.addSlotToContainer(this.attachmentSlots[attachmentType.getSlot()]);
		}
		
		for (int k = 0; k < 3; ++k)
		{
			for (int i1 = 0; i1 < 9; ++i1)
			{
				this.addSlotToContainer(new Slot(entityPlayer.inventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
			}
		}
		
		for (int l = 0; l < 9; ++l)
		{
			this.addSlotToContainer(new Slot(entityPlayer.inventory, l, 8 + l * 18, 142));
		}
	}
	
	private boolean changing = false;
	private int attachmentAmmount = 0;
	
	@Override
	public void onCraftMatrixChanged(IInventory inventory)
	{
		if(!this.changing)
		{
			this.changing = true;
			
			if(inventory == this.craftMatrix)
			{
				//WITH GUN
				if(this.gunSlot.getHasStack())
				{
					int attachmentAmmount = 0;
					
					for(EnumAttachmentType attachmentType : EnumAttachmentType.values())
					{
						if(ContainerGunTable.this.attachmentSlots[attachmentType.getSlot()].getHasStack())
						{
							++attachmentAmmount;
						}
					}
					
					ItemStack gunStack = this.gunSlot.getStack();
					ItemGun gun = (ItemGun) gunStack.getItem();
					
					//+GUN -PREV
					if(this.attachmentAmmount == 0)
					{
						//+GUN -PREV -ATTACHMENTS
						if(attachmentAmmount == 0)
						{
							for(EnumAttachmentType attachmentType : EnumAttachmentType.values())
							{
								this.attachmentSlots[attachmentType.getSlot()].putStack(gun.getAttachment(gunStack, attachmentType.getSlot()).createItemStack());
							}
						}
						//+GUN -PREV +ATTACHMENTS
						else
						{
							Attachment attachment;
							
							for(EnumAttachmentType attachmentType : EnumAttachmentType.values())
							{
								if(this.attachmentSlots[attachmentType.getSlot()].getHasStack())
								{
									attachment = (Attachment) this.attachmentSlots[attachmentType.getSlot()].getStack().getItem();
									gun.setAttachment(gunStack, attachmentType.getSlot(), attachment.getID());
								}
								else
								{
									gun.setAttachment(gunStack, attachmentType.getSlot(), 0);
								}
							}
						}
						
						this.attachmentAmmount = 0;
						
						for(EnumAttachmentType attachmentType : EnumAttachmentType.values())
						{
							if(ContainerGunTable.this.attachmentSlots[attachmentType.getSlot()].getHasStack())
							{
								++this.attachmentAmmount;
							}
						}
					}
					//+GUN +PREV
					else
					{
						Attachment attachment;
						
						for(EnumAttachmentType attachmentType : EnumAttachmentType.values())
						{
							if(this.attachmentSlots[attachmentType.getSlot()].getHasStack())
							{
								attachment = (Attachment) this.attachmentSlots[attachmentType.getSlot()].getStack().getItem();
								gun.setAttachment(gunStack, attachmentType.getSlot(), attachment.getID());
							}
							else
							{
								gun.setAttachment(gunStack, attachmentType.getSlot(), 0);
							}
						}
					}
				}
				//WITHOUT GUN
				else
				{
					for(EnumAttachmentType attachmentType : EnumAttachmentType.values())
					{
						this.attachmentSlots[attachmentType.getSlot()].putStack(ItemStack.EMPTY);
					}
					
					this.attachmentAmmount = 0;
				}
			}
			
			super.onCraftMatrixChanged(inventory);
			
			this.changing = false;
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		if (this.world.getBlockState(this.pos).getBlock() != GunCus.BLOCK_GUN_TABLE)
		{
			return false;
		}
		else
		{
			return playerIn.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D;
		}
	}
	
	protected void clearContainer(EntityPlayer playerIn, World worldIn)
	{
		if (!playerIn.isEntityAlive() || playerIn instanceof EntityPlayerMP && ((EntityPlayerMP)playerIn).hasDisconnected())
		{
			if(this.gunSlot.getHasStack())
			{
				playerIn.dropItem(this.gunSlot.getStack(), false);
			}
			else
			{
				for(SlotAttachment slot : this.attachmentSlots)
				{
					playerIn.dropItem(slot.getStack(), false);
				}
			}
		}
		else
		{
			if(this.gunSlot.getHasStack())
			{
				playerIn.inventory.placeItemBackInInventory(worldIn, this.gunSlot.getStack());
			}
			else
			{
				for(SlotAttachment slot : this.attachmentSlots)
				{
					playerIn.inventory.placeItemBackInInventory(worldIn, slot.getStack());
				}
			}
		}
	}
	
	@Override
	public void onContainerClosed(EntityPlayer playerIn)
	{
		super.onContainerClosed(playerIn);
		
		if (!this.world.isRemote)
		{
			this.clearContainer(playerIn, this.world);
		}
	}
	
	public static class SlotAttachment extends Slot
	{
		public Slot main;
		public int slot;
		
		public SlotAttachment(Slot main, int slot, IInventory inventory, int id, int x, int y)
		{
			super(inventory, id , x, y);
			
			this.main = main;
			this.slot = slot;
		}
		
		@Override
		public boolean isItemValid(ItemStack itemStack)
		{
			if(this.main.getHasStack() && !this.getHasStack() && (itemStack.getItem() instanceof Attachment))
			{
				ItemGun gun = (ItemGun) this.main.getStack().getItem();
				Attachment attachment = (Attachment) itemStack.getItem();
				
				return (attachment.getSlot() == this.slot) && gun.canSetAttachment(attachment);
			}
			else
			{
				return false;
			}
		}
	}
}
