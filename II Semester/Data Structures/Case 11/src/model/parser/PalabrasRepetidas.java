package model.parser;

import java.util.ArrayList;


public class PalabrasRepetidas implements Comparable<PalabrasRepetidas> {
	
	private int repeticiones;
	private ArrayList<String> palabras;
	private String sitioWeb;
	
	public PalabrasRepetidas() {
		repeticiones = 0;
		palabras = new ArrayList<String>();
	}
	
	public PalabrasRepetidas(String link, int repeticiones) {
		this();
		this.sitioWeb = link;
		this.repeticiones = repeticiones;
	}
	
	public int getRepeticiones() {
		return repeticiones;
	}

	public void setRepeticiones(int repeticiones) {
		this.repeticiones = repeticiones;
	}

	public ArrayList<String> getPalabras() {
		return palabras;
	}

	public void setPalabras(ArrayList<String> palabras) {
		this.palabras = palabras;
	}

	public String getSitioWeb() {
		return sitioWeb;
	}

	public void setSitioWeb(String sitioWeb) {
		this.sitioWeb = sitioWeb;
	}

	public int compareTo(int o) {
		return Integer.compare(repeticiones, o);	
	}

	@Override
	public int compareTo(PalabrasRepetidas o) {
		return o.compareTo(repeticiones);
	}
	
}
