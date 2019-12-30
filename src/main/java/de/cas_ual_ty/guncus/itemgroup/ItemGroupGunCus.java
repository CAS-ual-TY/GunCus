package de.cas_ual_ty.guncus.itemgroup;

import java.util.Random;

import de.cas_ual_ty.guncus.GunCus;

public class ItemGroupGunCus extends ItemGroupShuffle
{
    public ItemGroupGunCus()
    {
        super(GunCus.MOD_ID, new Random(1776));
    }
}
