package mrcomputerghost.testerino;

import mrcomputerghost.testerino.blocks.TesterinoBlocks;
import mrcomputerghost.testerino.commands.CommandConvert;
import mrcomputerghost.testerino.commands.CommandExport;
import mrcomputerghost.testerino.commands.CommandImport;
import mrcomputerghost.testerino.json.StructureManager;
import mrcomputerghost.testerino.proxy.CommonProxy;
import mrcomputerghost.testerino.world.gen.StructureGenerator;
import mrcomputerghost.testerino.world.gen.structure.Structure;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = "Testerino", name = "Testerino", version = "1.0")
public class Testerino {

    @Mod.Instance
    public static Testerino instance;

    @SidedProxy(serverSide = "mrcomputerghost.testerino.proxy.CommonProxy", clientSide = "mrcomputerghost.testerino.proxy.ClientProxy")
    public static CommonProxy proxy;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        TesterinoBlocks.initBlocks();

        StructureManager.findStructureJSONs();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();

        for (Structure s : StructureManager.structures) {
            GameRegistry.registerWorldGenerator(new StructureGenerator(s), 15);
        }
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Mod.EventHandler
    public void serverStart(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandExport());
        event.registerServerCommand(new CommandImport());
        event.registerServerCommand(new CommandConvert());
    }

}
