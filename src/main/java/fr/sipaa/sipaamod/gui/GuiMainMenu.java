package fr.sipaa.sipaamod.gui;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jline.reader.impl.history.DefaultHistory;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.Project;

import com.google.common.collect.Lists;

import fr.sipaa.selene.SeleneScreen;
import fr.sipaa.selene.render.*;
import fr.sipaa.sipaamod.SipaaMod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonLanguage;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameType;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMainMenu extends GuiScreen
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Random RANDOM = new Random();
    /** Counts the number of screen updates. */
    private final float updateCounter;
    /** The splash message. */
    private String splashText;
    private GuiButton buttonResetDemo;
    /** Timer used to rotate the panorama, increases every tick. */
    private int panoramaTimer;
    /** Texture allocated for the current viewport of the main menu's panorama background. */
    private DynamicTexture viewportTexture;
    /** The Object object utilized as a thread lock when performing non thread-safe operations */
    private final Object threadLock = new Object();
    public static final String MORE_INFO_TEXT = "Please click " + TextFormatting.UNDERLINE + "here" + TextFormatting.RESET + " for more information.";
    // arrière plan personnalisé :
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("sipaamod", "textures/gui/background.png");
    /** Width of openGLWarning2 */
    private int openGLWarning2Width;
    /** Width of openGLWarning1 */
    private int openGLWarning1Width;
    /** Left x coordinate of the OpenGL warning */
    private int openGLWarningX1;
    /** Text Button Lists **/
    protected List<GuiTextButton> textButtonList = Lists.<GuiTextButton>newArrayList();
    /** Top y coordinate of the OpenGL warning */
    private int openGLWarningY1;
    /** Right x coordinate of the OpenGL warning */
    private int openGLWarningX2;
    /** Bottom y coordinate of the OpenGL warning */
    private int openGLWarningY2;
    /** OpenGL graphics card warning. */
    private String openGLWarning1;
    /** OpenGL graphics card warning. */
    private String openGLWarning2;
    /** Link to the Mojang Support about minimum requirements */
    private String openGLWarningLink;
    private static final ResourceLocation SPLASH_TEXTS = new ResourceLocation("texts/splashes.txt");
    private static final ResourceLocation MINECRAFT_TITLE_TEXTURES = new ResourceLocation("textures/gui/title/minecraft.png");
    /** An array of all the paths to the panorama pictures. */
    private static final ResourceLocation[] TITLE_PANORAMA_PATHS = new ResourceLocation[] {new ResourceLocation("textures/gui/title/background/panorama_0.png"), new ResourceLocation("textures/gui/title/background/panorama_1.png"), new ResourceLocation("textures/gui/title/background/panorama_2.png"), new ResourceLocation("textures/gui/title/background/panorama_3.png"), new ResourceLocation("textures/gui/title/background/panorama_4.png"), new ResourceLocation("textures/gui/title/background/panorama_5.png")};
    private ResourceLocation backgroundTexture = new ResourceLocation(SipaaMod.MODID, "textures/gui/wallpaper.png");
    private GuiButton modButton;
	private boolean isDemo = true;

    public GuiMainMenu()
    {
        this.openGLWarning2 = MORE_INFO_TEXT;
        this.splashText = "missingno";
        IResource iresource = null;

        try
        {
            List<String> list = Lists.<String>newArrayList();
            iresource = Minecraft.getMinecraft().getResourceManager().getResource(SPLASH_TEXTS);
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(iresource.getInputStream(), Charsets.UTF_8));
            String s;

            while ((s = bufferedreader.readLine()) != null)
            {
                s = s.trim();

                if (!s.isEmpty())
                {
                    list.add(s);
                }
            }

            if (!list.isEmpty())
            {
                while (true)
                {
                    this.splashText = (String)list.get(RANDOM.nextInt(list.size()));

                    if (this.splashText.hashCode() != 125780783)
                    {
                        break;
                    }
                }
            }
        }
        catch (IOException var8)
        {
            ;
        }
        finally
        {
            IOUtils.closeQuietly((Closeable)iresource);
        }

        this.updateCounter = RANDOM.nextFloat();
        this.openGLWarning1 = "";

        if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.areShadersSupported())
        {
            this.openGLWarning1 = I18n.format("title.oldgl1", new Object[0]);
            this.openGLWarning2 = I18n.format("title.oldgl2", new Object[0]);
            this.openGLWarningLink = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
        }

        String s1 = System.getProperty("java.version");

        if (s1 != null && (s1.startsWith("1.6") || s1.startsWith("1.7")))
        {
            this.openGLWarning1 = I18n.format("title.oldjava1", new Object[0]);
            this.openGLWarning2 = I18n.format("title.oldjava2", new Object[0]);
            this.openGLWarningLink = "https://help.mojang.com/customer/portal/articles/2636196?ref=game";
        }
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        ++this.panoramaTimer;
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
    	this.isDemo = mc.isDemo();
        this.viewportTexture = new DynamicTexture(256, 256);
        Random r = new Random();
        int rn = r.nextInt(5);
        this.backgroundTexture = new ResourceLocation("sipaamod", "textures/gui/wallpapers/wallpaper" + String.valueOf(rn) + ".png");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        if (calendar.get(2) + 1 == 12 && calendar.get(5) == 24)
        {
            this.splashText = "Merry X-mas!";
        }
        else if (calendar.get(2) + 1 == 1 && calendar.get(5) == 1)
        {
            this.splashText = "Happy new year!";
        }
        else if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31)
        {
            this.splashText = "Spooky Scary Skeletons!";
        }
        else if (calendar.get(2) + 1 == 2 && calendar.get(5) == 24)
        {
            this.splashText = "Long live Ukraine!";
            this.backgroundTexture = new ResourceLocation("sipaamod", "textures/gui/wallpapers/longliveukraine.png");
        }
        else if (mc.world != null)
        {
            this.mc.player.respawnPlayer();
            this.mc.player.setGameType(GameType.SPECTATOR);
        	this.splashText = "You unlocked the secret main menu!";
        }

        int i = 24;
        int j = this.height / 4 + 48;

        if (this.mc.isDemo())
        {
            this.addDemoButtons(j, 24);
        }
        else
        {
            this.addSingleplayerMultiplayerButtons(j, 24);
        }

        this.buttonList.add(new GuiButton(0, 10, j + 24 * 3, 98, 20, I18n.format("menu.options", new Object[0])));
        this.buttonList.add(new GuiButton(4, 10, j + 24 * 4, 98, 20, I18n.format("menu.quit", new Object[0])));
        //this.buttonList.add(new GuiButtonLanguage(5, this.width / 2 - 124, j + 72 + 12));

        synchronized (this.threadLock)
        {
            this.openGLWarning1Width = this.fontRenderer.getStringWidth(this.openGLWarning1);
            this.openGLWarning2Width = this.fontRenderer.getStringWidth(this.openGLWarning2);
            int k = Math.max(this.openGLWarning1Width, this.openGLWarning2Width);
            this.openGLWarningX1 = (this.width - k) / 2;
            //this.openGLWarningY1 = ((GuiButton)this.buttonList.get(0)).yPosition - 24;
            this.openGLWarningX2 = this.openGLWarningX1 + k;
            this.openGLWarningY2 = this.openGLWarningY1 + 24;
        }

        this.mc.setConnectedToRealms(false);
    }

    /**
     * Adds Singleplayer and Multiplayer buttons on Main Menu for players who have bought the game.
     */
    private void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_)
    {
        this.buttonList.add(new GuiButton(1, 10, p_73969_1_, 98, 20, I18n.format("menu.singleplayer", new Object[0])));
        this.buttonList.add(new GuiButton(2, 10, p_73969_1_ + p_73969_2_ * 1, 98, 20, I18n.format("menu.multiplayer", new Object[0])));
        this.buttonList.add(modButton = new GuiButton(6, 10, p_73969_1_ + p_73969_2_ * 2, 98, 20, "Selene Tests"));
    }

    /**
     * Adds Demo buttons on Main Menu for players who are playing Demo.
     */
    private void addDemoButtons(int p_73972_1_, int p_73972_2_)
    {
    	isDemo  = true;
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id == 0)
        {
            this.mc.displayGuiScreen(new fr.sipaa.sipaamod.gui.GuiOptions(this, this.mc.gameSettings));
        }

        if (button.id == 5)
        {
            this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
        }

        if (button.id == 1)
        {
            this.mc.displayGuiScreen(new GuiWorldSelection(this));
        }

        if (button.id == 2)
        {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }

        if (button.id == 4)
        {
            this.mc.shutdown();
        }

        if (button.id == 6)
        {
            this.mc.displayGuiScreen(new SeleneScreen(new SeleneGUIExample()));
        }

        if (button.id == 11)
        {
            //this.mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.DEMO_WORLD_SETTINGS);
        }

        if (button.id == 12)
        {
            ISaveFormat isaveformat = this.mc.getSaveLoader();
            WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");

            if (worldinfo != null)
            {
                this.mc.displayGuiScreen(new GuiYesNo(this, I18n.format("selectWorld.deleteQuestion", new Object[0]), "\'" + worldinfo.getWorldName() + "\' " + I18n.format("selectWorld.deleteWarning", new Object[0]), I18n.format("selectWorld.deleteButton", new Object[0]), I18n.format("gui.cancel", new Object[0]), 12));
            }
        }
    }

    public void confirmClicked(boolean result, int id)
    {
        if (result && id == 12)
        {
            ISaveFormat isaveformat = this.mc.getSaveLoader();
            isaveformat.flushCache();
            isaveformat.deleteWorldDirectory("Demo_World");
            this.mc.displayGuiScreen(this);
        }
        else if (id == 12)
        {
            this.mc.displayGuiScreen(this);
        }
        else if (id == 13)
        {
            if (result)
            {
                try
                {
                    Class<?> oclass = Class.forName("java.awt.Desktop");
                    Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
                    oclass.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {new URI(this.openGLWarningLink)});
                }
                catch (Throwable throwable)
                {
                    LOGGER.error("Couldn\'t open link", throwable);
                }
            }

            this.mc.displayGuiScreen(this);
        }
    }

    /**
     * Draws the main menu panorama
     */
    private void drawPanorama(int mouseX, int mouseY, float partialTicks)
    {
    }

    /**
     * Rotate and blurs the skybox view in the main menu
     */
    private void rotateAndBlurSkybox(float partialTicks)
    {
    }

    /**
     * Renders the skybox in the main menu
     */
    private void renderSkybox(int mouseX, int mouseY, float partialTicks)
    {
    }

    public void DrawQuad(int x, int y, float width, float height, float a, float r, float g, float b) {
        GL11.glColor4f(a, r, g, b);
        
        GL11.glBegin(GL11.GL_QUADS); 

        GL11.glVertex2f(-x, y);
        GL11.glVertex2f(width, y);
        GL11.glVertex2f(width, -height);
        GL11.glVertex2f(-x, -height);

        GL11.glEnd();
    }
    
    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
    	if (mc.world == null)
    	{
            mc.getTextureManager().bindTexture(this.backgroundTexture);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            Gui.drawScaledCustomSizeModalRect(0, 0, 0, 0, 1, 1, this.width, this.height, 1, 1);
            GlStateManager.enableAlpha();
    	}
    	else
    	{
    		mc.player.setPosition(mc.player.getPosition().getX() - 1, mc.player.getPosition().getY(), mc.player.getPosition().getZ());
    	}
        
    	if (isDemo)
    	{
    		this.drawCenteredString(mc.fontRenderer, "Hi, fellow Minecraft player! I am really sorry to tell you that Minecraft demo mode isn't available.", width / 2, height / 2 - mc.fontRenderer.FONT_HEIGHT / 2, 16777215);
    		this.drawCenteredString(mc.fontRenderer, "But you can fix that using one of these solutions :", width / 2, height / 2 - mc.fontRenderer.FONT_HEIGHT / 2 + mc.fontRenderer.FONT_HEIGHT, 16777215);
    		this.drawCenteredString(mc.fontRenderer, "1) Buy Minecraft (recommended to have best multiplayer experience)", width / 2, height / 2 - mc.fontRenderer.FONT_HEIGHT / 2 + mc.fontRenderer.FONT_HEIGHT + mc.fontRenderer.FONT_HEIGHT, 16777215);
    		this.drawCenteredString(mc.fontRenderer, "2) Use a cracked version of the game (usually by lanching a mod developement environment or using a cracked launcher)", width / 2, height / 2 - mc.fontRenderer.FONT_HEIGHT / 2 + mc.fontRenderer.FONT_HEIGHT + mc.fontRenderer.FONT_HEIGHT + mc.fontRenderer.FONT_HEIGHT, 16777215);
    	}
    	else
    	{
            int j = this.width / 2 - 137;
            
            this.mc.getTextureManager().bindTexture(MINECRAFT_TITLE_TEXTURES);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            if ((double)this.updateCounter < 1.0E-4D)
            {
                this.drawTexturedModalRect(j + 0, 30, 0, 0, 99, 44);
                this.drawTexturedModalRect(j + 99, 30, 129, 0, 27, 44);
                this.drawTexturedModalRect(j + 99 + 26, 30, 126, 0, 3, 44);
                this.drawTexturedModalRect(j + 99 + 26 + 3, 30, 99, 0, 26, 44);
                this.drawTexturedModalRect(j + 155, 30, 0, 45, 155, 44);
            }
            else
            {
                this.drawTexturedModalRect(j + 0, 30, 0, 0, 155, 44);
                this.drawTexturedModalRect(j + 155, 30, 0, 45, 155, 44);
            }

            GlStateManager.pushMatrix();
            GlStateManager.translate((float)(this.width / 2 + 90), 70.0F, 0.0F);
            GlStateManager.rotate(-20.0F, 0.0F, 0.0F, 1.0F);
            float f = 1.8F - MathHelper.abs(MathHelper.sin((float)(Minecraft.getSystemTime() % 1000L) / 1000.0F * ((float)Math.PI * 2F)) * 0.1F);
            f = f * 100.0F / (float)(this.fontRenderer.getStringWidth(this.splashText) + 32);
            GlStateManager.scale(f, f, f);
            this.drawCenteredString(this.fontRenderer, this.splashText, 0, -8, -256);
            GlStateManager.popMatrix();
            
            Renderer.drawRect(1, 1, 117, this.height - 2, new Color(0.5f, 0, 0, 0));
            Renderer.drawVerticalLine(118, -1, this.height + 1, new Color(1f, 0, 0.5f, 1f));
            
            String s = "Sipaa " + ForgeVersion.mcVersion;

            if (this.mc.isDemo())
            {
                s = s + " Demo";
            }
            else
            {
                s = s + ("release".equalsIgnoreCase(this.mc.getVersionType()) ? "" : "/" + this.mc.getVersionType());
            }
            for (int i = 0; i < this.textButtonList.size(); ++i)
            {
                ((GuiTextButton)this.textButtonList.get(i)).drawButton(this.mc, mouseX, mouseY, partialTicks);
            }
            this.drawString(this.fontRenderer, s, 2, this.height - this.fontRenderer.FONT_HEIGHT, 16777215);

            String s1 = "Thanks to Forge for the mod loader";
            String s2 = "Copyright RaphMar2021, Mojang AB. Do not distribute!";
            this.drawString(this.fontRenderer, s1, this.width - this.fontRenderer.getStringWidth(s1) - 2, this.height - 10 - this.fontRenderer.FONT_HEIGHT, -1);
            this.drawString(this.fontRenderer, s2, this.width - this.fontRenderer.getStringWidth(s2) - 2, this.height - 10, -1);

            if (this.openGLWarning1 != null && !this.openGLWarning1.isEmpty())
            {
                drawRect(this.openGLWarningX1 - 2, this.openGLWarningY1 - 2, this.openGLWarningX2 + 2, this.openGLWarningY2 - 1, 1428160512);
                this.drawString(this.fontRenderer, this.openGLWarning1, this.openGLWarningX1, this.openGLWarningY1, -1);
//                this.drawString(this.fontRenderer, this.openGLWarning2, (this.width - this.openGLWarning2Width) / 2, ((GuiButton)this.buttonList.get(0)).yPosition - 12, -1);
            }
            
            if (SipaaMod.ISBETA)
            {
            	drawCenteredString(fontRenderer, "! SipaaMod BETA version !",width / 2, 5, 16777215);
            	drawCenteredString(fontRenderer, "! It could be that unfinished elements or bugs are present !",width / 2, 5 + fontRenderer.FONT_HEIGHT, 16777215);
            }
            
            //drawRect(1, 1, 117, this.height - 2, 1428160512);
            
            super.drawScreen(mouseX, mouseY, partialTicks);
    	}
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        synchronized (this.threadLock)
        {
            if (!this.openGLWarning1.isEmpty() && !StringUtils.isNullOrEmpty(this.openGLWarningLink) && mouseX >= this.openGLWarningX1 && mouseX <= this.openGLWarningX2 && mouseY >= this.openGLWarningY1 && mouseY <= this.openGLWarningY2)
            {
                GuiConfirmOpenLink guiconfirmopenlink = new GuiConfirmOpenLink(this, this.openGLWarningLink, 13, true);
                guiconfirmopenlink.disableSecurityWarning();
                this.mc.displayGuiScreen(guiconfirmopenlink);
            }
        }

        //net.minecraftforge.client.ForgeHooksClient.mainMenuMouseClick(mouseX, mouseY, mouseButton, this.fontRenderer, this.width);
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {

    }
}