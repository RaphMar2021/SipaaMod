package fr.sipaa.selene;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import fr.sipaa.selene.render.*;
import fr.sipaa.sipaamod.SipaaMod;

public class SeleneOverlay {
	/** Position **/
	public int posX = 3;
	public int posY = 3;
	
	/** Size **/
	public int width = 98;
	public int height = 20;
	
	/** Properties **/
	public boolean isEnabled = true;
	
	/** Colors **/
	public Color backgroundColor = new Color(0.5f, 0, 0, 0);
	
	public void renderOverlay(Minecraft m) 
	{
		Renderer.drawRect(posX, posY, width, height, backgroundColor);
	}
}
