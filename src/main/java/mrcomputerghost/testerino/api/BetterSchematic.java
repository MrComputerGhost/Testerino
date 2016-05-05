package mrcomputerghost.testerino.api;

import com.google.common.primitives.Ints;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPOutputStream;

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
        List<BlockPos> tile = new ArrayList<BlockPos>();
        for (int z = box.minZ; z <= box.maxZ; ++z) {
            for (int y = box.minY; y <= box.maxY; ++y) {
                for (int x = box.minX; x <= box.maxX; ++x) {
                    BlockPos pos = new BlockPos(x, y, z);
                    metas.add(world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos)));
                    String s = ForgeRegistries.BLOCKS.getKey(world.getBlockState(pos).getBlock()).toString();
                    if (!nameID.contains(s)) {
                        nameID.add(s);
                    }
                    blocks.add(nameID.indexOf(s));
                    if (world.getTileEntity(pos) != null) {
                        tile.add(new BlockPos(x, y, z));
                    }
                }
            }
        }
        NBTTagCompound key = new NBTTagCompound();
        for (String s : nameID) {
            key.setString(String.valueOf(nameID.indexOf(s)), s);
        }
        NBTTagList tiles = new NBTTagList();
        for (BlockPos c : tile) {
            TileEntity te = world.getTileEntity(new BlockPos(c.getX(), c.getY(), c.getZ()));
            NBTTagCompound b = new NBTTagCompound();
            te.writeToNBT(b);
            b.setInteger("x", c.getX() - box.minX);
            b.setInteger("y", c.getY() - box.minY);
            b.setInteger("z", c.getZ() - box.minZ);
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
            byte[] temp = compress(schem);
            FileUtils.writeByteArrayToFile(file, temp);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private byte[] compress(NBTTagCompound tag) throws IOException {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        DataOutputStream dataoutputstream = new DataOutputStream(new GZIPOutputStream(bytearrayoutputstream));
        try {
            CompressedStreamTools.write(tag, dataoutputstream);
        } finally {
            dataoutputstream.close();
        }
        return bytearrayoutputstream.toByteArray();
    }

    public void generateInWorld(World world, BlockPos coords, boolean ignoreAir) {
        int i = 0;
        for (int z = coords.getZ(); z <= length + coords.getZ(); ++z) {
            for (int y = coords.getY(); y <= height + coords.getY(); ++y) {
                for (int x = coords.getX(); x <= width + coords.getX(); ++x) {
                    String[] s = key.getString(String.valueOf(blocks[i])).split(":");
                    BlockPos pos = new BlockPos(x, y, z);
                    if (s[1].toLowerCase().endsWith("air") && ignoreAir) {
                        if (s[1].toLowerCase().equals("fakeair")) {
                            world.setBlockToAir(pos);
                        }
                    } else {
                        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(s[0], s[1]));
                        IBlockState state = block.getStateFromMeta(meta[i]);
                        world.setBlockState(pos, state);
                    }
                    ++i;
                }
            }
        }

        for (int j = 0; j < tiles.tagCount(); ++j) {
            NBTTagCompound t = tiles.getCompoundTagAt(j);
            t.setInteger("x", coords.getX() + t.getInteger("x"));
            t.setInteger("y", coords.getY() + t.getInteger("y"));
            t.setInteger("z", coords.getZ() + t.getInteger("z"));
            TileEntity te = world.getTileEntity(new BlockPos(t.getInteger("x"), t.getInteger("y"), t.getInteger("z")));
            if (te != null) {
                te.readFromNBT(t);
            }
        }
    }

}
