package mrcomputerghost.testerino.commands;

import mrcomputerghost.testerino.api.BetterSchematic;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.FMLInjectionData;

import java.io.File;

public class CommandImport extends CommandBase {


    @Override
    public String getCommandName() {
        return "import";
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "/import <x> <y> <z> <name> <ignoreAir:true|false>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (args.length < 5) {
            try {
                throw new WrongUsageException("/import <x> <y> <z> <name> <ignoreAir:true|false>");
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
            BetterSchematic bs = BetterSchematic.genBetterSchematicFromFile(new File((File) FMLInjectionData.data()[6], "/Structures/" + args[3] + ".nbt"));
            bs.generateInWorld(sender.getEntityWorld(), new BlockPos(Integer.parseInt(args[0]), Integer.parseInt(args[1]), (Integer.parseInt(args[2]))), Boolean.parseBoolean(args[4]));
            sender.addChatMessage(new TextComponentString("Imported Structure"));
        }
    }
}
