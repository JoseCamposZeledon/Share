package model.grafo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Grafo<T> {
	
	private ArrayList<T> nodos;
	
	public Grafo() {
		nodos = new ArrayList<T>();
	}
	
	public ArrayList<T> getNodos() {
		return nodos;
	}

	public void setNodos(ArrayList<T> nodos) {
		this.nodos = nodos;
	}
	
	public void agregarNodo(T pNodo) {
		this.nodos.add(pNodo);
	}

	public Queue<T> buscarOrden(Object pInicio, Object pBuscado) {
		// mapa y cola son para realizar busqueda por anchura, resultado se guarda cada busqueda
		// resultado es una cola con punteros hacia nodos en el orden que fue buscado, retorna null si no se encuentra el nodo
		HashMap<Integer, Nodo<T>> mapa = new HashMap<Integer, Nodo<T>>();
		Queue<Nodo<T>> cola = new LinkedList<Nodo<T>>();
		Queue<Nodo<T>> resultado = new LinkedList<Nodo<T>>();
		
		mapa.put(((Nodo<T>) pInicio).getIp(), (Nodo<T>) pInicio);
		cola.add((Nodo<T>) pInicio);
		resultado.add((Nodo<T>) pInicio);
		
		while (!cola.isEmpty()) {
			Nodo<T> temp = cola.poll();
			for (Nodo<T> nodo : temp.getAdjacentes()) {
				if (!mapa.containsKey(nodo.getIp())) {
					resultado.add(nodo);
					if (nodo.equals(pBuscado)) {
						return (Queue<T>) resultado;
					}
					mapa.put(nodo.getIp(), nodo);
					cola.add(nodo);
				}
			}
		}
		return null;  
		
	}
}