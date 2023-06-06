package fr.sipaa.sipaamod.proxy;

import fr.sipaa.sipaamod.SipaaModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommonProxy {
      protected Logger logger;
      
      public CommonProxy()
      {
    	 logger = LogManager.getLogger("Proxy");
      }
      
	  public void preInit()
	  {
		  logger.info("Registring items");
		  ForgeRegistries.ITEMS.register(SipaaModItems.sodiumIngot);
		  ForgeRegistries.ITEMS.register(SipaaModItems.sodiumSword);
		  ForgeRegistries.ITEMS.register(SipaaModItems.sodiumAxe);
		  ForgeRegistries.ITEMS.register(SipaaModItems.sodiumPickaxe);
		  ForgeRegistries.ITEMS.register(SipaaModItems.sodiumShovel);
		  ForgeRegistries.ITEMS.register(SipaaModItems.sodiumHelmet);
		  ForgeRegistries.ITEMS.register(SipaaModItems.sodiumChestplate);
		  ForgeRegistries.ITEMS.register(SipaaModItems.sodiumLeggings);
		  ForgeRegistries.ITEMS.register(SipaaModItems.sodiumBoots);
		  ForgeRegistries.ITEMS.register(SipaaModItems.itemSodiumOre);
		  ForgeRegistries.ITEMS.register(SipaaModItems.itemSodiumBlock);
		  
		  logger.info("Registring blocks");
		  ForgeRegistries.BLOCKS.register(SipaaModItems.sodiumOre);
		  ForgeRegistries.BLOCKS.register(SipaaModItems.sodiumBlock);
		  
		  logger.info("Registring smelting recipes");
		  GameRegistry.addSmelting(SipaaModItems.itemSodiumOre, new ItemStack(SipaaModItems.sodiumIngot), 10.0F);
	  }

	  public void init()
	  {
		  
	  }

	  public void postInit()
	  {
		  ForgeRegistries.ITEMS.register(SipaaModItems.sodiumIngot);
		  
	  }
}
