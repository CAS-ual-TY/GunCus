package de.cas_ual_ty.gci.block;

import java.util.ArrayList;

import javax.annotation.Nullable;

import de.cas_ual_ty.gci.GunCus;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockGCI extends Block
{
	public static final ArrayList<BlockGCI> BLOCKS_LIST = new ArrayList<BlockGCI>();
	
	private String modelRL;
	
	public BlockGCI(String rl, Material material)
	{
		this(rl, null, material);
	}
	
	public BlockGCI(String rl, @Nullable CreativeTabs tab, Material material)
	{
		super(material);
		
		this.setUnlocalizedName(GunCus.MOD_ID + ":" + rl);
		this.setRegistryName(GunCus.MOD_ID + ":" + rl);
		this.modelRL = rl;
		
		if(tab != null)
			this.setCreativeTab(tab);
		else
			this.setCreativeTab(GunCus.TAB_GUNCUS);
		
		BLOCKS_LIST.add(this);
	}
	
	public String getModelRL()
	{
		return this.modelRL;
	}
}
