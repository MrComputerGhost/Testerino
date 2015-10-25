package mrcomputerghost.testerino.json;

import com.google.gson.*;
import mrcomputerghost.testerino.world.gen.structure.Structure;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class StructureJSON implements JsonDeserializer<Structure> {

    @Override
    public Structure deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject obj = json.getAsJsonObject();

        final int minHeight = obj.get("minHeight").getAsInt();
        final int maxHeight = obj.get("maxHeight").getAsInt();
        final int rarity = obj.get("rarity").getAsInt();

        final JsonArray dims = obj.getAsJsonArray("dimensions");
        final JsonArray biomes = obj.getAsJsonArray("spawnBiomeIds");

        ArrayList<Integer> biomeList = new ArrayList<Integer>();
        for (JsonElement element : biomes) {
            biomeList.add(element.getAsInt());
        }
        ArrayList<Integer> dimensions = new ArrayList<Integer>();
        for (JsonElement element : dims) {
            dimensions.add(element.getAsInt());
        }

        final String file = obj.get("structureName").getAsString();
        final boolean ignoreAir = obj.get("ignoreAir").getAsBoolean();

        return new Structure(file, minHeight, maxHeight, rarity, biomeList, dimensions, ignoreAir);
    }

}
