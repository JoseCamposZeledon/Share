package model.grafo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;

import model.cola.Cola;

public class Grafo<T> {
	private LinkedList<Nodo<T>> nodos;
	
	public void agregarNodo(Nodo<T> pNodo) {
		nodos.add(pNodo);
	}
	
	public ArrayList<Nodo<T>> dijkstra(Nodo<T> pInicio) {
		if (!nodos.contains(pInicio)) return null; 
		
		HashSet<Nodo<T>> visitados = new HashSet<Nodo<T>>();
		Cola<Nodo<T>> minimaPrioridad = new Cola<Nodo<T>>();
		HashMap<Nodo<T>, Integer> menoresPesos = new HashMap<Nodo<T>, Integer>();
		
		menoresPesos.put(pInicio, 0);
		minimaPrioridad.enqueue(pInicio);
		
		for (Nodo<T> nodoActual : this.nodos) {
			if (nodoActual != pInicio) {
				menoresPesos.put(nodoActual, Integer.MAX_VALUE);
			}
		}
		
		while (!minimaPrioridad.isEmpty()) {
			
			// Remueve el que tiene el valor más pequeño
			Nodo<T> nuevoMin = minimaPrioridad.dequeue();
			
			for (int posActual = 0; posActual < nuevoMin.getConexiones().size(); posActual++) {
				
				Arco<T> arcoActual = nuevoMin.getConexiones().get(posActual);
				
				if (!visitados.contains(arcoActual.getConexion()[1])) {
					
					int nuevaDistancia = menoresPesos.get(nuevoMin) + arcoActual.getPeso();
					if (nuevaDistancia < menoresPesos.get(arcoActual.getConexion()[1])) {
						
						menoresPesos.put(key, value)
						
					}
					
				}
				
				
			}
			
			
		}
		
		return null;
	}
}
