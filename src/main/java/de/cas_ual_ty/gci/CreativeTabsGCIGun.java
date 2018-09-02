package de.cas_ual_ty.gci;

import de.cas_ual_ty.gci.item.ItemGun;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTabsGCIGun extends CreativeTabsGCI
{
	private int variants;
	private ItemGun gun;
	
	public CreativeTabsGCIGun(String label, ItemGun gun)
	{
		super(label, new ItemStack(gun));
		this.gun = gun;
		this.variants = 0;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public String getTranslatedTabLabel()
	{
		return I18n.translateToLocal(this.getTabIconItem().getItem().getUnlocalizedName() + ".name") + " (" + this.getVariants() + ")";
	}
	
	public int getVariants()
	{
		if(this.variants == 0)
		{
			this.variants = this.gun.getVariants();
		}
		
		return this.variants;
	}
}
