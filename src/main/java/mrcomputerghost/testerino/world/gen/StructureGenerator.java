package mrcomputerghost.testerino.world.gen;

import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.relauncher.FMLInjectionData;
import mrcomputerghost.testerino.api.BetterSchematic;
import mrcomputerghost.testerino.world.gen.structure.Structure;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import java.io.File;
import java.util.Random;

public class StructureGenerator implements IWorldGenerator {

    private Structure structure;

    public StructureGenerator(Structure structure) {
        this.structure = structure;
    }

    @Override
    public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        if (structure.safeBiomeIds.contains(world.getBiomeGenForCoords(chunkX * 16, chunkZ * 16).biomeID)) {
            int x = chunkX * 16;
            int z = chunkZ * 16;
            if (structure.dimensions.contains(world.provider.dimensionId)) {
                if (rand.nextInt(structure.rarity) == 0) {
                    int y = structure.minLevel + rand.nextInt(structure.maxLevel - structure.minLevel);
                    BetterSchematic struct = BetterSchematic.genBetterSchematicFromFile(new File((File) FMLInjectionData.data()[6], "/Structures/" + structure.structureName + ".nbt"));
                    struct.generateInWorld(world, new ChunkCoordinates(x, y, z), structure.ignoreAir);
                }
            }
        }
    }

}
