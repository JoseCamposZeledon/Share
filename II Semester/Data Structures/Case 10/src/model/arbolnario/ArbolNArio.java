package model.arbolnario;

import java.util.ArrayList;

public class ArbolNArio<T> {

	private NodoNArio<T> raiz;
	private int cantidadNodos;
	
	
	// Constructores
	
	public ArbolNArio() {
		raiz = null;
	}
	
	public ArbolNArio(T pValor) {
		raiz = new NodoNArio<T>();
		raiz.setValor(pValor);
		
		cantidadNodos += 1;
	}

	// Getters & Setters generados
	public NodoNArio<T> getRaiz() {
		return raiz;
	}

	public void setRaiz(NodoNArio<T> raiz) {
		this.raiz = raiz;
	}
	
	public int getCantidadNodos() {
		return cantidadNodos;
	}
	
	//Metodos creados
	
	public void agregarNodo(NodoNArio<T> pPadre, NodoNArio<T> pNodo) {
		
		// Si no hay nodos entonces lo convierte en la raiz
		if (pPadre == null) {
			this.setRaiz(pNodo);
			return;
		}
		
		// Agrega el nodo al padre
		pPadre.agregarHijo(pNodo);
		
		if (pNodo.tieneHijos()) cantidadNodos += pNodo.getHijos().size();
		cantidadNodos += 1;
		
	}
	
	public void quitarNodo(NodoNArio<T> pNodo) {
		
		// Si es la raiz entonces se borra todo el arbol
		if (pNodo.getPadre() == null) {
			
			this.limpiar();
			
		} else {  // Quita algun nodo, si tiene hijos entonces les cambia el padre
			
			NodoNArio<T> padre = pNodo.getPadre();
			ArrayList<NodoNArio<T>> hijos = pNodo.getHijos();
			
			for (NodoNArio<T> hijoActual : hijos) {
				padre.agregarHijo(hijoActual);
			}
			
			padre.removerHijo(pNodo);
			
			pNodo = null;
			
			this.cantidadNodos -= 1;
		}
		
	}
	
	public void limpiar() {
		raiz = null;
	
		// Se llama al Garbage Collector para borrar todo
		System.gc();
		this.cantidadNodos = 0;
	}
	
	public NodoNArio<T> cambiarRaizNula(NodoNArio<T> pNodo) {
		if (this.raiz == null) {
			this.setRaiz(pNodo);
			this.cantidadNodos = 1;
		}
		
		return pNodo;
	}
	
	public boolean isEmpty() {
		return raiz == null;
	}
}
