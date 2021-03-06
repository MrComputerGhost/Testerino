package mrcomputerghost.testerino.commands;

import mrcomputerghost.testerino.api.BetterSchematic;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.fml.relauncher.FMLInjectionData;

import java.io.File;

public class CommandExport extends CommandBase {


    @Override
    public String getCommandName() {
        return "export";
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "/export <x1> <y1> <z1> <x2> <y2> <z2> <name>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (args.length < 7) {
            try {
                throw new WrongUsageException("/export <x1> <y1> <z1> <x2> <y2> <z2> <name>");
            } catch (WrongUsageException e) {
                e.printStackTrace();
            }
        } else if (Integer.parseInt(args[0]) > Integer.parseInt(args[3]) || Integer.parseInt(args[1]) > Integer.parseInt(args[4]) || Integer.parseInt(args[2]) > Integer.parseInt(args[5])) {
            try {
                throw new WrongUsageException("The second set of coordinates must be greater than or equal to the original set!");
            } catch (WrongUsageException e) {
                e.printStackTrace();
            }
        } else {
            if (args[0].startsWith("~") && sender instanceof EntityPlayer) {
                args[0] = String.valueOf((int) (((EntityPlayer) sender).posX + Integer.parseInt(args[0].replace("~", "0"))));
            }
            if (args[1].startsWith("~") && sender instanceof EntityPlayer) {
                args[1] = String.valueOf((int) (((EntityPlayer) sender).posY + Integer.parseInt(args[1].replace("~", "0"))));
            }
            if (args[2].startsWith("~") && sender instanceof EntityPlayer) {
                args[2] = String.valueOf((int) (((EntityPlayer) sender).posZ + Integer.parseInt(args[2].replace("~", "0"))));
            }
            if (args[3].startsWith("~") && sender instanceof EntityPlayer) {
                args[3] = String.valueOf((int) (((EntityPlayer) sender).posX + Integer.parseInt(args[3].replace("~", "0"))));
            }
            if (args[4].startsWith("~") && sender instanceof EntityPlayer) {
                args[4] = String.valueOf((int) (((EntityPlayer) sender).posY + Integer.parseInt(args[4].replace("~", "0"))));
            }
            if (args[5].startsWith("~") && sender instanceof EntityPlayer) {
                args[5] = String.valueOf((int) (((EntityPlayer) sender).posZ + Integer.parseInt(args[5].replace("~", "0"))));
            }
            StructureBoundingBox box = new StructureBoundingBox(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]));
            BetterSchematic bs = BetterSchematic.genBetterSchematicFromWorld(sender.getEntityWorld(), box);
            if (bs.writeToFile(new File((File) FMLInjectionData.data()[6], "/Structures/" + args[6] + ".nbt"))) {
                sender.addChatMessage(new TextComponentString("Exported Structure to: " + args[6] + ".nbt"));
            } else {
                sender.addChatMessage(new TextComponentString("There was an error exporting the Structure"));
            }
        }
    }
}
