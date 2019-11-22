package model.splayTree;

import model.arbolnario.NodoJTree;
import model.sensor.Sensor;

public class SplayTree<T extends Comparable<T>> {
	
	private NodoSplay<T> raiz;
	private int cantidad;
	
	public NodoSplay<T> getRaiz() {
		return raiz;
	}
	public void setRaiz(NodoSplay<T> raiz) {
		this.raiz = raiz;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	
	public void convertirEnRaiz(NodoSplay<T> pNodo) {
		// Función recursiva para convertir por medio de rotaciones a un nodo en raiz.
		if (pNodo.esRaiz()) {
			// Nodo ya es raiz
			return;
		} else if (pNodo.getPadre().esRaiz()) {
			// Nodo se encuentra en el nivel 1
			pNodo.zig(pNodo.esHijoIzquierdo());
		} else if (pNodo.esHijoIzquierdo() != pNodo.getPadre().esHijoIzquierdo()) {
			// Nodo se encuentra en algún nivel del árbol y se diferencia con su padre en su posición como hijo
			pNodo.zigZag(pNodo.esHijoIzquierdo());
			convertirEnRaiz(pNodo);
		} else {
			// Nodo se encuentra en algún nivel del árbol pero su posición es igual a la del padre
			pNodo.zigZig(pNodo.esHijoIzquierdo());
			convertirEnRaiz(pNodo);
		}
	}
	
	
	public void agregar(T pValor) {
		// Función para agregar una variable de tipo T al árbol, notar que esta es la que debe ser usada
		// preferiblemente ya que hace validaciones y crea el nodo, a diferencia de llamar directamente a la otra versión de esta
		NodoSplay<T> nodo = new NodoSplay<T>(pValor);
		if (getRaiz() == null) {
			setRaiz(nodo);
		} else {
			agregar(nodo, getRaiz());
		}
	}
	
	
	public void agregar(NodoJTree<Sensor> pNodo) {
		NodoSplay<String> nodoSplay = new NodoSplay<String>(pNodo.getNodo().getValor().getId(), pNodo.getNodo());
		if (getRaiz() == null) {
			setRaiz((NodoSplay<T>) nodoSplay);
		} else {
			agregar((NodoSplay<T>) nodoSplay, getRaiz());
		}
	}
	
	
	public void agregar(NodoSplay<T> pNodo, NodoSplay<T> currentNodo) {
		// Función que correrá recursivamente y comparará el currentNodo con el pNodo
		int resultado = pNodo.compareTo(currentNodo);
		if (resultado == 0) {
			// Son iguales y como es un splay tree no sirve tener duplicados
			return;
		} else if (resultado < 0) {
			// Es menor, si existe hijo correr función con él, de lo contrario agregarlo como hijo izquierdo del currentNodo
			if (currentNodo.getHijoIzquierdo() != null) {
				agregar(pNodo, currentNodo.getHijoIzquierdo());
			} else {
				currentNodo.setHijoIzquierdo(pNodo);
				pNodo.setPadre(currentNodo);
			}
		} else {
			// Es mayor, si existe hijo correr función con él, de lo contrario agregarlo como hijo derecho del currentNodo
			if (currentNodo.getHijoDerecho() != null) {
				agregar(pNodo, currentNodo.getHijoDerecho());
			} else {
				currentNodo.setHijoDerecho(pNodo);
				pNodo.setPadre(currentNodo);
			}
		}
	}
	
	
	public NodoSplay<T> buscar(T pValor) {
		NodoSplay<T> nodo = new NodoSplay<T>(pValor);
		return buscar(nodo, getRaiz());
	}
	
	
	public NodoSplay<T> buscar(NodoSplay<T> pNodo, NodoSplay<T> currentNodo) {
		if (currentNodo == null) {
			// Se llegó a una hoja y no se encontró pNodo
			return null;
		}
		int resultado = pNodo.compareTo(currentNodo);
		if (resultado == 0) {
			// Son iguales por lo tanto existe en el arbol y retorna currentNodo
			convertirEnRaiz(currentNodo);
			setRaiz(currentNodo);
			return currentNodo;
		} else if (resultado < 0) {
			// Es menor, correr función en el hijo izquierdo de él
			return buscar(pNodo, currentNodo.getHijoIzquierdo());
		} else {
			// Es mayor, correr función con en el hijo derecho de él
			return buscar(pNodo, currentNodo.getHijoDerecho());
		}
	}
	
	public NodoSplay<T> getMax(NodoSplay<T> pNodo) {
		if (pNodo.getHijoDerecho() != null) {
			return getMax(pNodo.getHijoDerecho());
		} else {
			return pNodo;
		}
	}
	
	public void borrar(T pValor) {
		NodoSplay<T> buscado = buscar(pValor);
		if (buscado != null) {
			if (buscado.getHijoIzquierdo() == null) {
				setRaiz(buscado.getHijoDerecho());
			} else {
				buscado.getHijoIzquierdo().setPadre(null);
				NodoSplay<T> hojaMaxima = getMax(buscado.getHijoIzquierdo());
				convertirEnRaiz(hojaMaxima);
				hojaMaxima.setHijoDerecho(buscado.getHijoDerecho());
				if (buscado.getHijoDerecho() != null) {
					buscado.getHijoDerecho().setPadre(hojaMaxima);
				}
				setRaiz(hojaMaxima);
			}
			buscado = null;
		}
	}
	
	
	public void imprimirArbol() {
		// Imprime los elementos de menor a mayor desde la raiz
		imprimirArbol(getRaiz());
	}
	
	
	public void imprimirArbol(NodoSplay<T> pNodo) {
		// Imprime los elementos de menor a mayor desde el nodo
		if (pNodo != null) { 
			imprimirArbol(pNodo.getHijoIzquierdo());
			System.out.printf(pNodo + " ");
			imprimirArbol(pNodo.getHijoDerecho());
		}
	}
	
	
	public static void main(String args[]) {
		SplayTree<Integer> arbol = new SplayTree<Integer>();
		arbol.agregar(4);
		arbol.agregar(2);
		arbol.agregar(6);
		arbol.agregar(1);
		arbol.agregar(3);
		arbol.agregar(5);
		arbol.agregar(7);
		
		arbol.borrar(5);
		arbol.imprimirArbol();
		System.out.println(arbol.getRaiz());
	}
}
