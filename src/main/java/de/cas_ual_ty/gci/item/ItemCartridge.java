package de.cas_ual_ty.gci.item;

public class ItemCartridge extends ItemGCI
{
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
}
