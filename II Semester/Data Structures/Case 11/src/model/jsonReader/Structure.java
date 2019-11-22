package model.jsonReader;

import java.util.ArrayList;

public class Structure {
	
	private int ancho;
	private int profundidad;
	private ArrayList<String> links;
	
	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public int getProfundidad() {
		return profundidad - 1;
	}

	public void setProfundidad(int profundidad) {
		this.profundidad = profundidad;
	}

	public ArrayList<String> getLinks() {
		return links;
	}

	public void setLinks(ArrayList<String> links) {
		this.links = links;
	}
	
}
