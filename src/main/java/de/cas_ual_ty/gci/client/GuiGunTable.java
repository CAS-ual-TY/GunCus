package de.cas_ual_ty.gci.client;

import de.cas_ual_ty.gci.ContainerGunTable;
import de.cas_ual_ty.gci.ContainerGunTable.SlotAttachment;
import de.cas_ual_ty.gci.GunCus;
import de.cas_ual_ty.gci.item.ItemGCI;
import de.cas_ual_ty.gci.item.attachment.EnumAttachmentType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiGunTable extends GuiContainer
{
	private static final ResourceLocation CRAFTING_TABLE_GUI_TEXTURES = new ResourceLocation(GunCus.MOD_ID, "textures/gui/gun_table.png");
	
	private ContainerGunTable c;
	
	public GuiGunTable(Container container)
	{
		super(container);
		this.c = (ContainerGunTable) container;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		this.fontRenderer.drawString(I18n.format("tile." + GunCus.MOD_ID + ":" + GunCus.BLOCK_GUN_TABLE.getModelRL() + ".name"), 62, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GuiGunTable.CRAFTING_TABLE_GUI_TEXTURES);
		int i = this.guiLeft;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
		
		ItemStack itemStack = Minecraft.getMinecraft().player.inventory.getItemStack();
		if(itemStack.getItem() instanceof ItemGCI)
		{
			if(this.c.gunSlot.isItemValid(itemStack))
			{
				this.drawTexturedModalRect(i + 61 + 18, j + 16 + 18, 176, 0, 18, 18);
			}
			else
			{
				EnumAttachmentType t;
				for(SlotAttachment slot : this.c.attachmentSlots)
				{
					t = EnumAttachmentType.values()[slot.slot];
					if(slot.isItemValid(itemStack))
					{
						this.drawTexturedModalRect(i + 61 + t.getX() * 18, j + 16 + t.getY() * 18, 176, 0, 18, 18);
					}
				}
			}
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
}
