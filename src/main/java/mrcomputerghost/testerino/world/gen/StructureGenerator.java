package mrcomputerghost.testerino.world.gen;

import mrcomputerghost.testerino.api.BetterSchematic;
import mrcomputerghost.testerino.world.gen.structure.Structure;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.relauncher.FMLInjectionData;

import java.io.File;
import java.util.Random;

public class StructureGenerator implements IWorldGenerator {

    private Structure structure;

    public StructureGenerator(Structure structure) {
        this.structure = structure;
    }

    @Override
    public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        int x = chunkX * 16;
        int z = chunkZ * 16;
        if (structure.dimensions.contains(world.provider.getDimensionId())) {
            if (structure.useExactCoords) {
                if (Math.abs(structure.coordX - x) < 16 && Math.abs(structure.coordZ - z) < 16) {
                    BetterSchematic struct = BetterSchematic.genBetterSchematicFromFile(new File((File) FMLInjectionData.data()[6], "/Structures/" + structure.structureName + ".nbt"));
                    struct.generateInWorld(world, new BlockPos(structure.coordX, structure.coordY, structure.coordZ), structure.ignoreAir);
                }
            } else {
                if (structure.safeBiomeIds.contains(world.getBiomeGenForCoords(new BlockPos(chunkX * 16, 0, chunkZ * 16)).biomeID)) {
                    if (rand.nextInt(structure.rarity) == 0) {
                        int y = structure.minLevel + rand.nextInt(structure.maxLevel - structure.minLevel);
                        BetterSchematic struct = BetterSchematic.genBetterSchematicFromFile(new File((File) FMLInjectionData.data()[6], "/Structures/" + structure.structureName + ".nbt"));
                        struct.generateInWorld(world, new BlockPos(x, y, z), structure.ignoreAir);
                    }
                }
            }
        }
    }
}