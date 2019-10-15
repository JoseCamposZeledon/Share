package model.bptree;

import java.util.ArrayList;
import java.util.Collections;

/*
 * Nodo Arbol B+
 */
public class BPNode<T extends Comparable<T>> implements Comparable<BPNode<T>> {
	// Atributos 
	private int llavesMax;
	private T llaves[];
	private BPNode<T> padre, siguiente, hijos[];
	
	
	// Constructores
	public BPNode() {
		padre = null;
		siguiente = null;
	}
	
	
	@SuppressWarnings("unchecked")
	public BPNode(int pLlavesMax) {
		llavesMax = pLlavesMax;
		llaves = (T[]) new Object[llavesMax - 1];
		hijos = (BPNode<T>[]) new Object[llavesMax];
	}
	
	
	// Setters & Getters
	public T[] getLlaves() {
		return llaves;
	}

	
	public void setLlaves(T[] llaves) {
		this.llaves = llaves;
	}

	
	public BPNode<T> getPadre() {
		return padre;
	}

	
	public void setPadre(BPNode<T> padre) {
		this.padre = padre;
	}

	
	public BPNode<T> getSiguiente() {
		return siguiente;
	}

	
	public void setSiguiente(BPNode<T> siguiente) {
		this.siguiente = siguiente;
	}

	
	public BPNode<T>[] getHijos() {
		return hijos;
	}

	
	public void setHijos(BPNode<T>[] hijos) {
		this.hijos = hijos;
	}

	
	public int getLlavesMax() {
		return llavesMax;
	}

	
	// Metodos Creados
	public boolean tieneHijos() {
		return hijos.length != 0;
	}
	
	
	public boolean isEmpty() {
		return llaves.length == 0;
	}
	
	
	public boolean isFull () {
		return llaves.length == llavesMax;
	}
	
	
	@Override
	public int compareTo(BPNode<T> o) {
		// TODO Auto-generated method stub
		return 1;
	}
	
	
	// Retorna true si se pudo incluir la nueva llave, false de lo contrario
	public boolean nuevaLlave(T pLlave) { 
		if (this.isEmpty()) { // Caso 1, nodo vacio
			this.getLlaves()[0] = pLlave;
			return true;
		} else if(this.isFull()) { // Caso 2, nodo lleno
			return false;
		} else {
			
			
			
			return true;
		}
		
	}

	
}
