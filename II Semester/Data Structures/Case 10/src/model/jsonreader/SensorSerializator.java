package model.jsonreader;

import java.util.ArrayList;

public class SensorSerializator {
	
	private String id;
	private int tipoUbicacion;
	private String nombreLugar;
	private int consumoBase;
	private ArrayList<SensorSerializator> hijos;
	
	
	public SensorSerializator() {
	}
	
	
	public SensorSerializator(String id, int tipoUbicacion, String nombreLugar, int consumoBase) {
		super();
		this.id = id;
		this.tipoUbicacion = tipoUbicacion;
		this.nombreLugar = nombreLugar;
		this.consumoBase = consumoBase;
		hijos = new ArrayList<SensorSerializator>();
	}


	public SensorSerializator(String id, int tipoUbicacion, String nombreLugar, int consumoBase,
			ArrayList<SensorSerializator> hijos) {
		super();
		this.id = id;
		this.tipoUbicacion = tipoUbicacion;
		this.nombreLugar = nombreLugar;
		this.consumoBase = consumoBase;
		this.hijos = hijos;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getTipoUbicacion() {
		return tipoUbicacion;
	}
	public void setTipoUbicacion(int tipoUbicacion) {
		this.tipoUbicacion = tipoUbicacion;
	}
	public String getNombreLugar() {
		return nombreLugar;
	}
	public void setNombreLugar(String nombreLugar) {
		this.nombreLugar = nombreLugar;
	}
	public int getConsumoBase() {
		return consumoBase;
	}
	public void setConsumoBase(int consumoBase) {
		this.consumoBase = consumoBase;
	}
	public ArrayList<SensorSerializator> getHijos() {
		return hijos;
	}
	public void setHijos(ArrayList<SensorSerializator> hijos) {
		this.hijos = hijos;
	}
	
	
	
}
