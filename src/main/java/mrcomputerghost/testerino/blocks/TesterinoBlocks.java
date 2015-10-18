package mrcomputerghost.testerino.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

import java.util.ArrayList;

public class TesterinoBlocks {

    public static ArrayList<Block> blocks = new ArrayList<Block>();

    public static Block fakeAir;

    public static void initBlocks() {

        fakeAir = new BlockFakeAir();

        for (Block block : blocks) {
            GameRegistry.registerBlock(block, block.getUnlocalizedName());
        }

    }

}
