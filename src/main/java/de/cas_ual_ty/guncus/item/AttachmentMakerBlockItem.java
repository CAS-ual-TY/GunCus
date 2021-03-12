package de.cas_ual_ty.guncus.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class AttachmentMakerBlockItem extends BlockItem
{
    public AttachmentMakerBlockItem(Block blockIn, Properties builder)
    {
        super(blockIn, builder);
    }
    
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("local.guncus.attachments").modifyStyle((s) -> s.applyFormatting(TextFormatting.DARK_GRAY)));
    }
}
