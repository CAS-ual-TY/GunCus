package de.cas_ual_ty.gci;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class SoundEventGCI extends SoundEvent
{
	public static SoundEventGCI[] soundEventList = new SoundEventGCI[4];
	
	protected int id;
	
	public SoundEventGCI(int id, String rl)
	{
		super(new ResourceLocation(GunCus.MOD_ID, rl));
		this.setRegistryName(GunCus.MOD_ID + ":" + rl);
		
		this.id = id;
		
		soundEventList[id] = this;
	}
	
	public int getID()
	{
		return this.id;
	}
}
