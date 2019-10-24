package model.parser;

import java.util.ArrayList;
import java.util.HashMap;

import model.tree.AVLTree;

public class SitioPadre extends Sitio {
	
	private HashMap<String, Sitio> map;
	private ArrayList<SitioDerivado> derivados;
	private AVLTree arbol;
	
	SitioPadre() {
		super();
		map = new HashMap<String, Sitio>();
		derivados = new ArrayList<SitioDerivado>();
	}
	
	SitioPadre(String link) {
		super(link);
		map = new HashMap<String, Sitio>();
		derivados = new ArrayList<SitioDerivado>();
		this.padre = this;
	}
	
	public boolean checkIfIn(String palabra) {
		return map.containsKey(palabra);
	}

	public HashMap<String, Sitio> getMap() {
		return map;
	}

	public void setMap(HashMap<String, Sitio> map) {
		this.map = map;
	}

	public ArrayList<SitioDerivado> getDerivados() {
		return derivados;
	}

	public void setDerivados(ArrayList<SitioDerivado> derivados) {
		this.derivados = derivados;
	}
	
}
