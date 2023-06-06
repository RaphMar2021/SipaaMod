package fr.sipaa.selene;

import java.util.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SeleneScreen extends GuiScreen {
	public SeleneGUIScreen currentScreen;
	
	public SeleneScreen(SeleneGUIScreen screen) {
		currentScreen = screen;
		currentScreen.parent = this;
		currentScreen.elements = new ArrayList<SeleneGUIElement>();
		currentScreen.init();
	}
	
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        
        currentScreen.renderElement(Minecraft.getMinecraft(), mouseX, mouseY);
        currentScreen.updateElement(Minecraft.getMinecraft(), mouseX, mouseY);
        
        for (SeleneGUIElement el : currentScreen.elements)
        {
        	el.renderElement(Minecraft.getMinecraft(), mouseX, mouseY);
        	el.updateElement(Minecraft.getMinecraft(), mouseX, mouseY);
        }
    }
}
