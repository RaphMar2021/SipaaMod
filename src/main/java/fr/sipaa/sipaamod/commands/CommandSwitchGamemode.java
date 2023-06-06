package fr.sipaa.sipaamod.commands;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldSettings;

public class CommandSwitchGamemode extends CommandBase {
  public String getName() {
    return "gm";
  }
  
  public int getRequiredPermissionLevel() {
    return 2;
  }
  
  public String getUsage(ICommandSender sender) {
    return "commands.gamemode.usage";
  }
  
  public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
	  // Try to run the /gamemode command with the arguments of this command. (like a shortcut)
	  String[] args2 = args;
	  
	  if (args.length == 1)
	  {
		  args2 = new String[] { args[0], Minecraft.getMinecraft().getSession().getUsername() };
	  }
	  
	  String arguments = "/gamemode ";
	  for (int i = 0; i < args2.length; i++)
	  {
		  arguments = arguments + args2[i] + " ";
	  }
	  System.out.println(arguments);
	  server.commandManager.executeCommand(sender, arguments);
  }
}
