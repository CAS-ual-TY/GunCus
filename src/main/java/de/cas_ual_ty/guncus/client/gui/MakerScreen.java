package de.cas_ual_ty.guncus.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import de.cas_ual_ty.guncus.GunCus;
import de.cas_ual_ty.guncus.client.ClientProxy;
import de.cas_ual_ty.guncus.container.MakerContainer;
import de.cas_ual_ty.guncus.network.MakerMessages;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.PacketDistributor;

public class MakerScreen extends ContainerScreen<MakerContainer>
{
    public static final ResourceLocation MAKER_GUI_TEXTURES = new ResourceLocation(GunCus.MOD_ID, "textures/gui/container/generic_maker.png");
    
    private Button makeButton;
    
    public MakerScreen(MakerContainer screenContainer, PlayerInventory inv, ITextComponent titleIn)
    {
        super(screenContainer, inv, titleIn);
    }
    
    @Override
    protected void init()
    {
        super.init();
        this.addButton(new Button(this.guiLeft + 124 - 12 - 4, this.guiTop + 33, 10, 20, new StringTextComponent("<"), (b) -> this.prev()));
        this.addButton(new Button(this.guiLeft + 124 + 18 + 4, this.guiTop + 33, 10, 20, new StringTextComponent(">"), (b) -> this.next()));
        this.addButton(this.makeButton = new Button(this.guiLeft + 124 - 12 - 4, this.guiTop + 57, 48, 20, new StringTextComponent("MAKE"), (b) -> this.create()));
        this.makeButton.active = false;
    }
    
    private void next()
    {
        GunCus.channel.send(PacketDistributor.SERVER.noArg(), new MakerMessages.Next());
    }
    
    private void prev()
    {
        GunCus.channel.send(PacketDistributor.SERVER.noArg(), new MakerMessages.Prev());
    }
    
    private void create()
    {
        GunCus.channel.send(PacketDistributor.SERVER.noArg(), new MakerMessages.Create());
    }
    
    @Override
    public void tick()
    {
        if(this.getContainer().selectedItemSlot.getHasStack())
        {
            boolean hasEverything = true;
            
            for(Slot s : this.getContainer().materialsSlots)
            {
                ItemStack material = s.getStack();
                
                if(!material.isEmpty())
                {
                    if(hasEverything && !this.getContainer().playerHasMaterial(material, false))
                    {
                        hasEverything = false;
                        break;
                    }
                }
            }
            
            if(this.makeButton.active != hasEverything)
            {
                this.makeButton.active = hasEverything;
            }
        }
        else if(this.makeButton.active)
        {
            this.makeButton.active = false;
        }
        
        super.tick();
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack ms, int mouseX, int mouseY)
    {
        String text = this.title.getString();
        this.font.drawString(ms, text, (float)(this.xSize - this.font.getStringWidth(text)) * 0.5F, 6.0F, 0x404040);
        this.font.drawString(ms, this.playerInventory.getDisplayName().getString(), 8.0F, (float)(this.ySize - 96 + 2), 0x404040);
        
        RenderSystem.color4f(1F, 1F, 1F, 1F);
        this.minecraft.getTextureManager().bindTexture(MakerScreen.MAKER_GUI_TEXTURES);
        
        for(Slot s : this.getContainer().materialsSlots)
        {
            ItemStack material = s.getStack();
            
            if(!material.isEmpty())
            {
                if(this.getContainer().playerHasMaterial(material, false))
                {
                    this.drawCheck(ms, s);
                }
                else
                {
                    this.drawMissing(ms, s);
                }
            }
            else
            {
                this.drawDisabled(ms, s);
            }
        }
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack ms, float partialTicks, int mouseX, int mouseY)
    {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(MakerScreen.MAKER_GUI_TEXTURES);
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
    
    public void drawCheck(MatrixStack ms, Slot slot)
    {
        this.drawCheck(ms, slot.xPos - 1, slot.yPos - 1);
    }
    
    public void drawCheck(MatrixStack ms, int x, int y)
    {
        this.drawDisabled(ms, x, y);
        this.blit(ms, x, y, this.xSize, 0, 18, 18);
    }
    
    public void drawMissing(MatrixStack ms, Slot slot)
    {
        this.drawMissing(ms, slot.xPos - 1, slot.yPos - 1);
    }
    
    public void drawMissing(MatrixStack ms, int x, int y)
    {
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
