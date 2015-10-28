package mrcomputerghost.testerino.world.gen.structure;

import java.util.ArrayList;

public class Structure {

    public int minLevel, maxLevel, rarity;

    public String structureName;
    public ArrayList<Integer> safeBiomeIds, dimensions;
    public boolean ignoreAir, useExactCoords;

    public int coordX, coordY, coordZ;

    public Structure(String structureName, int minLevel, int maxLevel, int rarity, ArrayList<Integer> safeBiomeIds, ArrayList<Integer> dimensions, boolean ignoreAir) {
        this.structureName = structureName;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.rarity = rarity;
        this.safeBiomeIds = safeBiomeIds;
        this.ignoreAir = ignoreAir;
        this.dimensions = dimensions;
        this.useExactCoords = false;
    }

    public Structure(String structureName, int x, int y, int z, ArrayList<Integer> dimensions, boolean ignoreAir) {
        this.structureName = structureName;
        this.coordX = x;
        this.coordY = y;
        this.coordZ = z;
        this.dimensions = dimensions;
        this.ignoreAir = ignoreAir;
        this.useExactCoords = true;
    }

}
