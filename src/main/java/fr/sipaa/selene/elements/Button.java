package fr.sipaa.selene.elements;

import org.lwjgl.input.Mouse;

import fr.sipaa.selene.SeleneGUIElement;
import fr.sipaa.selene.render.*;
import fr.sipaa.sipaamod.gui.GuiMainMenu;
import net.minecraft.client.Minecraft;

public class Button extends SeleneGUIElement {
	private String text = "Button";
	private Color bg = new Color(0.5f, 0, 0, 0);

	public Button() { super(); }
	public Button(SeleneGUIElement parent) { super(parent); }
	
	public String getText() {
		return text;
	}
	
	public Button setText(String text) {
		this.text = text;
		return this;
	}

	public Color getBackground() {
		return bg;
	}
	
	public Button setBackground(Color bg) {
		this.bg = bg;
		return this;
	}
	
	public void renderElement(Minecraft m, int x, int y) {
		Renderer.drawRect(this.posX, this.posY, this.posX + width, this.posY + height, bg);
		m.fontRenderer.drawString(text, posX + (width / 2 - m.fontRenderer.getStringWidth(text) / 2), posY + (height / 2 - m.fontRenderer.FONT_HEIGHT / 2), 16777215);
	}
	
	public void updateElement(Minecraft m, int x, int y) {
        if (mouseHover(x, y, posX, posY, width, height))
        {
        	if (Mouse.isButtonDown(0))
        	{
        		if (parent != null)
        		{
        			parent.elementPressed(this.id);
        		}
        	}
        }
	}
}
