package de.cas_ual_ty.gci.item;

import de.cas_ual_ty.gci.GunCus;
import de.cas_ual_ty.gci.block.BlockGCI;
import net.minecraft.item.ItemBlock;

public class ItemBlockGCI extends ItemBlock
{
	private String modelRL;
	
	public ItemBlockGCI(BlockGCI block)
	{
		super(block);
		
		this.setUnlocalizedName(GunCus.MOD_ID + ":" + block.getModelRL());
		this.setRegistryName(GunCus.MOD_ID + ":" + block.getModelRL());
		this.modelRL = block.getModelRL();
		
		this.setCreativeTab(block.getCreativeTabToDisplayOn());
	}
	
	public String getModelRL()
	{
		return this.modelRL;
	}
}
