package fr.sipaa.sipaamod;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

import fr.sipaa.selene.SeleneRegistry;
import fr.sipaa.selene.overlays.FpsOverlay;
import fr.sipaa.selene.render.Color;

@Config(modid = SipaaMod.MODID)
@Config.LangKey("sipaamod.config.title")
public class ModConfig {

	@Config.Comment("Use the custom main menu (You may restart the game in order to apply the value)")
	public static boolean useCustomMainMenu = true;

	@Config.Comment("Use the experimental Selene-based main menu.")
	public static boolean useExperimentalMainMenu = false;
	
	public static final Client client = new Client();

	public static class Client {
		
		public final SeleneOverlayConfig fpsOverlayConfig = new SeleneOverlayConfig();
		public final SeleneOverlayConfig coordinatesOverlayConfig = new SeleneOverlayConfig();

		public static class SeleneOverlayConfig {
			@Config.Comment("The x coordinate")
			public int x = 3;

			@Config.Comment("The y coordinate")
			public int y = 3;
			
			@Config.Comment("The x coordinate")
			public int width = 92;

			@Config.Comment("The y coordinate")
			public int height = 20;
			
			@Config.Comment("Show the overlay into the game")
			public boolean enabled = true;
			
			@Config.Comment("The background color")
			public Color color = new Color((byte)147, (byte)0, (byte)0, (byte)0);
		}
	}

	@Mod.EventBusSubscriber(modid = SipaaMod.MODID)
	private static class ConfigHandler {

		/**
		 * Inject the new values and save to the config file when the config has been changed from the GUI.
		 *
		 * @param event The event
		 */
		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(SipaaMod.MODID)) {
				ConfigManager.sync(SipaaMod.MODID, Config.Type.INSTANCE);
				// Reset Selene overlays so they can obtain the new config values
				SeleneRegistry.overlays.clear();
		    	SeleneRegistry.overlays.add(new FpsOverlay());
			}
		}
	}
}