package model.jsonReader;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonParser implements JsonConstants{
	
	private static JsonParser instance;
	private Structure data;
	private Gson gson;
	
	private JsonParser() {
		gson = new GsonBuilder().setPrettyPrinting().create();
		data = readFromFile();
	}
	
	public static JsonParser get() {
		if (instance == null) {
			instance = new JsonParser();
		}
		return instance;
	}

	public Structure getData() {
		return data;
	}

	public void setData(Structure data) {
		this.data = data;
	}
	
	public Structure readFromFile() {
		try (Reader reader = new FileReader(JSON_FILES_PATH + "config.json")) {
        	Structure contenido = gson.fromJson(reader, Structure.class);
        	return contenido;
        } catch (IOException e) {
            e.printStackTrace();
        }
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(JsonParser.get().getData().getAncho());
	}
	
}
