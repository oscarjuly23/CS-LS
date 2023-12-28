package ArbreCoords;

import Model.Position;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Import {

    public static Position[] Json() {
        Gson gson = new Gson();
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader("data/dataset_MapS.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Position[] positions = gson.fromJson(reader, Position[].class);
        return positions;
    }
}