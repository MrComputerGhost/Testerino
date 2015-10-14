package mrcomputerghost.testerino;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import mrcomputerghost.testerino.commands.CommandExport;
import mrcomputerghost.testerino.commands.CommandImport;
import mrcomputerghost.testerino.proxy.CommonProxy;

@Mod(modid = "Testerino", name = "Testerino", version = "1.0.0")
public class Testerino {

    @Mod.Instance
    public static Testerino instance;

    @SidedProxy(serverSide = "mrcomputerghost.testerino.proxy.CommonProxy", clientSide = "mrcomputerghost.testerino.proxy.ClientProxy")
    public static CommonProxy proxy;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

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
