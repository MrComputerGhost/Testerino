package mrcomputerghost.testerino.commands;

import cpw.mods.fml.relauncher.FMLInjectionData;
import mrcomputerghost.testerino.api.BetterSchematic;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length < 4) {
            throw new WrongUsageException("/import <x> <y> <z> <name> <ignoreAir:true|false>");
        } else {
            boolean flag = false;
            if (args.length > 4) {
                flag = Boolean.parseBoolean(args[4]);
            }
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
            bs.generateInWorld(sender.getEntityWorld(), new ChunkCoordinates(Integer.parseInt(args[0]), Integer.parseInt(args[1]), (Integer.parseInt(args[2]))), flag);
            sender.addChatMessage(new ChatComponentText("Imported Structure"));
        }
    }

    public List addTabCompletionOptions(ICommandSender sender, String[] list) {
        List<String> l = new ArrayList<String>();
        if (sender instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer) sender;
            l.add(String.valueOf((int) Math.floor(p.posX)));
            l.add(String.valueOf((int) Math.floor(p.posY)));
            l.add(String.valueOf((int) Math.floor(p.posZ)));
        }
        File structFolder = new File((File) FMLInjectionData.data()[6], "/Structures/");
        for (File f : structFolder.listFiles()) {
            l.add(f.getName().replace(".nbt", ""));
        }
        return l;
    }
}
