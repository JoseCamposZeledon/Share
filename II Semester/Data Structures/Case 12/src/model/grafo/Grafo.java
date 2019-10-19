package model.grafo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Grafo {
	
	private static Grafo instance;
	
	private ArrayList<Nodo> nodos;
	
	private Grafo() {
		nodos = new ArrayList<Nodo>();
	}
	
	public static Grafo get() {
		if (instance == null) {
			instance = new Grafo();
		}
		return instance;
	}
	
	public ArrayList<Nodo> getNodos() {
		return nodos;
	}

	public void setNodos(ArrayList<Nodo> nodos) {
		this.nodos = nodos;
	}

	public Queue<Nodo> buscarOrden(Nodo pInicio, Nodo pBuscado) {
		// mapa y cola son para realizar busqueda por anchura, resultado se guarda cada busqueda
		// resultado es una cola con punteros hacia nodos en el orden que fue buscado, retorna null si no se encuentra el nodo
		HashMap<Integer, Nodo> mapa = new HashMap<Integer, Nodo>();
		Queue<Nodo> cola = new LinkedList<Nodo>();
		Queue<Nodo> resultado = new LinkedList<Nodo>();
		
		mapa.put(pInicio.getIp(), pInicio);
		cola.add(pInicio);
		resultado.add(pInicio);
		
		while (!cola.isEmpty()) {
			Nodo temp = cola.poll();
			for (Nodo nodo : temp.getAdjacentes()) {
				if (!mapa.containsKey(nodo.getIp())) {
					resultado.add(nodo);
					if (nodo.equals(pBuscado)) {
						return resultado;
					}
					mapa.put(nodo.getIp(), nodo);
					cola.add(nodo);
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		
		Nodo nodo1 = new Nodo();
		Nodo nodo2 = new Nodo();
		Nodo nodo3 = new Nodo();
		Nodo nodo4 = new Nodo();
		Nodo nodo5 = new Nodo();
		
		nodo1.setAdjacentes(new ArrayList<Nodo>(
            Arrays.asList(nodo2, nodo3)));
		nodo2.setAdjacentes(new ArrayList<Nodo>(
	            Arrays.asList(nodo1, nodo3, nodo4)));
		nodo3.setAdjacentes(new ArrayList<Nodo>(
	            Arrays.asList(nodo1, nodo2)));
		nodo4.setAdjacentes(new ArrayList<Nodo>(
	            Arrays.asList(nodo2, nodo5)));
		nodo5.setAdjacentes(new ArrayList<Nodo>(
	            Arrays.asList(nodo4)));
		
		Grafo.get().setNodos(new ArrayList<Nodo>(
	            Arrays.asList(nodo1, nodo2, nodo3, nodo4, nodo5)));
		
		for (Nodo nodo : Grafo.get().buscarOrden(nodo1, nodo5)) {
			System.out.println(nodo);
		}
	}
	
}