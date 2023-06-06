package fr.sipaa.sipaamod.proxy;

import org.lwjgl.opengl.Display;

import fr.sipaa.selene.SeleneRegistry;
import fr.sipaa.selene.overlays.*;
import fr.sipaa.sipaamod.DebugHandler;
import fr.sipaa.sipaamod.SipaaModItems;
import fr.sipaa.sipaamod.commands.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ClientProxy extends CommonProxy {
	
	public ClientProxy()
	{
		super();
		logger.info("Looks like you are running SipaaMod on client side!");
	}
	
	private void loadModel(Item item)
	{
		logger.info("Loading model for item " + item.getRegistryName());
		ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(item.getRegistryName(), "inventory");
		final int DEFAULT_ITEM_SUBTYPE = 0;
		ModelLoader.setCustomModelResourceLocation(item, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation);
	}
	
	public void preInit()
	{
		super.preInit();
		logger.info("Registring 'fr.sipaa.sipaamod.DebugHandler' in Forge Event Bus");
	    MinecraftForge.EVENT_BUS.register(new DebugHandler());
		logger.info("Changing OpenGL display title");
	    Display.setTitle(Minecraft.getMinecraft().getSession().getUsername() + " - Sipaa");
		logger.info("Registring Selene overlays");
    	SeleneRegistry.overlays.add(new FpsOverlay());
    	SeleneRegistry.overlays.add(new CoordinatesOverlay());
		loadModel(SipaaModItems.sodiumIngot);
		loadModel(SipaaModItems.sodiumSword);
		loadModel(SipaaModItems.sodiumPickaxe);
		loadModel(SipaaModItems.sodiumShovel);
		loadModel(SipaaModItems.sodiumAxe);
		loadModel(SipaaModItems.sodiumHelmet);
		loadModel(SipaaModItems.sodiumChestplate);
		loadModel(SipaaModItems.sodiumLeggings);
		loadModel(SipaaModItems.sodiumBoots);
		loadModel(SipaaModItems.itemSodiumOre);
		loadModel(SipaaModItems.itemSodiumBlock);
	}

	public void init()
	{
    	final ClientCommandHandler clientCommandHandler = ClientCommandHandler.instance;
		clientCommandHandler.registerCommand(new CommandSwitchGamemode());
		clientCommandHandler.registerCommand(new CommandFeed());
		clientCommandHandler.registerCommand(new CommandFly());
		clientCommandHandler.registerCommand(new CommandFurnace());
		clientCommandHandler.registerCommand(new CommandGodMode());
		clientCommandHandler.registerCommand(new CommandCreative());
	}

	public void postInit()
	{
		  
	}
}
