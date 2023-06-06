package fr.sipaa.sipaamod;

import fr.sipaa.sipaamod.item.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.item.Item.*;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraft.item.ItemBlock;

public class SipaaModItems {
	public static final CreativeTabs cTab = new SipaaModCreativeTab();
	
	/** Sodium **/
	public static final ToolMaterial sodiumToolMaterial = EnumHelper.addToolMaterial("sipaamod:sodium", 4, 3071, 18, 10, 10);
	public static final ArmorMaterial sodiumArmorMaterial = EnumHelper.addArmorMaterial("sipaamod:sodium_armor", "sipaamod:sodium", 3940, new int[] { 4,6,7,4 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0);
	
	public static final Item sodiumIngot = new Item().setRegistryName("sodiumIngot").setUnlocalizedName("sodiumIngot").setCreativeTab(cTab);
	public static final Item sodiumSword = new ItemSword(sodiumToolMaterial).setRegistryName("sodiumSword").setUnlocalizedName("sodiumSword").setCreativeTab(cTab);
	public static final Item sodiumShovel = new ItemSpade(sodiumToolMaterial).setRegistryName("sodiumShovel").setUnlocalizedName("sodiumShovel").setCreativeTab(cTab);
	public static final Item sodiumPickaxe = new SipaaModPickaxe(sodiumToolMaterial).setRegistryName("sodiumPickaxe").setUnlocalizedName("sodiumPickaxe").setCreativeTab(cTab);
	public static final Item sodiumAxe = new SipaaModAxe(sodiumToolMaterial).setRegistryName("sodiumAxe").setUnlocalizedName("sodiumAxe").setCreativeTab(cTab);
	
	public static final SipaaModArmor sodiumHelmet = (SipaaModArmor) new SipaaModArmor(sodiumArmorMaterial, EntityEquipmentSlot.HEAD).setRegistryName("sodiumHelmet").setUnlocalizedName("sodiumHelmet").setCreativeTab(cTab);
	public static final SipaaModArmor sodiumChestplate = (SipaaModArmor) new SipaaModArmor(sodiumArmorMaterial, EntityEquipmentSlot.CHEST).setRegistryName("sodiumChestplate").setUnlocalizedName("sodiumChestplate").setCreativeTab(cTab);
	public static final SipaaModArmor sodiumLeggings = (SipaaModArmor) new SipaaModArmor(sodiumArmorMaterial, EntityEquipmentSlot.LEGS).setRegistryName("sodiumLeggings").setUnlocalizedName("sodiumLeggings").setCreativeTab(cTab);
	public static final SipaaModArmor sodiumBoots = (SipaaModArmor) new SipaaModArmor(sodiumArmorMaterial, EntityEquipmentSlot.FEET).setRegistryName("sodiumBoots").setUnlocalizedName("sodiumBoots").setCreativeTab(cTab);
	
	public static final Block sodiumOre = new Block(Material.IRON).setRegistryName("sodiumOre").setCreativeTab(cTab).setUnlocalizedName("sodiumOre");
	public static final ItemBlock itemSodiumOre = (ItemBlock)new ItemBlock(sodiumOre).setRegistryName("sodiumOre").setCreativeTab(cTab);

	public static final Block sodiumBlock = new Block(Material.IRON).setRegistryName("sodiumBlock").setCreativeTab(cTab).setUnlocalizedName("sodiumBlock");
	public static final ItemBlock itemSodiumBlock = (ItemBlock)new ItemBlock(sodiumBlock).setRegistryName("sodiumBlock").setCreativeTab(cTab);
	 
}
