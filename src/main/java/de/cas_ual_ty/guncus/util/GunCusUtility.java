package de.cas_ual_ty.guncus.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableSet;

import de.cas_ual_ty.guncus.item.ItemGun;
import de.cas_ual_ty.guncus.item.attachments.EnumAttachmentType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class GunCusUtility
{
    private static Method blockStatesInjector;
    
    static
    {
        GunCusUtility.blockStatesInjector = ObfuscationReflectionHelper.findMethod(PointOfInterestType.class, "func_221052_a", PointOfInterestType.class);
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
    
    public static AxisAlignedBB aabbFromVec3ds(Vec3d vec1, Vec3d vec2)
    {
        return new AxisAlignedBB(Math.min(vec1.x, vec2.x), Math.min(vec1.y, vec2.y), Math.min(vec1.z, vec2.z), Math.max(vec1.x, vec2.x), Math.max(vec1.y, vec2.y), Math.max(vec1.z, vec2.z));
    }
    
    public static ArrayList<ItemStack> createAllVariants(ItemGun gun)
    {
        ArrayList<ItemStack> itemStacks = new ArrayList<>();
        
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
    
    public static PointOfInterestType pointOfInterestType(String p1, Set<BlockState> p2, int p3, int p4)
    {
        try
        {
            //          Constructor<PointOfInterestType> c = (Constructor<PointOfInterestType>)PointOfInterestType.class.getDeclaredConstructors()[1];
            Constructor<PointOfInterestType> c = PointOfInterestType.class.getDeclaredConstructor(String.class, Set.class, Integer.TYPE, Integer.TYPE);
            c.setAccessible(true);
            return c.newInstance(p1, p2, p3, p4);
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        catch (SecurityException e)
        {
            e.printStackTrace();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static VillagerProfession villagerProfession(String p1, PointOfInterestType p2, ImmutableSet<Item> p3, ImmutableSet<Block> p4, @Nullable SoundEvent p5)
    {
        try
        {
            Constructor<VillagerProfession> c = VillagerProfession.class.getDeclaredConstructor(String.class, PointOfInterestType.class, ImmutableSet.class, ImmutableSet.class, SoundEvent.class);
            c.setAccessible(true);
            return c.newInstance(p1, p2, p3, p4, p5);
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        catch (SecurityException e)
        {
            e.printStackTrace();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
        
        return null;
    }
}
