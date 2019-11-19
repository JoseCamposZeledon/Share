package model.json;

import java.awt.Rectangle;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.mapComponents.Obstaculo;
import model.mapComponents.ObstaculoGrafico;

public class MapParser implements IConstants {
	
	private static MapParser instance = null; 
	private Gson gson;
	private Obstaculo obstaculos;
	
	public static MapParser getInstance() {
		if (instance == null) {
			instance = new MapParser();
		}
		return instance;
	}
	
	private MapParser() {
		gson = new GsonBuilder().setPrettyPrinting().create();
	}
	
	public Obstaculo loadMap(String pNombreArchivo) {
		
		String pathMapa = MAPS_PATH + "\\" +pNombreArchivo;
		
		try {
			Reader reader = new FileReader(pathMapa);
			obstaculos = gson.fromJson(reader, Obstaculo.class);
			return obstaculos;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public static void main(String[] args) {
		System.out.println(MapParser.getInstance().loadMap("Mapa.json").getObstaculos()[0].getX1());
	}
	
}
