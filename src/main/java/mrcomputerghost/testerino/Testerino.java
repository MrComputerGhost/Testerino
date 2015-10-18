package mrcomputerghost.testerino;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import mrcomputerghost.testerino.blocks.TesterinoBlocks;
import mrcomputerghost.testerino.commands.CommandExport;
import mrcomputerghost.testerino.commands.CommandImport;
import mrcomputerghost.testerino.json.StructureManager;
import mrcomputerghost.testerino.proxy.CommonProxy;
import mrcomputerghost.testerino.world.gen.StructureGenerator;
import mrcomputerghost.testerino.world.structure.Structure;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = "Testerino", name = "Testerino", version = "0.02-beta")
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
    }

}
