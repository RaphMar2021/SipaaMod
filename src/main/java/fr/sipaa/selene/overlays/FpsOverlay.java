package fr.sipaa.selene.overlays;

import fr.sipaa.selene.*;
import fr.sipaa.selene.render.*;
import fr.sipaa.sipaamod.ModConfig;
import net.minecraft.client.Minecraft;

public class FpsOverlay extends SeleneOverlay{
	public FpsOverlay()
	{
		System.out.println("hi");
		
		this.backgroundColor = ModConfig.client.fpsOverlayConfig.color;
		this.posX = ModConfig.client.fpsOverlayConfig.x;
		this.posY = ModConfig.client.fpsOverlayConfig.y;
		this.width = ModConfig.client.fpsOverlayConfig.width;
		this.height = ModConfig.client.fpsOverlayConfig.height;
		this.isEnabled = ModConfig.client.fpsOverlayConfig.enabled;
	}
	
	@Override
	public void renderOverlay(Minecraft m)
	{
		if (isEnabled)
		{
			super.renderOverlay(m);
		
        	String fps = String.valueOf(m.getDebugFPS()) + " FPS";
        
        	m.fontRenderer.drawString(fps, posX + width / 2 - m.fontRenderer.getStringWidth(fps) / 2, posY + height / 2 - m.fontRenderer.FONT_HEIGHT / 2, 16777215, false);
		}
			
	}
}
