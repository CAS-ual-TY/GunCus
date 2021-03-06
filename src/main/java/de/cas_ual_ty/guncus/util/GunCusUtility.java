package de.cas_ual_ty.guncus.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import de.cas_ual_ty.guncus.item.GunItem;
import de.cas_ual_ty.guncus.item.attachments.EnumAttachmentType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class GunCusUtility
{
    private static Method blockStatesInjector;
    private static Field nonNullListElements;
    
    static
    {
        GunCusUtility.blockStatesInjector = ObfuscationReflectionHelper.findMethod(PointOfInterestType.class, "func_221052_a", PointOfInterestType.class);
        GunCusUtility.nonNullListElements = ObfuscationReflectionHelper.findField(NonNullList.class, "field_191198_a");
        GunCusUtility.nonNullListElements.setAccessible(true);
    }
    
    public static final Hand[] HANDS = Hand.values();
    
    public static Hand[] intToHands(int handsInt)
    {
        if(handsInt == 1)
        {
            return new Hand[] { Hand.MAIN_HAND };
        }
        else if(handsInt == 2)
        {
            return new Hand[] { Hand.OFF_HAND };
        }
        else if(handsInt == 3)
        {
            return new Hand[] { Hand.MAIN_HAND, Hand.OFF_HAND };
        }
        
        return new Hand[] {};
    }
    
    public static AxisAlignedBB aabbFromVec3ds(Vector3d vec1, Vector3d vec2)
    {
        return new AxisAlignedBB(Math.min(vec1.x, vec2.x), Math.min(vec1.y, vec2.y), Math.min(vec1.z, vec2.z), Math.max(vec1.x, vec2.x), Math.max(vec1.y, vec2.y), Math.max(vec1.z, vec2.z));
    }
    
    public static ArrayList<ItemStack> createAllVariants(GunItem gun)
    {
        ArrayList<ItemStack> itemStacks = new ArrayList<>(gun.getVariantsAmt());
        
        EnumAttachmentType.callForAll(gun, (attachments) ->
        {
            itemStacks.add(gun.createVariant(attachments));
        });
        
        return itemStacks;
    }
    
    public static Set<BlockState> getAllStates(Block block)
    {
        return ImmutableSet.copyOf(block.getStateContainer().getValidStates());
    }
    
    public static void fixPOITypeBlockStates(PointOfInterestType poiType)
    {
        try
        {
            GunCusUtility.blockStatesInjector.invoke(null, poiType);
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    public static <E> void addAllToNonNullList(NonNullList<E> list, List<E> elements)
    {
        Object arrayList;
        try
        {
            arrayList = GunCusUtility.nonNullListElements.get(list);
            
            if(arrayList instanceof ArrayList)
            {
                ((ArrayList<E>)arrayList).addAll(elements);
                return;
            }
        }
        catch (Exception e)
        {
        }
        
        list.addAll(elements);
    }
}
