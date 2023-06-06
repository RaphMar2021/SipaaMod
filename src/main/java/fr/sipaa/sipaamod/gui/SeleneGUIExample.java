package fr.sipaa.sipaamod.gui;

import org.lwjgl.input.Mouse;

import fr.sipaa.selene.*;
import fr.sipaa.selene.render.*;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SeleneGUIExample extends SeleneGUIScreen {
	public void renderElement(Minecraft m) {
		m.fontRenderer.drawString("Hello, World from Selene!", 10, 10, 16777215);
		Renderer.drawRect(100, 100, 98, 20, new Color(0.5f, 0, 0, 0));
		if (mousePressed(m, Mouse.getX(), Mouse.getY(), 100, 100, 98, 20)) {
			m.currentScreen = null;
		}
	}

    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY, int x, int y, int width, int height)
    {
        return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }
}
