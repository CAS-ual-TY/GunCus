package de.cas_ual_ty.guncus.item.attachments;

import java.util.function.Consumer;

import de.cas_ual_ty.guncus.GunCus;
import de.cas_ual_ty.guncus.item.AttachmentItem;
import de.cas_ual_ty.guncus.item.GunItem;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public enum EnumAttachmentType
{
    /*
     * 2 0 5
     * 3 - 7
     * 4 6 1
     */
    
    OPTIC("optic", 0, 1, 0, Optic.DEFAULT), ACCESSORY("accessory", 1, 2, 2, Accessory.DEFAULT), BARREL("barrel", 2, 0, 0, Barrel.DEFAULT), UNDERBARREL("underbarrel", 3, 0, 1, Underbarrel.DEFAULT), AUXILIARY("auxiliary", 4, 0, 2, Auxiliary.DEFAULT), AMMO("ammo", 5, 2, 0, Ammo.DEFAULT), MAGAZINE("magazine", 6, 1, 2, Magazine.DEFAULT), PAINT("paint", 7, 2, 1, Paint.DEFAULT);
    
    public static final EnumAttachmentType[] VALUES = EnumAttachmentType.values();
    public static final EnumAttachmentType[] RENDER_ORDER = { PAINT, MAGAZINE, AMMO, AUXILIARY, UNDERBARREL, BARREL, ACCESSORY, OPTIC };
    public static final int LENGTH = EnumAttachmentType.VALUES.length;
    
    private final String key;
    private final int slot;
    private final int x;
    private final int y;
    private final AttachmentItem _default;
    
    private EnumAttachmentType(String key, int slot, int x, int y, AttachmentItem _default)
    {
        this.key = key;
        this.slot = slot;
        this.x = x;
        this.y = y;
        this._default = _default;
    }
    
    public String getKey()
    {
        return this.key;
    }
    
    public int getSlot()
    {
        return this.slot;
    }
    
    public int getX()
    {
        return this.x;
    }
    
    public int getY()
    {
        return this.y;
    }
    
    public AttachmentItem getDefault()
    {
        return this._default;
    }
    
    public String getDefaultResource()
    {
        return this.getKey() + "_" + "default";
    }
    
    public String getTranslationKey()
    {
        return "local." + GunCus.MOD_ID + "." + this.getKey();
    }
    
    public IFormattableTextComponent getDisplayName()
    {
        return new TranslationTextComponent(this.getTranslationKey());
    }
    
    public static EnumAttachmentType getSlot(int slot)
    {
        return EnumAttachmentType.VALUES[slot];
    }
    
    public static IFormattableTextComponent getDisplayName(int slot)
    {
        return EnumAttachmentType.getSlot(slot).getDisplayName();
    }
    
    public static void callForAll(GunItem gun, Consumer<AttachmentItem[]> consumer)
    {
        EnumAttachmentType.callForAll(gun.getAttachments(), consumer);
    }
    
    public static void callForAll(AttachmentItem[][] attachments, Consumer<AttachmentItem[]> consumer)
    {
        EnumAttachmentType.recCallForAll(consumer, attachments, new AttachmentItem[EnumAttachmentType.LENGTH], 0);
    }
    
    private static void recCallForAll(Consumer<AttachmentItem[]> consumer, AttachmentItem[][] attachments, AttachmentItem[] current, int slot)
    {
        if(slot < EnumAttachmentType.LENGTH)
        {
            for(int i = 0; i < attachments[slot].length; ++i)
            {
                current[slot] = attachments[slot][i];
                
                EnumAttachmentType.recCallForAll(consumer, attachments, current, slot + 1);
            }
        }
        else
        {
            consumer.accept(current);
        }
    }
}
