package de.cas_ual_ty.guncus.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import de.cas_ual_ty.guncus.GunCus;
import de.cas_ual_ty.guncus.container.ContainerGunMaker;
import de.cas_ual_ty.guncus.item.ItemGun;
import de.cas_ual_ty.guncus.network.MessageGunMaker;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class GuiContainerGunMaker extends ContainerScreen<ContainerGunMaker>
{
    public static final ResourceLocation GUN_MAKER_GUI_TEXTURES = new ResourceLocation(GunCus.MOD_ID, "textures/gui/container/gun_maker.png");
    
    private int currentGun;
    
    public GuiContainerGunMaker(ContainerGunMaker screenContainer, PlayerInventory inv, ITextComponent titleIn)
    {
        super(screenContainer, inv, titleIn);
        this.currentGun = 0;
    }
    
    @Override
    protected void init()
    {
        super.init();
        this.addButton(new Button(this.guiLeft + 116 - 12, this.guiTop + 20, 10, 20, new StringTextComponent("<"), (b) ->
        {
            this.prev();
        }));
        this.addButton(new Button(this.guiLeft + 116 + 18, this.guiTop + 20, 10, 20, new StringTextComponent(">"), (b) ->
        {
            this.next();
        }));
    }
    
    private void next()
    {
        this.currentGun = (this.currentGun + 1) % ItemGun.GUNS_LIST.size();
        this.send();
    }
    
    private void prev()
    {
        this.currentGun = (this.currentGun - 1 + ItemGun.GUNS_LIST.size()) % ItemGun.GUNS_LIST.size();
        this.send();
    }
    
    private void send()
    {
        ItemGun gun = ItemGun.GUNS_LIST.get(this.currentGun);
        
        if(gun != null)
        {
            GunCus.channel.sendToServer(new MessageGunMaker(gun.getRegistryName().getNamespace(), gun.getRegistryName().getPath()));
        }
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack ms, int mouseX, int mouseY)
    {
        String text = this.title.getString();
        this.font.drawString(ms, text, (float)(this.xSize - this.font.getStringWidth(text)) * 0.5F, 6.0F, 0x404040);
        this.font.drawString(ms, this.playerInventory.getDisplayName().getString(), 8.0F, (float)(this.ySize - 96 + 2), 0x404040);
        
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GuiContainerGunMaker.GUN_MAKER_GUI_TEXTURES);
        
        Slot slotS;
        Slot slotI;
        for(int i = 3; i < 6; ++i)
        {
            slotS = this.container.slots.get(i);
            slotI = this.container.slots.get(i - 3);
            if(slotS.getStack().getCount() <= 0)
            {
                this.drawStrike(ms, slotS);
            }
            if(!slotI.getHasStack())
            {
                if(slotS.getStack().getCount() <= 0)
                {
                    this.drawStrike(ms, slotI);
                }
                else
                {
                    this.drawFocus(ms, slotI);
                }
            }
        }
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack ms, float partialTicks, int mouseX, int mouseY)
    {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GuiContainerGunMaker.GUN_MAKER_GUI_TEXTURES);
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
        this.blit(ms, x, y, this.xSize + 18, 0, 18, 18);
    }
}
