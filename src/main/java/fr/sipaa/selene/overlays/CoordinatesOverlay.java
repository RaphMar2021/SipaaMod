package fr.sipaa.selene.overlays;

import fr.sipaa.selene.*;
import fr.sipaa.selene.render.*;
import fr.sipaa.sipaamod.ModConfig;
import net.minecraft.client.Minecraft;

public class CoordinatesOverlay extends SeleneOverlay{
	public CoordinatesOverlay()
	{
		System.out.println("hi");
		
		this.backgroundColor = ModConfig.client.coordinatesOverlayConfig.color;
		this.posX = ModConfig.client.coordinatesOverlayConfig.x;
		this.posY = ModConfig.client.coordinatesOverlayConfig.y;
		this.width = ModConfig.client.coordinatesOverlayConfig.width;
		this.height = ModConfig.client.coordinatesOverlayConfig.height;
		this.isEnabled = ModConfig.client.coordinatesOverlayConfig.enabled;
	}
	
	@Override
	public void renderOverlay(Minecraft m)
	{
		if (isEnabled)
		{
			super.renderOverlay(m);
		
        	String xyz = String.valueOf((int)m.player.posX) + " " + String.valueOf((int)m.player.posY) + " " + String.valueOf((int)m.player.posZ);
        
        	m.fontRenderer.drawString(xyz, posX + width / 2 - m.fontRenderer.getStringWidth(xyz) / 2, posY + height / 2 - m.fontRenderer.FONT_HEIGHT / 2, 16777215, false);
		}
			
	}
}
