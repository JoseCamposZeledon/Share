package model.parser;

import java.util.ArrayList;
import java.util.HashMap;

public class Sitio {
	
	private int profundidadActual;
	private int anchuraActual;
	private String link;
	protected SitioPadre padre;
	public HashMap<Integer, PalabrasRepetidas> repetidas;
	
	
	public Sitio() {
		this.profundidadActual = 1;
		this.anchuraActual = 1;
		repetidas =  new HashMap<Integer, PalabrasRepetidas>();
	}
	
	public Sitio(String link) {
		this();
		this.link = link;
	}

	public Sitio(int profundidadActual, int anchuraActual, String link) {
		this.profundidadActual = profundidadActual;
		this.anchuraActual = anchuraActual;
		this.link = link;
	}

	public int getProfundidadActual() {
		return profundidadActual;
	}
	
	

	public HashMap<Integer, PalabrasRepetidas> getRepetidas() {
		return repetidas;
	}

	public void setRepetidas(HashMap<Integer, PalabrasRepetidas> repetidas) {
		this.repetidas = repetidas;
	}

	public void setProfundidadActual(int profundidadActual) {
		this.profundidadActual = profundidadActual;
	}
	
	public int getAnchuraActual() {
		return anchuraActual;
	}
	
	public void setAnchuraActual(int anchuraActual) {
		this.anchuraActual = anchuraActual;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public SitioPadre getPadre() {
		return padre;
	}

	public void setPadre(SitioPadre padre) {
		this.padre = padre;
	}

	public void incrementarAnchura() {
		anchuraActual++;
	}
	
}
