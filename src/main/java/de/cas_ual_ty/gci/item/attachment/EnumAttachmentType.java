package de.cas_ual_ty.gci.item.attachment;

public enum EnumAttachmentType
{
	/*
	 * 2 0 5
	 * 3 - 7
	 * 4 6 1
	 */
	
	OPTIC(0, 1, 0), ACCESSORY(1, 2, 2), BARREL(2, 0, 0), UNDERBARREL(3, 0, 1), AUXILIARY(4, 0, 2), AMMO(5, 2, 0), MAGAZINE(6, 1, 2), PAINT(7, 2, 1);
	
	private int slot;
	private int x;
	private int y;
	
	private EnumAttachmentType(int slot, int x, int y)
	{
		this.slot = slot;
		this.x = x;
		this.y = y;
	}
	
	public int getSlot()
	{
		return this.slot;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
}
