package HashMap;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Import {

    public static Player[] JsonL() {
        Gson gson = new Gson();
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader("data/HashMapL.json"));
            return gson.fromJson(reader, Player[].class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Player[] JsonM() {
        Gson gson = new Gson();
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader("data/HashMapM.json"));
            return gson.fromJson(reader, Player[].class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Player[] JsonS() {
        Gson gson = new Gson();
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader("data/HashMapS.json"));
            return gson.fromJson(reader, Player[].class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}