package model.jsonreader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import model.arbolnario.NodoNArio;
import model.sensor.Sensor;
import com.google.gson.*;

public class JSonReader implements JsonConstants {
	
	private Gson gson;
	private String filePath;
	
	public JSonReader() {
		gson = new GsonBuilder().setPrettyPrinting().create();
	}
	
	public JSonReader(String filePath) {
		gson = new GsonBuilder().setPrettyPrinting().create();
		this.filePath = filePath;
	}

	public Gson getGson() {
		return gson;
	}
	
	public void setGson(Gson gson) {
		this.gson = gson;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void writeToFile(Object object) throws JsonIOException, IOException {
		FileWriter writer = new FileWriter(filePath);
		gson.toJson(object, writer);
		writer.close();
	}
	
	public SensorSerializator readFromFile() {
		try (Reader reader = new FileReader(filePath)) {
	        	SensorSerializator sensor = getGson().fromJson(reader, SensorSerializator.class);
	        	return sensor;
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		return null;
	}
	
	public NodoNArio<Sensor> deserializer(SensorSerializator serialized) {
		NodoNArio<Sensor> sensor = new NodoNArio<Sensor>(new Sensor(serialized.getId(), serialized.getTipoUbicacion(), serialized.getNombreLugar(), serialized.getConsumoBase()));
		for (SensorSerializator s : serialized.getHijos()) {
			sensor.getHijos().add(deserializer(s));
		}
		return sensor;
	}
	
	public SensorSerializator serializer(NodoNArio<Sensor> nodo) {
		SensorSerializator sensor = new SensorSerializator(nodo.getValor().getId(), nodo.getValor().getLugarInt(), nodo.getValor().getLugar().getNombre(), nodo.getValor().getConsumoBase());
		for (NodoNArio<Sensor> n : nodo.getHijos()) {
			sensor.getHijos().add(serializer(n));
		}
		return sensor;
	}
	
	public static void main(String args[]) throws JsonIOException, IOException {
		
		JSonReader jsonReader = new JSonReader(JSON_FILES_PATH + "//test.json");
		

        System.out.println(jsonReader.readFromFile());
        
        NodoNArio<Sensor> nodo = jsonReader.deserializer(jsonReader.readFromFile());
        System.out.println(nodo.getHijos());
        SensorSerializator serie = jsonReader.serializer(nodo);
        System.out.println(serie.getTipoUbicacion());
	}
}
