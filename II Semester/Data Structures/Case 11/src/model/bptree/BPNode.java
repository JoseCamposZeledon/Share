package model.bptree;

import java.util.Arrays;
import java.util.Collections;

/*
 * Nodo Arbol B+
 */
public class BPNode<T extends Comparable<T>> implements Comparable<T> {
	// Atributos 
	private int llavesMax;
	private T llaves[];
	private BPNode<T> padre, siguiente, hijos[];
	
	
	/*
	 * Inicializa parcialmente un arbol
	 */
	public BPNode() {
		padre = null;
		siguiente = null;
	}
	
	/*
	 * Inicializa un arbol vacio, con la cantidad de llaves que tendra
	 */
	@SuppressWarnings("unchecked")
	public BPNode(int pLlavesMax) {
		this();
		llavesMax = pLlavesMax;
		llaves = (T[]) new Comparable[llavesMax];
		hijos = (BPNode<T>[]) new Object[llavesMax + 1];
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
	
	
	public boolean isEmpty(Object[] pArray) {
		return Arrays.asList(pArray).isEmpty();
	}
	
	
	public boolean isFull (Object[] pArray) {
		return !Arrays.asList(pArray).contains(null);
	}
	
	/*
	 * Metodo necesario para hacer la clase comparable
	 */
	@Override
	public int compareTo(T o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/*
	 * Retorna true si se pudo incluir la nueva llave, false de lo contrario
	 */
	public boolean nuevaLlave(T pLlave) { 
		if (this.isEmpty(this.llaves)) { // Caso 1, nodo vacio
			this.getLlaves()[0] = pLlave;
			return true;
			
		} else if(this.isFull(this.llaves)) { // Caso 2, nodo lleno
			System.out.println("FAIl");
			return false;
			
		} else {
			return false;
		}
		
	}

	
	
	public static void main(String[] args) {
		BPNode<Integer> test = new BPNode<Integer>(5);
		
		test.nuevaLlave(1);
		test.nuevaLlave(13);
		test.nuevaLlave(5);
		test.nuevaLlave(13);
		test.nuevaLlave(-13);
	}

	
}
