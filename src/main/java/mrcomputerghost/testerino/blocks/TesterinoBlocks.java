package mrcomputerghost.testerino.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TesterinoBlocks {

    public static Block fakeAir;

    public static void initBlocks() {

        fakeAir = register(new BlockFakeAir());
    }

    private static Block register(Block block) {
        GameRegistry.register(block);
        GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
        return block;
    }

}
