package fr.sipaa.sipaamod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SipaaModCreativeTab extends CreativeTabs {

	public SipaaModCreativeTab() {
		super(SipaaMod.MODID);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(SipaaModItems.sodiumIngot);
	}
	
}