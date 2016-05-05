package mrcomputerghost.testerino.json;

import com.google.gson.*;
import mrcomputerghost.testerino.world.gen.structure.Structure;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class StructureJSON implements JsonDeserializer<Structure> {

    @Override
    public Structure deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject obj = json.getAsJsonObject();

        final JsonArray dims = obj.getAsJsonArray("dimensions");
        ArrayList<Integer> dimensions = new ArrayList<Integer>();
        for (JsonElement element : dims) {
            dimensions.add(element.getAsInt());
        }

        final String file = obj.get("structureName").getAsString();
        final boolean ignoreAir = obj.get("ignoreAir").getAsBoolean();

        if (obj.has("useExactCoords")) {
            if (obj.get("useExactCoords").getAsBoolean()) {
                final int x = obj.get("coordX").getAsInt();
                final int y = obj.get("coordY").getAsInt();
                final int z = obj.get("coordZ").getAsInt();

                return new Structure(file, x, y, z, dimensions, ignoreAir);
            }
        }

        final int minHeight = obj.get("minHeight").getAsInt();
        final int maxHeight = obj.get("maxHeight").getAsInt();
        final int rarity = obj.get("rarity").getAsInt();

        final JsonArray biomes = obj.getAsJsonArray("spawnBiomeIds");

        ArrayList<String> biomeList = new ArrayList<String>();
        for (JsonElement element : biomes) {
            biomeList.add(element.getAsString());
        }


        return new Structure(file, minHeight, maxHeight, rarity, biomeList, dimensions, ignoreAir);

    }

}
