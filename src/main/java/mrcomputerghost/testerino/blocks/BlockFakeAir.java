package mrcomputerghost.testerino.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialTransparent;

public class BlockFakeAir extends Block {

    public BlockFakeAir() {
        super(Material.glass);
        setBlockName("fakeAir");
        setBlockTextureName("testerino:fake_air");
        setBlockUnbreakable();
        TesterinoBlocks.blocks.add(this);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

}
