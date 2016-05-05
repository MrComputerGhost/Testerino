package mrcomputerghost.testerino.blocks;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TesterinoBlocks {

    public static Block fakeAir;

    public static void initBlocks() {

        fakeAir = register(new BlockFakeAir());

    }

    private static Block register(Block block) {
        GameRegistry.registerBlock(block);
        return block;
    }

}
