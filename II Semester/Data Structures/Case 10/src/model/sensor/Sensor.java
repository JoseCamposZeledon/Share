package model.sensor;

import java.util.Hashtable;

import model.Ubicacion.*;

public class Sensor implements ISensorConstants{
	
	private String id;
	private int cantidadAgua;
	private Ubicacion lugar;
	
	private Hashtable<Integer, Ubicacion> tipoUbicacion;
	
	public Sensor(String pId, int pTipoUbicacion, String pNombreLugar, int pCantidadAgua) {
		
		id = pId;
		
		cantidadAgua = pCantidadAgua;
		
		lugar = setTipoLugar(pTipoUbicacion);
		lugar.setNombre(pNombreLugar);
		
	}
	
	// Getters & Setters
	public String getId() {
		return id;
	}

	public void setId(String pId) {
		this.id = pId;
	}

	public int getCantidadAgua() {
		return cantidadAgua;
	}

	public void setCantidadAgua(int pCantidadAgua) {
		this.cantidadAgua = pCantidadAgua;
	}

	public Ubicacion getLugar() {
		return lugar;
	}

	public void setLugar(Ubicacion pLugar) {
		this.lugar = pLugar;
	}
	
	// Metodos creados
	public Ubicacion setTipoLugar(int pTipoUbicacion) {
		
		tipoUbicacion.put(CANTON, new Canton());
		tipoUbicacion.put(DISTRITO, new Distrito());
		tipoUbicacion.put(BARRIO, new Barrio());
		
		lugar = tipoUbicacion.get(pTipoUbicacion);
		
		return lugar;
	}

	
	
}	
