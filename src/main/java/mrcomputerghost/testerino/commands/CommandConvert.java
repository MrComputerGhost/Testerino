package mrcomputerghost.testerino.commands;

import com.google.common.primitives.Ints;
import mrcomputerghost.testerino.api.BetterSchematic;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.relauncher.FMLInjectionData;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommandConvert extends CommandBase {

    @Override
    public String getCommandName() {
        return "convert";
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "convert <Input Schematic File> <Export Better Schematic File>\nTo access your minecraft directory, use '$mcDir/'";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        BetterSchematic bs = null;
        File input = new File(args[0].replace("$mcDir", ((File) FMLInjectionData.data()[6]).getAbsolutePath()));
        if (!input.exists()) {
            try {
                throw new WrongUsageException("Make sure the file you are looking for exists!");
            } catch (WrongUsageException e) {
                e.printStackTrace();
            }
        } else {
            try {
                NBTTagCompound schematic = CompressedStreamTools.readCompressed(new FileInputStream(input));
                int height = schematic.getShort("Height");
                int length = schematic.getShort("Length");
                int width = schematic.getShort("Width");
                byte[] blocks = schematic.getByteArray("Blocks");
                byte[] data = schematic.getByteArray("Data");
                NBTTagList tiles = schematic.getTagList("TileEntities", 10);
                List<String> nameID = new ArrayList<String>();
                List<Integer> newBlocks = new ArrayList<Integer>();
                for (byte block : blocks) {
                    Block b = Block.getBlockById((int) block);
                    String s = GameData.getBlockRegistry().getNameForObject(b).toString();
                    if (!nameID.contains(s)) {
                        nameID.add(s);
                    }
                    newBlocks.add(nameID.indexOf(s));
                }
                NBTTagCompound key = new NBTTagCompound();
                for (String s : nameID) {
                    key.setString(String.valueOf(nameID.indexOf(s)), s);
                }
                List<Integer> meta = new ArrayList<Integer>();
                for (byte b : data) {
                    meta.add((int) b);
                }
                bs = new BetterSchematic(height, length, width, Ints.toArray(newBlocks), Ints.toArray(meta), key, tiles);
                bs.writeToFile(new File(args[1].replace("$mcDir", ((File) FMLInjectionData.data()[6]).getAbsolutePath())));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            sender.addChatMessage(new ChatComponentText("Converted Schematic to Better Schematic"));
        }
    }

}
