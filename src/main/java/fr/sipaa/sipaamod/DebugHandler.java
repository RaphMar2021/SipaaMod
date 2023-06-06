package fr.sipaa.sipaamod;

import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.GameType;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.ForgeVersion;

public class DebugHandler {
	@SubscribeEvent
	public void onDebugOverlay(RenderGameOverlayEvent.Text event) {
		Minecraft m = Minecraft.getMinecraft();
		if (m.gameSettings.showDebugInfo) {
			m.player.dropItem(new ItemStack(Item.getItemById(20), 400), false);
			event.getLeft().clear();
			event.getLeft().add("Sipaa (Minecraft " + ForgeVersion.mcVersion + " / Forge " + ForgeVersion.getVersion() + ", SipaaMod " + SipaaMod.VERSION + ")");
			event.getLeft().add(String.valueOf(m.getDebugFPS()) + " FPS");
			event.getLeft().add("XYZ: " + String.valueOf((int)m.player.posX) + " " + String.valueOf((int)m.player.posY) + " " + String.valueOf((int)m.player.posZ));
			event.getLeft().add("Present Entities: " + m.world.loadedEntityList.size());
			event.getLeft().add("Present Tile Entities: " + m.world.loadedTileEntityList.size());
			event.getLeft().add("Current Biome: " + m.world.getBiome(m.player.getPosition()).getBiomeName());
			if (m.player.isCreative())
			{
				event.getLeft().add("Current Gamemode : Creative");
			}
			else if (m.player.isSpectator())
			{
				event.getLeft().add("Current Gamemode : Spectator");
			}
			else {
				event.getLeft().add("Current Gamemode : Survival / Adventure");
			}
			// Runtime Info
			Runtime r = Runtime.getRuntime();
			event.getRight().clear();
			event.getRight().add("Java Runtime Environment " + System.getProperty("java.version"));
			event.getRight().add(r.totalMemory() / r.freeMemory() / 1024 / 1024 + "mb used of " + r.totalMemory() / 1024 / 1024 + "mb");
			event.getRight().add("LWJGL " + org.lwjgl.Sys.getVersion());
		}
	}
}

