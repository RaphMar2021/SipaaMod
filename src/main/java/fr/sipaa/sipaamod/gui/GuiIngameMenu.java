package fr.sipaa.sipaamod.gui;

import java.io.IOException;

import fr.sipaa.selene.SeleneScreen;
import fr.sipaa.selene.render.Color;
import fr.sipaa.selene.render.Renderer;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.gui.advancements.GuiScreenAdvancements;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.world.GameType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiIngameMenu extends GuiScreen
{
    private int saveStep;
    private int visibleTime;
    private int animationButtonsX = -128;
    
    private GuiButton returnToGameButton;
    private GuiButton optionsButton;
    private GuiButton shareToLanButton;
    private GuiButton advancementsBtn;
    private GuiButton statsBtn;
    private GuiButton disconnectBtn;
    
    
    public void initGui()
    {	
        this.saveStep = 0;
        this.buttonList.clear();
        int i = -16;
        int j = 98;
        disconnectBtn = this.addButton(new GuiButton(1, animationButtonsX, this.height / 4 + 120 + -16, 98, 20, I18n.format("menu.returnToMenu")));

        if (!this.mc.isIntegratedServerRunning())
        {
            (this.buttonList.get(0)).displayString = I18n.format("menu.disconnect");
        }
        returnToGameButton = this.addButton(new GuiButton(4, animationButtonsX, this.height / 4 + 24 + -16, 98, 20, I18n.format("menu.returnToGame")));
        optionsButton = this.addButton(new GuiButton(0, animationButtonsX, this.height / 4 + 96 + -16, 98, 20, I18n.format("menu.options")));
        shareToLanButton = this.addButton(new GuiButton(7, animationButtonsX, this.height / 4 + 72 + -16, 98, 20, I18n.format("menu.shareToLan", new Object[0])));
        shareToLanButton.enabled = this.mc.isSingleplayer() && !this.mc.getIntegratedServer().getPublic();
        advancementsBtn = this.addButton(new GuiButton(5, animationButtonsX, this.height / 4 + 48 + -16, 98, 20, I18n.format("gui.advancements")));
        statsBtn = this.addButton(new GuiButton(6, animationButtonsX, this.height / 4 + 48 + -16, 98, 20, I18n.format("gui.stats")));
    }

    protected void actionPerformed(GuiButton button) throws IOException
    {
        switch (button.id)
        {
            case 0:
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;
            case 1:
                boolean flag = this.mc.isIntegratedServerRunning();
                boolean flag1 = this.mc.isConnectedToRealms();
                button.enabled = false;
                this.mc.world.sendQuittingDisconnectingPacket();
                this.mc.loadWorld((WorldClient)null);

                if (flag)
                {
                    this.mc.displayGuiScreen(new GuiMainMenu());
                }
                else if (flag1)
                {
                    RealmsBridge realmsbridge = new RealmsBridge();
                    realmsbridge.switchToRealms(new GuiMainMenu());
                }
                else
                {
                    this.mc.displayGuiScreen(new GuiMultiplayer(new net.minecraft.client.gui.GuiMainMenu()));
                }

            case 2:
            case 3:
            default:
                break;
            case 4:
                this.mc.displayGuiScreen((GuiScreen)null);
                this.mc.setIngameFocus();
                break;
            case 5:
                if (this.mc.player != null)
                this.mc.displayGuiScreen(new GuiScreenAdvancements(this.mc.player.connection.getAdvancementManager()));
                break;
            case 6:
                if (this.mc.player != null)
                this.mc.displayGuiScreen(new GuiStats(this, this.mc.player.getStatFileWriter()));
                break;
            case 7:
        		mc.displayGuiScreen(new SeleneScreen(new SeleneGUIWindow())); //this.mc.displayGuiScreen(new GuiShareToLan(this));
                break;
            case 12:
                break;
        }
    }

    public void updateScreen()
    {
        super.updateScreen();
        ++this.visibleTime;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
    	if (animationButtonsX != 10)
    	{
    		animationButtonsX++;
    		disconnectBtn.x = animationButtonsX;
    		returnToGameButton.x = animationButtonsX;
    		shareToLanButton.x = animationButtonsX;
    		optionsButton.x = animationButtonsX;
    		statsBtn.x = animationButtonsX;
    		advancementsBtn.x = animationButtonsX;
    		
    	}
        Renderer.drawRect(0, 0, 118, this.height, new Color(0.5f, 0, 0, 0));
        Renderer.drawVerticalLine(118, -1, this.height + 1, new Color(1f, 0, 0.5f, 1f));
        this.drawCenteredString(fontRenderer, I18n.format("menu.game"), 118 / 2, 40, 16777215);
        //this.drawCenteredString(this.fontRenderer, I18n.format("menu.game"), this.width / 2, 40, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
