package fr.sipaa.sipaamod.commands;

import fr.sipaa.sipaamod.SipaaMod;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class CommandCreative extends CommandBase {

	@Override
	public void execute(MinecraftServer arg0, ICommandSender arg1, String[] arg2) throws CommandException {
		EntityPlayerSP player = (EntityPlayerSP)arg1;
		
		if (SipaaMod.isMultiplayerServer())
		{
			arg1.sendMessage(new TextComponentString("Sorry, but you are on a multiplayer server. You can't set godmode on multiplayer servers."));
		}
		else
		{
			SipaaMod.enableCreativeInv = !SipaaMod.enableCreativeInv;
			arg1.sendMessage(new TextComponentString("Creative inventory sucessfully toggled."));
		}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "creative";
	}

	@Override
	public String getUsage(ICommandSender arg0) {
		// TODO Auto-generated method stub
		return "Toggle the creative inventory for the player";
	}

}
