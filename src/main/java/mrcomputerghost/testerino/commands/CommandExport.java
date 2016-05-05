package mrcomputerghost.testerino.commands;

import cpw.mods.fml.relauncher.FMLInjectionData;
import mrcomputerghost.testerino.api.BetterSchematic;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.gen.structure.StructureBoundingBox;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length < 6) {
            throw new WrongUsageException("/export <x1> <y1> <z1> <x2> <y2> <z2> <name>");
        } else {
            if (args[0].startsWith("~") && sender instanceof EntityPlayer)
                args[0] = String.valueOf((int) (((EntityPlayer) sender).posX + Integer.parseInt(args[0].replace("~", "0"))));
            if (args[1].startsWith("~") && sender instanceof EntityPlayer)
                args[1] = String.valueOf((int) (((EntityPlayer) sender).posY + Integer.parseInt(args[1].replace("~", "0"))));
            if (args[2].startsWith("~") && sender instanceof EntityPlayer)
                args[2] = String.valueOf((int) (((EntityPlayer) sender).posZ + Integer.parseInt(args[2].replace("~", "0"))));
            if (args[3].startsWith("~") && sender instanceof EntityPlayer)
                args[3] = String.valueOf((int) (((EntityPlayer) sender).posX + Integer.parseInt(args[3].replace("~", "0"))));
            if (args[4].startsWith("~") && sender instanceof EntityPlayer)
                args[4] = String.valueOf((int) (((EntityPlayer) sender).posY + Integer.parseInt(args[4].replace("~", "0"))));
            if (args[5].startsWith("~") && sender instanceof EntityPlayer)
                args[5] = String.valueOf((int) (((EntityPlayer) sender).posZ + Integer.parseInt(args[5].replace("~", "0"))));
            for (int n = 0; n < 2; n++) {
                if (Integer.parseInt(args[n]) > Integer.parseInt(args[n + 3])) {
                    String s = args[n];
                    args[n] = args[n + 3];
                    args[n + 3] = s;
                }
            }
            StructureBoundingBox box = new StructureBoundingBox(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]));
            BetterSchematic bs = BetterSchematic.genBetterSchematicFromWorld(sender.getEntityWorld(), box);
            String name;
            if (args.length > 6) {
                name = args[6];
            } else {
                File structFolder = new File((File) FMLInjectionData.data()[6], "/Structures/");
                int iterations = 0;
                for (File f : structFolder.listFiles()) {
                    if (f.getName().startsWith("Structure-")) {
                        iterations++;
                    }
                }
                name = "Structure-" + iterations;
            }
            if (bs.writeToFile(new File((File) FMLInjectionData.data()[6], "/Structures/" + name + ".nbt"))) {
                sender.addChatMessage(new ChatComponentText("Exported Structure to: " + name + ".nbt"));
            } else {
                sender.addChatMessage(new ChatComponentText("There was an error exporting the Structure"));
            }
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
        return l;
    }
}
