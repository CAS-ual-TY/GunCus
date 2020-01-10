package de.cas_ual_ty.guncus.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;

import de.cas_ual_ty.guncus.GunCus;
import de.cas_ual_ty.guncus.container.ContainerGunTable;
import de.cas_ual_ty.guncus.item.ItemAttachment;
import de.cas_ual_ty.guncus.item.ItemGun;
import de.cas_ual_ty.guncus.item.attachments.EnumAttachmentType;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class GuiContainerGunTable extends ContainerScreen<ContainerGunTable>
{
    public static final ResourceLocation GUN_TABLE_GUI_TEXTURES = new ResourceLocation(GunCus.MOD_ID, "textures/gui/container/gun_table.png");
    
    public GuiContainerGunTable(ContainerGunTable screenContainer, PlayerInventory inv, ITextComponent titleIn)
    {
        super(screenContainer, inv, titleIn);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String text = this.title.getFormattedText();
        this.font.drawString(text, (float)(this.xSize - this.font.getStringWidth(text)) * 0.5F, 6.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 96 + 2), 4210752);
        
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GuiContainerGunTable.GUN_TABLE_GUI_TEXTURES);
        
        ItemStack held = this.playerInventory.getItemStack();
        Slot slot = this.getContainer().gunSlot;
        
        if(slot.getHasStack())
        {
            ItemGun gun = (ItemGun)slot.getStack().getItem();
            
            for(EnumAttachmentType type : EnumAttachmentType.VALUES)
            {
                slot = this.getContainer().attachmentSlots[type.getSlot()];
                
                if(!gun.isSlotAvailable(type))
                {
                    this.drawStrike(slot);
                }
            }
            
            if(held.getItem() instanceof ItemAttachment)
            {
                ItemAttachment attachment = (ItemAttachment)held.getItem();
                slot = this.getContainer().attachmentSlots[attachment.getSlot()];
                
                if(!slot.getHasStack() && gun.canSetAttachment(attachment))
                {
                    this.drawFocus(slot);
                }
            }
        }
        else
        {
            if(held.getItem() instanceof ItemGun)
            {
                this.drawFocus(slot);
            }
            
            for(EnumAttachmentType type : EnumAttachmentType.VALUES)
            {
                slot = this.getContainer().attachmentSlots[type.getSlot()];
                this.drawStrike(slot);
            }
        }
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GuiContainerGunTable.GUN_TABLE_GUI_TEXTURES);
        int i = this.guiLeft;
        int j = (this.height - this.ySize) / 2;
        this.blit(i, j, 0, 0, this.xSize, this.ySize);
    }
    
    @Override
    public void render(int mouseX, int mouseY, float partialTicks)
    {
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
    
    public void drawFocus(Slot slot)
    {
        this.drawFocus(slot.xPos - 1, slot.yPos - 1);
    }
    
    public void drawFocus(int x, int y)
    {
        this.blit(x, y, this.xSize, 0, 18, 18);
    }
    
    public void drawStrike(Slot slot)
    {
        this.drawStrike(slot.xPos - 1, slot.yPos - 1);
    }
    
    public void drawStrike(int x, int y)
    {
        this.blit(x, y, this.xSize + 18, 0, 18, 18);
    }
}
