package fr.sipaa.sipaamod.world;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import fr.sipaa.sipaamod.*;

public class OreGenerator implements IWorldGenerator 
{
	@Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) 
    {
        if (world.provider.getDimension() == 0) 
        {
            generateOverworld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
        }
    }
    
    private void generateOverworld(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        generateOreOverworld(SipaaModItems.sodiumOre.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 0, 15, random.nextInt(8) + 1, 45);
    }
    
    private void generateOreOverworld(IBlockState ore, World world, Random random, int x, int z, int minY, int maxY, int size, int chances) 
    {
        int deltaY = maxY - minY;
        
        for (int i = 0; i < chances; i++) 
        {
            BlockPos pos = new BlockPos(x + random.nextInt(16), minY + random.nextInt(deltaY), z + random.nextInt(16));
            
            WorldGenMinable generator = new WorldGenMinable(ore, size);
            generator.generate(world, random, pos);
        }
    }
}
