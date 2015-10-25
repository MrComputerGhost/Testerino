package mrcomputerghost.testerino.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cpw.mods.fml.relauncher.FMLInjectionData;
import mrcomputerghost.testerino.world.gen.structure.Structure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class StructureManager {

    public static ArrayList<Structure> structures = new ArrayList<Structure>();

    public static void findStructureJSONs() {
        File structureFolder = new File((File) FMLInjectionData.data()[6], "StructureGen/");
        if (!structureFolder.exists()) {
            structureFolder.mkdir();
        }
        if (structureFolder.listFiles() != null) {
            for (File f : structureFolder.listFiles()) {
                if (f != null && f.getName().endsWith(".json")) {
                    GsonBuilder builder = new GsonBuilder();
                    builder.registerTypeAdapter(Structure.class, new StructureJSON());
                    Gson g = builder.create();
                    try {
                        Structure[] s = g.fromJson(new FileReader(f), Structure[].class);
                        for (Structure b : s) {
                            structures.add(b);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
