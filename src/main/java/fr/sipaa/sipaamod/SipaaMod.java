package fr.sipaa.sipaamod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

import fr.sipaa.selene.*;
import fr.sipaa.selene.overlays.*;
import fr.sipaa.sipaamod.commands.CommandSwitchGamemode;
import fr.sipaa.sipaamod.proxy.CommonProxy;
import fr.sipaa.sipaamod.world.OreGenerator;

@Mod(modid = SipaaMod.MODID, name = SipaaMod.NAME, version = SipaaMod.VERSION)
public class SipaaMod
{
    public static final String MODID = "sipaamod";
    public static final String NAME = "SipaaMod";
    public static final String VERSION = "1.0";
    public static final Boolean ISBETA = true;
    
    @SidedProxy(clientSide="fr.sipaa.sipaamod.proxy.ClientProxy", serverSide="fr.sipaa.sipaamod.proxy.DedicatedServerProxy")
    public static CommonProxy proxy;
    
    private static boolean isCInvOpened = false;
    private static Logger logger;
	public static boolean enableCreativeInv = false;
    
    public SipaaMod() {
        //MinecraftForge.EVENT_BUS.register(new RegisteringHandler());
    	Blocks.BEDROCK.setHardness(1);
    }
    
    public static boolean isMultiplayerServer()
    {
    	return !(Minecraft.getMinecraft().isSingleplayer() && !Minecraft.getMinecraft().getIntegratedServer().getPublic());
    }
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) throws LWJGLException
    {
    	logger = event.getModLog();
    	logger.info("Pre-Initializing proxy...");
    	proxy.preInit();
    	logger.info("Registering world generator...");
    	GameRegistry.registerWorldGenerator(new OreGenerator(), 0);
    	logger.info("Registering class 'fr.sipaa.sipaamod.SipaaMod' into Forge Event Bus");
    	MinecraftForge.EVENT_BUS.register(this);
    	
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	logger.info("Initializing proxy...");
    	proxy.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	logger.info("Post-Initializing proxy...");
    	proxy.postInit();
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderTick(RenderTickEvent event)
    {
    	Minecraft minecraft = Minecraft.getMinecraft();
    	if (minecraft.currentScreen == null)
    	{
    		for (int i = 0; i < SeleneRegistry.overlays.size(); i++) {
    			  SeleneRegistry.overlays.get(i).renderOverlay(minecraft);
    		}
    	}
    	//Minecraft.getMinecraft().fontRenderer.drawString(String.valueOf(Minecraft.getMinecraft().getDebugFPS()) + " FPS", 10, 10, 0);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onOpenGui(GuiOpenEvent event)
    {
    	if (event.getGui() != null)
    	{
    		if (event.getGui().getClass() == GuiMainMenu.class)
    		{
    			if (ModConfig.useCustomMainMenu == true)
    			{
    				if (ModConfig.useExperimentalMainMenu)
    				{
    					event.setGui(new SeleneScreen(new fr.sipaa.sipaamod.gui.SeleneGUIMainMenu()));
    				}
    				else
    				{
            			event.setGui(new fr.sipaa.sipaamod.gui.GuiMainMenu());
    				}
    			}
    		}
    		if (event.getGui().getClass() == GuiIngameMenu.class)
    		{
    			event.setGui(new fr.sipaa.sipaamod.gui.GuiIngameMenu());
    		}
    		
    	}
    }
}
