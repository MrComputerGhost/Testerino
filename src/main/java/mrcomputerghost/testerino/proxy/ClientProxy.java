package mrcomputerghost.testerino.proxy;

import mrcomputerghost.testerino.blocks.TesterinoBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class ClientProxy extends CommonProxy {

    @Override
    public void init() {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(TesterinoBlocks.fakeAir), 0, new ModelResourceLocation("assets.testerino:" + TesterinoBlocks.fakeAir.getUnlocalizedName().substring(5), "inventory"));
    }

}
