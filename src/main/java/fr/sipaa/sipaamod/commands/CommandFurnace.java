package fr.sipaa.sipaamod.commands;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class CommandFurnace extends CommandBase {

	@Override
	public void execute(MinecraftServer arg0, ICommandSender arg1, String[] arg2) throws CommandException {
		EntityPlayerSP player = (EntityPlayerSP)arg1;
		
		if (player.experienceLevel < 5)
		{
			arg1.sendMessage(new TextComponentString("Sorry, but you need 5 XP levels to use this command!"));
		} else {
			NBTTagCompound nbt = player.getEntityData();
			int selSlot = nbt.getInteger("SelectedItemSlot") + 1;
			
			ItemStack i = FurnaceRecipes.instance().getSmeltingResult(player.inventory.getStackInSlot(selSlot));
			
			player.experienceLevel -= 1;
			player.experience += FurnaceRecipes.instance().getSmeltingExperience(player.inventory.getStackInSlot(selSlot));
			player.inventory.removeStackFromSlot(selSlot);
			player.inventory.add(selSlot, i);
		}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "furnace";
	}

	@Override
	public String getUsage(ICommandSender arg0) {
		// TODO Auto-generated method stub
		return "Breh i don't know";
	}

}
