package de.cas_ual_ty.guncus.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import de.cas_ual_ty.guncus.GunCus;
import de.cas_ual_ty.guncus.client.ClientProxy;
import de.cas_ual_ty.guncus.container.GunTableContainer;
import de.cas_ual_ty.guncus.item.AttachmentItem;
import de.cas_ual_ty.guncus.item.GunItem;
import de.cas_ual_ty.guncus.item.attachments.EnumAttachmentType;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class GunTableScreen extends ContainerScreen<GunTableContainer>
{
    public static final ResourceLocation GUN_TABLE_GUI_TEXTURES = new ResourceLocation(GunCus.MOD_ID, "textures/gui/container/gun_table.png");
    
    public GunTableScreen(GunTableContainer screenContainer, PlayerInventory inv, ITextComponent titleIn)
    {
        super(screenContainer, inv, titleIn);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack ms, int mouseX, int mouseY)
    {
        String text = this.title.getString();
        this.font.drawString(ms, text, (float)(this.xSize - this.font.getStringWidth(text)) * 0.5F, 6.0F, 0x404040);
        this.font.drawString(ms, this.playerInventory.getDisplayName().getString(), 8.0F, (float)(this.ySize - 96 + 2), 0x404040);
        
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GunTableScreen.GUN_TABLE_GUI_TEXTURES);
        
        ItemStack held = this.playerInventory.getItemStack();
        Slot slot = this.getContainer().gunSlot;
        
        if(slot.getHasStack())
        {
            GunItem gun = (GunItem)slot.getStack().getItem();
            
            for(EnumAttachmentType type : EnumAttachmentType.VALUES)
            {
                slot = this.getContainer().attachmentSlots[type.getSlot()];
                
                if(!gun.isSlotAvailable(type))
                {
                    this.drawStrike(ms, slot);
                }
            }
            
            if(held.getItem() instanceof AttachmentItem)
            {
                AttachmentItem attachment = (AttachmentItem)held.getItem();
                slot = this.getContainer().attachmentSlots[attachment.getSlot()];
                
                if(!slot.getHasStack() && gun.canSetAttachment(attachment))
                {
                    this.drawFocus(ms, slot);
                }
            }
        }
        else
        {
            if(held.getItem() instanceof GunItem)
            {
                this.drawFocus(ms, slot);
            }
            
            for(EnumAttachmentType type : EnumAttachmentType.VALUES)
            {
                slot = this.getContainer().attachmentSlots[type.getSlot()];
                this.drawStrike(ms, slot);
            }
        }
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack ms, float partialTicks, int mouseX, int mouseY)
    {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GunTableScreen.GUN_TABLE_GUI_TEXTURES);
        int i = this.guiLeft;
        int j = (this.height - this.ySize) / 2;
        this.blit(ms, i, j, 0, 0, this.xSize, this.ySize);
    }
    
    @Override
    public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks)
    {
        super.render(ms, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(ms, mouseX, mouseY);
    }
    
    public void drawFocus(MatrixStack ms, Slot slot)
    {
        this.drawFocus(ms, slot.xPos - 1, slot.yPos - 1);
    }
    
    public void drawFocus(MatrixStack ms, int x, int y)
    {
        this.blit(ms, x, y, this.xSize, 0, 18, 18);
    }
    
    public void drawStrike(MatrixStack ms, Slot slot)
    {
        this.drawStrike(ms, slot.xPos - 1, slot.yPos - 1);
    }
    
    public void drawStrike(MatrixStack ms, int x, int y)
    {
        this.drawDisabled(ms, x, y);
        this.blit(ms, x, y, this.xSize + 18, 0, 18, 18);
    }
    
    public void drawDisabled(MatrixStack ms, Slot slot)
    {
        this.drawDisabled(ms, slot.xPos - 1, slot.yPos - 1);
    }
    
    public void drawDisabled(MatrixStack ms, int x, int y)
    {
        ClientProxy.renderDisabledRect(ms, x, y, 18, 18);
    }
}
