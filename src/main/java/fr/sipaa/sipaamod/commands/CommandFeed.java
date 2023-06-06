package fr.sipaa.sipaamod.commands;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class CommandFeed extends CommandBase {

	@Override
	public void execute(MinecraftServer arg0, ICommandSender arg1, String[] arg2) throws CommandException {
		EntityPlayerSP player = (EntityPlayerSP)arg1;
		
		if (player.experienceLevel < 5)
		{
			arg1.sendMessage(new TextComponentString("Sorry, but you need 5 XP levels to use this command!"));
		} else {
			player.getFoodStats().setFoodLevel(1000000);
			player.experienceLevel -= 1;
		}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "feed";
	}

	@Override
	public String getUsage(ICommandSender arg0) {
		// TODO Auto-generated method stub
		return "Feed the player (Only available when player have 5 XP levels)";
	}

}
