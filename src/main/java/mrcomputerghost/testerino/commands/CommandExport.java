package mrcomputerghost.testerino.commands;

import cpw.mods.fml.relauncher.FMLInjectionData;
import mrcomputerghost.testerino.api.BetterSchematic;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.gen.structure.StructureBoundingBox;

import java.io.File;

public class CommandExport extends CommandBase {


    @Override
    public String getCommandName() {
        return "export";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "/export <x1> <y1> <z1> <x2> <y2> <z2> <name>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length < 7) {
            throw new WrongUsageException("/export <x1> <y1> <z1> <x2> <y2> <z2> <name>");
        } else {
            StructureBoundingBox box = new StructureBoundingBox(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]));
            BetterSchematic bs = BetterSchematic.genBetterSchematicFromWorld(sender.getEntityWorld(), box);
            if (bs.writeToFile(new File((File) FMLInjectionData.data()[6], "/Structures/" + args[6] + ".nbt"))) {
                sender.addChatMessage(new ChatComponentText("Exported Structure to: " + args[6] + ".nbt"));
            } else {
                sender.addChatMessage(new ChatComponentText("There was an error exporting the Structure"));
            }
        }
    }
}
