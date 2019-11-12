package model.grafo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Grafo<T> {
	
	private ArrayList<Nodo<T>> nodos;
	
	public Grafo() {
		nodos = new ArrayList<Nodo<T>>();
	}
	
	public ArrayList<Nodo<T>> getNodos() {
		return nodos;
	}

	public void setNodos(ArrayList<Nodo<T>> nodos) {
		this.nodos = nodos;
	}
	
	public void agregarNodo(T pValor) {
		this.nodos.add(new Nodo(pValor));
	}

	public void limpiarAnteriores() {
		for (Nodo<T> nodo : nodos) {
			nodo.setAnterior(null);
		}
	}

	public Queue<T> backTrack(Nodo<T> pOrigen, Nodo<T> pDestino) {
		LinkedList<Nodo<T>> resultado = new LinkedList<Nodo<T>>();
		Stack<Nodo<T>> reversa = new Stack<Nodo<T>>();
		Nodo<T> temp = pDestino;
		reversa.add(temp);
		while (temp != pOrigen) {
			reversa.add(temp.getAnterior());
			temp = temp.getAnterior();
		}
		// Invierte el orden de la cola usando una pila
		while (!reversa.isEmpty()) {
			resultado.add(reversa.pop());
		}
		return (Queue<T>) resultado;
	}

	public Queue<T> buscarOrden(Object pInicio, Object pBuscado) {
		// mapa y cola son para realizar busqueda por anchura, resultado se guarda cada busqueda
		// resultado es una cola con punteros hacia nodos en el orden que fue buscado, retorna null si no se encuentra el nodo
		HashMap<Integer, Nodo<T>> mapa = new HashMap<Integer, Nodo<T>>();
		Queue<Nodo<T>> cola = new LinkedList<Nodo<T>>();
		
		mapa.put(((Nodo<T>) pInicio).getIp(), (Nodo<T>) pInicio);
		cola.add((Nodo<T>) pInicio);
		
		while (!cola.isEmpty()) {
			Nodo<T> temp = cola.poll();
			for (Nodo<T> nodo : temp.getAdjacentes()) {
				if (!mapa.containsKey(nodo.getIp())) {
					nodo.setAnterior(temp);
					if (nodo.equals(pBuscado)) {
						Queue<Nodo<T>> resultado;
						resultado = (Queue<Nodo<T>>) backTrack((Nodo<T>) pInicio, (Nodo<T>) pBuscado);
						limpiarAnteriores();
						return (Queue<T>) resultado;
					} else {
						mapa.put(nodo.getIp(), nodo);
						cola.add(nodo);
					}
				}
			}
		}
                limpiarAnteriores();
		return null;  
		
	}
}
