package fr.sipaa.selene;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SeleneGUIElement {
	/** Position **/
	public int posX = 3;
	public int posY = 3;
	
	/** Size **/
	public int width = 98;
	public int height = 20;
	
	/** Parent **/
	public SeleneGUIElement parent = null;
	
	/**IDs**/
	public int id = 0;
	
	/** Methods **/
	public SeleneGUIElement() { }
	public SeleneGUIElement(SeleneGUIElement parent) { this.parent = parent; }
	
	public void renderElement(Minecraft m, int x, int y) {
		
	}
	
	public void updateElement(Minecraft m, int x, int y) {
		
	}

	public void elementPressed(int id) {
		
	}
	
    protected boolean mouseHover(int mouseX, int mouseY, int x, int y, int width, int height)
    {
        return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }
}
