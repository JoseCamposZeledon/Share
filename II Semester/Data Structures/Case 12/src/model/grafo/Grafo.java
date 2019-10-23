package model.grafo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

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

        public void limpiarAnteriores() {
                for (T nodo : nodos) {
                        ((Nodo<T>) ((Nodo<T>) nodo).getValor()).setAnterior(null);
                }
        }

        public Queue<T> backTrack(Nodo<T> pNodo) {
                LinkedList<Nodo<T>> resultado = new LinkedList<Nodo<T>>();
                Stack<Nodo<T>> reversa = new Stack<Nodo<T>>();
                Nodo<T> temp = pNodo;
                reversa.add(temp);
                while (temp.getAnterior() != null) {
                        reversa.add(temp.getAnterior());
                        temp = temp.getAnterior();
                }
                // Invierte el orden de la cola usando una pila
                while (!reversa.isEmpty()) {
                        resultado.add(reversa.pop());
                }
                return (Queue<T>) resultado;
        }

	public Queue<Nodo<T>> buscarOrden(Object pInicio, Object pBuscado) {
		// mapa y cola son para realizar busqueda por anchura, resultado se guarda cada busqueda
		// resultado es una cola con punteros hacia nodos en el orden que fue buscado, retorna null si no se encuentra el nodo
		HashMap<Integer, Nodo<T>> mapa = new HashMap<Integer, Nodo<T>>();
		Queue<Nodo<T>> cola = new LinkedList<Nodo<T>>();
		
		mapa.put(((Nodo<T>) pInicio).getIp(), (Nodo<T>) pInicio);
		cola.add((Nodo<T>) pInicio);
		
		while (!cola.isEmpty()) {
			Nodo<T> temp = cola.poll();
			for (Nodo<T> nodo : temp.getAdjacentes()) {
                                nodo.setAnterior(temp);
				if (!mapa.containsKey(nodo.getIp())) {
					if (nodo.equals(pBuscado)) {
						Queue<Nodo<T>> resultado;
                                                resultado = (Queue<Nodo<T>>) backTrack(nodo);
                                                limpiarAnteriores();
                                                return resultado;
					}
					mapa.put(nodo.getIp(), nodo);
					cola.add(nodo);
				}
			}
		}
                limpiarAnteriores();
		return null;  
		
	}
}
