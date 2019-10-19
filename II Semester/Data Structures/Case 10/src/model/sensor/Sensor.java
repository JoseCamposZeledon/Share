package model.sensor;

import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import model.ubicacion.*;

public class Sensor implements ISensorConstants{
	
	private String id;
	private int consumoBase;
	private int consumoActual;
	private Ubicacion lugar;
	
	private static Hashtable<Integer, Ubicacion> tipoUbicacion = new Hashtable<Integer, Ubicacion>();
	
	public Sensor() {
		tipoUbicacion.put(CANTON, new Canton());
		tipoUbicacion.put(DISTRITO, new Distrito());
		tipoUbicacion.put(BARRIO, new Barrio());
		tipoUbicacion.put(FUENTE_PRINCIPAL, new FuentePrincipal());
	}
	
	public Sensor(String pId, int pTipoUbicacion, String pNombreLugar, int pConsumoBase) {
		this();
		id = pId;
		consumoBase = pConsumoBase;

		consumoActual = pConsumoBase;
		

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

	public int getConsumoBase() {
		return consumoBase;
	}

	public void setConsumoBase(int consumoBase) {
		this.consumoBase = consumoBase;
	}

	public int getConsumoActual() {
		return consumoActual;
	}

	public void setConsumoActual(int consumoActual) {
		this.consumoActual = consumoActual;
	}

	public Ubicacion getLugar() {
		return lugar;
	}

	public void setLugar(Ubicacion pLugar) {
		this.lugar = pLugar;
	}
	
	public int getLugarInt() {
		int key= 9;
        Ubicacion value = lugar;
        
        for(Map.Entry entry: tipoUbicacion.entrySet()){
            if(value.getClass().equals(entry.getValue().getClass())){
                key = (int) entry.getKey();
                break; //breaking because its one to one map
            }
        }
        
        return key;
	}

	// Metodos creados
	public Ubicacion setTipoLugar(int pTipoUbicacion) {
		return tipoUbicacion.get(pTipoUbicacion);
	}

	public void actualizarConsumo() {
		Random randomValue = new Random();
		
		double randomRate = OSCILAMIENTO_MIN + (OSCILAMIENTO_MAX - OSCILAMIENTO_MIN) * randomValue.nextDouble();
		consumoActual = (int) (consumoBase + (consumoBase * randomRate));
	}
	 
	
	public String toString() {
		return this.getLugar().toString();
	}
}	
