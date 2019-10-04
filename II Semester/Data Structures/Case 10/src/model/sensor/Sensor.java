package model.sensor;

import java.util.Hashtable;
import java.util.Random;

import model.ubicacion.*;

public class Sensor implements ISensorConstants{
	
	private String id;
	private int consumoBase;
	private int consumoActual;
	private Ubicacion lugar;
	
	private Hashtable<Integer, Ubicacion> tipoUbicacion = new Hashtable<Integer, Ubicacion>();
	
	public Sensor(String pId, int pTipoUbicacion, String pNombreLugar, int pConsumoBase) {
		
		id = pId;
		
		consumoBase = pConsumoBase;
		
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
	
	// Metodos creados
	public Ubicacion setTipoLugar(int pTipoUbicacion) {
		
		tipoUbicacion.put(CANTON, new Canton());
		tipoUbicacion.put(DISTRITO, new Distrito());
		tipoUbicacion.put(BARRIO, new Barrio());
		tipoUbicacion.put(FUENTE_PRINCIPAL, new FuentePrincipal());
		
		return tipoUbicacion.get(pTipoUbicacion);
		
	}

	public void actualizarConsumo() {
		Random randomValue = new Random();
		
		double randomRate = OSCILAMIENTO_MIN + (OSCILAMIENTO_MAX - OSCILAMIENTO_MIN) * randomValue.nextDouble();
		consumoActual = (int) (consumoBase + (consumoBase * randomRate));
		
	}
	 
	
	public static void main(String args[]) {
		
		Sensor sensorTest = new Sensor("TEST", CANTON, "LUGAR TEST", 31);
		
		System.out.println("CLASE > " + sensorTest.getLugar().getClass());
		System.out.println("BASE" + sensorTest.getConsumoBase());
		sensorTest.actualizarConsumo();
		System.out.println("ACTUAL 1 " + sensorTest.getConsumoActual());
		sensorTest.actualizarConsumo();
		System.out.println("ACTUAL 2 " + sensorTest.getConsumoActual());
	}
}	
