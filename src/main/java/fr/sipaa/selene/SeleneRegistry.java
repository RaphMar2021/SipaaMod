package fr.sipaa.selene;

import java.util.ArrayList;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SeleneRegistry {
	public static ArrayList<SeleneOverlay> overlays = new ArrayList<SeleneOverlay>();
	
	public static void registerOverlay(SeleneOverlay o)
	{
		overlays.add(o);
	}
}
