package mrcomputerghost.testerino.api;

import com.google.common.primitives.Ints;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.common.ChestGenHooks;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BetterSchematic {

    public int height, length, width;
    public int[] blocks, meta;

    public NBTTagCompound key;
    public NBTTagList tiles;

    public BetterSchematic(int height, int length, int width, int[] blocks, int[] meta, NBTTagCompound key) {
        this(height, length, width, blocks, meta, key, new NBTTagList());
    }

    public BetterSchematic(int height, int length, int width, int[] blocks, int[] meta, NBTTagCompound key, NBTTagList tiles) {
        this.height = height;
        this.length = length;
        this.width = width;
        this.blocks = blocks;
        this.meta = meta;
        this.key = key;
        this.tiles = tiles;
    }

    public static BetterSchematic genBetterSchematicFromWorld(World world, StructureBoundingBox box) {
        List<String> nameID = new ArrayList<String>();
        nameID.add("minecraft:air");
        List<Integer> blocks = new ArrayList<Integer>();
        List<Integer> metas = new ArrayList<Integer>();
        List<ChunkCoordinates> tile = new ArrayList<ChunkCoordinates>();
        for (int z = box.minZ; z <= box.maxZ; ++z) {
            for (int y = box.minY; y <= box.maxY; ++y) {
                for (int x = box.minX; x <= box.maxX; ++x) {
                    metas.add(world.getBlockMetadata(x, y, z));
                    String s = GameRegistry.findUniqueIdentifierFor(world.getBlock(x, y, z)).toString();
                    if (!nameID.contains(s)) {
                        nameID.add(s);
                    }
                    blocks.add(nameID.indexOf(s));
                    if (world.getTileEntity(x, y, z) != null) {
                        tile.add(new ChunkCoordinates(x, y, z));
                    }
                }
            }
        }
        NBTTagCompound key = new NBTTagCompound();
        for (String s : nameID) {
            key.setString(String.valueOf(nameID.indexOf(s)), s);
        }
        NBTTagList tiles = new NBTTagList();
        for (ChunkCoordinates c : tile) {
            TileEntity te = world.getTileEntity(c.posX, c.posY, c.posZ);
            NBTTagCompound b = new NBTTagCompound();
            te.writeToNBT(b);
            b.setInteger("x", c.posX - box.minX);
            b.setInteger("y", c.posY - box.minY);
            b.setInteger("z", c.posZ - box.minZ);
            tiles.appendTag(b);
        }
        return new BetterSchematic(Math.abs(box.maxY - box.minY), Math.abs(box.maxZ - box.minZ), Math.abs(box.maxX - box.minX), Ints.toArray(blocks), Ints.toArray(metas), key, tiles);
    }

    public static BetterSchematic genBetterSchematicFromFile(File file) {
        try {
            FileInputStream inputStream = new FileInputStream(file);
            NBTTagCompound schematic = CompressedStreamTools.readCompressed(inputStream);
            int height = schematic.getShort("Height");
            int length = schematic.getShort("Length");
            int width = schematic.getShort("Width");
            int[] blocks = schematic.getIntArray("Blocks");
            int[] meta = schematic.getIntArray("Data");
            NBTTagCompound key = schematic.getCompoundTag("Key");
            NBTTagList tiles = schematic.getTagList("Tiles", 10);

            return new BetterSchematic(height, length, width, blocks, meta, key, tiles);
        } catch (IOException e) {
            e.printStackTrace();
            return new BetterSchematic(0, 0, 0, new int[]{}, new int[]{}, null);
        }
    }

    public boolean writeToFile(File file) {
        try {
            NBTTagCompound schem = new NBTTagCompound();
            schem.setInteger("Height", height);
            schem.setInteger("Length", length);
            schem.setInteger("Width", width);
            schem.setIntArray("Blocks", blocks);
            schem.setIntArray("Data", meta);
            schem.setTag("Key", key);
            schem.setTag("Tiles", tiles);
            byte[] temp = CompressedStreamTools.compress(schem);
            FileUtils.writeByteArrayToFile(file, temp);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void generateInWorld(World world, ChunkCoordinates coords, boolean ignoreAir) {
        int i = 0;
        for (int z = coords.posZ; z <= length + coords.posZ; ++z) {
            for (int y = coords.posY; y <= height + coords.posY; ++y) {
                for (int x = coords.posX; x <= width + coords.posX; ++x) {
                    String[] s = key.getString(String.valueOf(blocks[i])).split(":");
                    if (s[1].toLowerCase().endsWith("air") && ignoreAir) {
                        if (s[1].toLowerCase().equals("fakeair")) {
                            world.setBlockToAir(x, y, z);
                        }
                    } else {
                        world.setBlock(x, y, z, GameRegistry.findBlock(s[0], s[1]), meta[i], 2);
                    }
                    ++i;
                }
            }
        }

        for (int j = 0; j < tiles.tagCount(); ++j) {
            NBTTagCompound t = tiles.getCompoundTagAt(j);
            t.setInteger("x", coords.posX + t.getInteger("x"));
            t.setInteger("y", coords.posY + t.getInteger("y"));
            t.setInteger("z", coords.posZ + t.getInteger("z"));
            TileEntity te = world.getTileEntity(t.getInteger("x"), t.getInteger("y"), t.getInteger("z"));
            if (te != null) {
                te.readFromNBT(t);
                if (t.hasKey("dungeonLoot")) {
                    Random rand = new Random();
                    WeightedRandomChestContent.generateChestContents(rand, ChestGenHooks.getItems(t.getString("dungeonLoot"), rand), (IInventory) te, ChestGenHooks.getCount(t.getString("dungeonLoot"), rand));
                }
            }
        }
    }

}
