package mrcomputerghost.testerino.commands;

import cpw.mods.fml.relauncher.FMLInjectionData;
import mrcomputerghost.testerino.api.BetterSchematic;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;

import java.io.File;

public class CommandImport extends CommandBase {


    @Override
    public String getCommandName() {
        return "import";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "/import <x> <y> <z> <name> <ignoreAir:true|false>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length < 5) {
            throw new WrongUsageException("/import <x> <y> <z> <name> <ignoreAir:true|false>");
        } else {
            BetterSchematic bs = BetterSchematic.genBetterSchematicFromFile(new File((File) FMLInjectionData.data()[6], "/Structures/" + args[3] + ".nbt"));
            bs.generateInWorld(sender.getEntityWorld(), new ChunkCoordinates(Integer.parseInt(args[0]), Integer.parseInt(args[1]), (Integer.parseInt(args[2]))), Boolean.parseBoolean(args[4]));
            sender.addChatMessage(new ChatComponentText("Imported Structure"));
        }
    }
}
