package model.jsonreader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

import model.arbolnario.NodoNArio;
import model.sensor.Sensor;

public class JSonReader implements JsonConstants {
	
	private Gson gson;
	private String filePath;
	private SensorSerializator fileContent;
	private NodoNArio<Sensor> nodo;
	
	public JSonReader() {
		gson = new GsonBuilder().setPrettyPrinting().create();
	}
	
	public JSonReader(String filePath) {
		this();
		this.filePath = JSON_FILES_PATH + "\\" + filePath;
		fileContent = readFromFile();
		if (fileContent != null) {
			nodo = deserializer(fileContent);
		}
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

	public SensorSerializator getFileContent() {
		return fileContent;
	}

	public void setFileContent(SensorSerializator fileContent) {
		this.fileContent = fileContent;
	}

	public NodoNArio<Sensor> getNodo() {
		return nodo;
	}

	public void setNodo(NodoNArio<Sensor> nodo) {
		this.nodo = nodo;
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
		
		JSonReader jsonReader = new JSonReader("save.json");
		
        System.out.println(jsonReader.getNodo().getHijos().get(0).getHijos().get(0).getValor());
        
	}
}
