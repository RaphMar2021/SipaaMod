package fr.sipaa.sipaamod.gui;

import java.util.Random;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import fr.sipaa.selene.*;
import fr.sipaa.selene.render.*;
import fr.sipaa.selene.elements.*;
import fr.sipaa.sipaamod.SipaaMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class SeleneGUIMainMenu extends SeleneGUIScreen {

    private static final ResourceLocation MINECRAFT_TITLE_TEXTURES = new ResourceLocation("textures/gui/title/minecraft.png");
    private ResourceLocation backgroundTexture = new ResourceLocation(SipaaMod.MODID, "textures/gui/wallpaper.png");
    private final float updateCounter;
    
    public SeleneGUIMainMenu()
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

    public void init()
    {
    	Button mainMenuBtn = new Button(this);
    	mainMenuBtn.posX = 10;
    	mainMenuBtn.posY = this.height - 10 - mainMenuBtn.height;
    	mainMenuBtn.setText("Return to MC MainMenu");
    	elements.add(mainMenuBtn);
    }
    
    public void elementPressed(int id)
    {
    	if (id == 0)
    	{
    		parent.mc.displayGuiScreen(new GuiMainMenu());
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

        /** MINECRAFT LOGO **/
        int j = this.width / 2 - 137;
        
        m.getTextureManager().bindTexture(MINECRAFT_TITLE_TEXTURES);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        if ((double)this.updateCounter < 1.0E-4D)
        {
            Renderer.drawTexturedModalRect(j + 0, 30, 0, 0, 99, 44);
            Renderer.drawTexturedModalRect(j + 99, 30, 129, 0, 27, 44);
            Renderer.drawTexturedModalRect(j + 99 + 26, 30, 126, 0, 3, 44);
            Renderer.drawTexturedModalRect(j + 99 + 26 + 3, 30, 99, 0, 26, 44);
            Renderer.drawTexturedModalRect(j + 155, 30, 0, 45, 155, 44);
        }
        else
        {
        	Renderer.drawTexturedModalRect(j + 0, 30, 0, 0, 155, 44);
            Renderer.drawTexturedModalRect(j + 155, 30, 0, 45, 155, 44);
        }

        /** SPLASH TEXT **/
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)(this.width / 2 + 90), 70.0F, 0.0F);
        GlStateManager.rotate(-20.0F, 0.0F, 0.0F, 1.0F);
        float f = 1.8F - MathHelper.abs(MathHelper.sin((float)(Minecraft.getSystemTime() % 1000L) / 1000.0F * ((float)Math.PI * 2F)) * 0.1F);
        f = f * 100.0F / (float)(m.fontRenderer.getStringWidth("Selene... BETA!") + 32);
        GlStateManager.scale(f, f, f);
        this.drawCenteredString(m.fontRenderer, "Selene... BETA!", 0, -8, -256);
        GlStateManager.popMatrix();
        
        Renderer.drawRect(1, 1, 117, this.height - 2, new Color(0.5f, 0, 0, 0));
        Renderer.drawVerticalLine(118, -1, this.height + 1, new Color(1f, 0, 0.5f, 1f));
        
        /** RENDERER INFO **/
        FontRenderer fnt = m.fontRenderer;
        fnt.drawString("Selene Renderer V" + SeleneVersion.VERSION, 3, 3, 16777215);
        fnt.drawString(String.valueOf(m.getDebugFPS()) + " FPS", 3, 3 + (fnt.FONT_HEIGHT * 1), 16777215);
	}

    public boolean mouseHover(Minecraft mc, int mouseX, int mouseY, int x, int y, int width, int height)
    {
        return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }

}
