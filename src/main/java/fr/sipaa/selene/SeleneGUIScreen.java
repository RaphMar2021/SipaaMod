package fr.sipaa.selene;

import java.util.List;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SeleneGUIScreen extends SeleneGUIElement {

	public SeleneScreen parent;
	public List<SeleneGUIElement> elements;
	
	public void init() {
		
	}
}
