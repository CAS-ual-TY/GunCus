package de.cas_ual_ty.guncus.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;

import de.cas_ual_ty.guncus.item.ItemGun;
import de.cas_ual_ty.guncus.util.GunCusUtility;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

public class CommandGunCus
{
    public static void register(CommandDispatcher<CommandSource> dispatcher)
    {
        dispatcher.register(Commands.literal("guncus").requires((arg1) ->
        {
            return arg1.getServer().isSinglePlayer() || arg1.hasPermissionLevel(0);
        }).then((Commands.literal("on").executes((context) ->
        {
            return CommandGunCus.change(context.getSource(), true);
        }))).then(Commands.literal("off").executes((context) ->
        {
            return CommandGunCus.change(context.getSource(), false);
        })));
    }
    
    public static int change(CommandSource source, boolean on)
    {
        if(source.getEntity() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity)source.getEntity();
            
            for(Hand hand : GunCusUtility.HANDS)
            {
                if(player.getHeldItem(hand).getItem() instanceof ItemGun)
                {
                    ItemGun gun = (ItemGun)player.getHeldItem(hand).getItem();
                    gun.setNBTAccessoryTurnedOn(player.getHeldItem(hand), on);
                }
            }
        }
        
        return Command.SINGLE_SUCCESS;
    }
}
