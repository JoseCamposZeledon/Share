package model.grafo;

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
	
	/*
	 * Obtiene la ruta propuesta por los diferentes algoritmos
	 */
	public LinkedList<Nodo<T>> backTrack(Nodo<T> pOrigen, Nodo<T> pDestino) {
		//El origen o destino no existen dentro del grafo
		if (!(nodos.contains(pOrigen) && nodos.contains(pDestino))) return null;
		
		LinkedList<Nodo<T>> ruta = new LinkedList<Nodo<T>>();
		
		// Inserta todos los nodos hasta llegar a pDestino
		Nodo<T> nodoActual = pOrigen; 
		
		System.out.println(nodoActual.getValor());
		
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
		
		return backTrack(pDestino, pOrigen);
	}
	
	public static void main(String[] args) {
		
		Grafo<String> test = new Grafo<String>();
		
		Nodo<String> nodoA = new Nodo<String>("A");
		Nodo<String> nodoB = new Nodo<String>("B");
		Nodo<String> nodoC = new Nodo<String>("C");
		Nodo<String> nodoD = new Nodo<String>("D");
		Nodo<String> nodoE = new Nodo<String>("E");
		
		nodoA.conectar(nodoB, 3);
		nodoA.conectar(nodoC, 5);
		nodoA.conectar(nodoD, 4);
		
		nodoB.conectar(nodoD, 5);
		
		nodoC.conectar(nodoE, 2);
		
		nodoD.conectar(nodoE, 7);
		
		test.agregarNodo(nodoA);
		test.agregarNodo(nodoB);
		test.agregarNodo(nodoC);
		test.agregarNodo(nodoD);
		test.agregarNodo(nodoE);
		
		for (Nodo<String> actual : test.dijkstra(nodoA, nodoE)) {
			System.out.println("Actual: " + actual.getValor());
		}
		
		
	}
}
