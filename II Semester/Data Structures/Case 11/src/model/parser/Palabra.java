package model.parser;

import java.util.ArrayList;

import model.tree.AVLTree2;
import model.tree.Link;

public class Palabra implements Comparable<Palabra> {
	
	private String palabra;
	private ArrayList<PalabrasRepetidas> palabras;
	
	public Palabra() {
		palabras = new ArrayList<PalabrasRepetidas>();
	}
	
	public Palabra(String palabra) {
		this();
		this.palabra = palabra;
	}
	
	public String getPalabra() {
		return palabra;
	}

	public void setPalabra(String palabra) {
		this.palabra = palabra;
	}

	public ArrayList<PalabrasRepetidas> getPalabras() {
		return palabras;
	}

	public void setPalabras(ArrayList<PalabrasRepetidas> palabras) {
		this.palabras = palabras;
	}

	
	public ArrayList<Link> getLinks() {
		ArrayList<Link> resultado = new ArrayList<Link>();
		for (PalabrasRepetidas palabra : palabras) {
			resultado.add(AVLTree2.mapaLinks.get(palabra.getSitioWeb()));
		}
		return resultado;
	}

	@Override
	public int compareTo(Palabra o) {
		// TODO Auto-generated method stub
		return this.palabra.compareTo(o.getPalabra());
	}
	
	
}
