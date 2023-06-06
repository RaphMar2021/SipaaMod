package fr.sipaa.sipaamod.gui;

import java.util.Random;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import fr.sipaa.selene.*;
import fr.sipaa.selene.render.*;
import fr.sipaa.sipaamod.SipaaMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class SeleneGUIWindow extends SeleneGUIScreen {

    private static final ResourceLocation MINECRAFT_TITLE_TEXTURES = new ResourceLocation("textures/gui/title/minecraft.png");
    private ResourceLocation backgroundTexture = new ResourceLocation(SipaaMod.MODID, "textures/gui/wallpaper.png");
    private final float updateCounter;
    
    public int baseX = 200;
    public int baseY = 200;
    public int iX;
    public int iY;

    public boolean dragging = false;
    public boolean moving = false;
    
    public SeleneGUIWindow()
    {
        this.updateCounter = new Random().nextFloat();
        if (Display.getWidth() < 640 && Display.getHeight() < 480)
        {
            this.width = Display.getWidth();
            this.height = Display.getHeight();
        } else
        {
            this.width = Display.getWidth() / 2;
            this.height = Display.getHeight() / 2;
        }
    }

    public void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color)
    {
        fontRendererIn.drawStringWithShadow(text, (float)(x - fontRendererIn.getStringWidth(text) / 2), (float)y, color);
    }

	public void renderElement(Minecraft m, int x, int y) {
        if (Display.getWidth() < 640 && Display.getHeight() < 480)
        {
            this.width = Display.getWidth();
            this.height = Display.getHeight();
        } else
        {
            this.width = Display.getWidth() / 2;
            this.height = Display.getHeight() / 2;
        }

        /** BACKGROUND **/
        m.getTextureManager().bindTexture(this.backgroundTexture);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Gui.drawScaledCustomSizeModalRect(0, 0, 0, 0, 1, 1, this.width, this.height, 1, 1);
        GlStateManager.enableAlpha();

        m.fontRenderer.drawString("MX = " + String.valueOf(x) + " / MY = " + String.valueOf(y), 3, 3, -256);
        m.fontRenderer.drawString("IX = " + String.valueOf(iX) + " / IY = " + String.valueOf(iY), 3, 3 + (m.fontRenderer.FONT_HEIGHT * 1), -256);
        m.fontRenderer.drawString("MV = " + String.valueOf(moving) + " / DG = " + String.valueOf(dragging), 3, 3 + (m.fontRenderer.FONT_HEIGHT * 2), -256);
        
        /** WINDOW **/
    	if (Mouse.isButtonDown(0))
    	{
	        if (mouseHover(m, x, y, baseX, baseY, 100, 100))
	        {
        		dragging = true;
        		moving = true;
        		iX = (int)x - baseX;
        		iY = (int)y - baseY;
        	}
        }
    	else
    	{
    		dragging = false;
    		moving = false;
    	}
    	
    	if (moving)
    	{
    		baseX = x - iX;
    		baseY = y - iY;
    	}

        Renderer.drawRect(baseX, baseY, baseX + 100, baseY + 100, new Color(1f, 0, 0, 0));
        Renderer.drawRect(baseX, baseY, baseX + 100, baseY + 20, new Color(1f, 0.15f, 0.15f, 0.15f));
	}

    public boolean mouseHover(Minecraft mc, int mouseX, int mouseY, int x, int y, int width, int height)
    {
        return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }

}
