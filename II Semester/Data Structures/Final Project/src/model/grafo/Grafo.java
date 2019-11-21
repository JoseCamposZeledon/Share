package model.grafo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import model.cola.Cola;

public class Grafo<T> {
	
	private LinkedList<Nodo<T>> nodos;
	
	public Grafo() {
		nodos = new LinkedList<Nodo<T>>();
	}
	
	public void agregarNodo(Nodo<T> pNodo) {
		nodos.addLast(pNodo);
	}
	
	public LinkedList<Nodo<T>> getNodos() {
		return nodos;
	}

	public void setNodos(LinkedList<Nodo<T>> nodos) {
		this.nodos = nodos;
	}

	/*
	 * Obtiene la ruta propuesta por los diferentes algoritmos
	 */
	public LinkedList<Nodo<T>> backTrack(Nodo<T> pOrigen, Nodo<T> pDestino) {
		//El origen o destino no existen dentro del grafo
		if (!(nodos.contains(pOrigen) && nodos.contains(pDestino))) return null;
		
		LinkedList<Nodo<T>> ruta = new LinkedList<Nodo<T>>();
		
		// Inserta todos los nodos hasta llegar a pDestino
		Nodo<T> nodoActual = pOrigen; 
	
		
		while (nodoActual != pDestino) {
			ruta.addFirst(nodoActual);
			nodoActual = nodoActual.getPrevio();
		}
		
		ruta.addFirst(pDestino);
		
		return ruta;
	}
	
	
	/*
	 * Busca la ruta mas pequena, por medio del algoritmo de Dijkstra
	 */
	public LinkedList<Nodo<T>> dijkstra(Nodo<T> pOrigen, Nodo<T> pDestino) {
		// El origen o destino no existen dentro del grafo
		if (!(nodos.contains(pOrigen) && nodos.contains(pDestino))) return null; 
		
		HashSet<Nodo<T>> visitados = new HashSet<Nodo<T>>();
		Cola<Nodo<T>> menorPrioridad = new Cola<Nodo<T>>();
		HashMap<Nodo<T>, Integer> menoresPesos = new HashMap<Nodo<T>, Integer>();
		
		// Revisa el origen
		menoresPesos.put(pOrigen, 0);
		menorPrioridad.enqueue(pOrigen);
		
		// Inserta todos los nodos con el mayor peso posible
		for (Nodo<T> nodoActual : this.nodos) {
			if (nodoActual != pOrigen) {
				menoresPesos.put(nodoActual, Integer.MAX_VALUE);
			}
		}
		
		while (!menorPrioridad.isEmpty()) {
			
			// Se remueve el menor elemento de la cola
			Nodo<T> nodoActual = menorPrioridad.dequeue();
			visitados.add(nodoActual);
			
			// Se insertan todos los vecinos del actual en la cola
			for (Arco<T> arcoActual : nodoActual.getConexiones()) {
				
				Nodo<T> vecino = arcoActual.getConexion()[1];
				
				// Si el vecino no esta visitado entonces revisa la distancia y cambia su prioridad (distancia total)
				if (!visitados.contains(vecino)) {
					
					int distanciaAlterna = menoresPesos.get(nodoActual) + arcoActual.getPeso();
					
					// Revisa si la distanciaAlterna es menor la que ya esta puesta para cambiarla
					if (distanciaAlterna < menoresPesos.get(vecino)) {
						menoresPesos.replace(vecino, menoresPesos.get(vecino), distanciaAlterna);
						menorPrioridad.enqueue(vecino, distanciaAlterna);
						vecino.setPrevio(nodoActual);
					}	
				}	
			}
		}
		if (pDestino.getPrevio() == null) {
			return new LinkedList<Nodo<T>>();
		}
		return backTrack(pDestino, pOrigen);
	}
	
	public void limpiarPrevios() {
		for (Nodo nodo : nodos) {
			nodo.setPrevio(null);
		}
	}
}
