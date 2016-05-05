package mrcomputerghost.testerino.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockFakeAir extends Block {

    public BlockFakeAir() {
        super(Material.glass);
        this.setBlockUnbreakable();
        this.setUnlocalizedName("fakeAir");
        this.setRegistryName("testerino", "fakeAir");
    }

}
