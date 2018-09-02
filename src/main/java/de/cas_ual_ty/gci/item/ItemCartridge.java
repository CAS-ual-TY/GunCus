package de.cas_ual_ty.gci.item;

import java.util.ArrayList;
import java.util.List;

import de.cas_ual_ty.gci.GunCus;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class ItemCartridge extends ItemGCI
{
	public static final ArrayList<ItemCartridge> CARTRIDGES_LIST = new ArrayList<ItemCartridge>();
	
	protected float damage;
	protected float gravityModifier;
	protected float bulletSpeedModifier;
	protected float spreadModifier;
	protected int projectileAmmount;
	
	public ItemCartridge(String rl)
	{
		super(rl);
		
		this.damage = 0F;
		this.gravityModifier = 1F;
		this.bulletSpeedModifier = 1F;
		this.spreadModifier = 1F;
		this.projectileAmmount = 1;
		
		CARTRIDGES_LIST.add(this);
	}

	public float getDamage() {
		return damage;
	}

	public float getGravityModifier() {
		return gravityModifier;
	}

	public float getBulletSpeedModifier() {
		return bulletSpeedModifier;
	}

	public float getSpreadModifier() {
		return spreadModifier;
	}
	
	public int getProjectileAmmount()
	{
		return this.projectileAmmount;
	}

	public ItemCartridge setDamage(float damage) {
		this.damage = damage;
		return this;
	}

	public ItemCartridge setGravityModifier(float gravityModifier) {
		this.gravityModifier = gravityModifier;
		return this;
	}

	public ItemCartridge setBulletSpeedModifier(float bulletSpeedModifier) {
		this.bulletSpeedModifier = bulletSpeedModifier;
		return this;
	}

	public ItemCartridge setSpreadModifier(float spreadModifier) {
		this.spreadModifier = spreadModifier;
		return this;
	}
	
	public ItemCartridge setProjectileAmmount(int projectileAmmount)
	{
		this.projectileAmmount = projectileAmmount;
		return this;
	}
	
	@Override
	public void addInformation(ItemStack itemStack, World world, List<String> list, ITooltipFlag flag)
	{
		list.add("§e" + I18n.translateToLocal("local." + GunCus.MOD_ID + ":cartridge.name").trim());
	}
}
